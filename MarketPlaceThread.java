import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MarketPlaceThread extends Thread {
    protected Socket socket;
    protected PrintWriter writer;
    protected BufferedReader br;

    public MarketPlaceThread(Socket clientSocket, PrintWriter pw, BufferedReader br) {
        this.socket = clientSocket;
        this.writer = pw;
        this.br = br;
    }

    public void run() {
        boolean createNewAccount = false;

        User user = null;

        try {

            String line;

            // Receives the Login Credentials, and returns the information
            while (true) {
                line = br.readLine();
                if (line.contains("Break out of the loop")) {
                    break;
                }
                if (line.contains("Creating a new account")) {
                    createNewAccount = true;
                    break;
                }
                String[] lines = line.split(";");
                line = verifyLogin(lines[0], lines[1]);
                if (line == null) {
                    writer.println("Incorrect Username or Password, try again");
                    writer.flush();
                } else {
                    user = ThreadedMarketPlaceServer.getUser(line);
                    writer.println(user);
                    writer.flush();
                }

            }
            if (createNewAccount) {
                line = br.readLine();
                String[] info = line.split(";");
                user = createAccount(info[0], info[1], info[2], info[3]);
                if (user == null) {
                    line = "ERROR";
                }
                writer.println(line);
                writer.flush();

            }

            // Options for Customer or Seller
            String option = br.readLine();
            if (option.equals("Customer")) {
                // Customer Options
                label1:
                while (true) {
                    option = br.readLine();

                    switch (option) {
                        case "1": {
                            String toReturn = CustomerServer.viewMarket();
                            writer.println(toReturn);
                            writer.flush();

                            break;
                        }
                        case "2": {
                            option = br.readLine();
                            String message = br.readLine();
                            String toReturn = CustomerServer.searchProducts(option, message);
                            if (toReturn.equals("")) {
                                writer.println("None");
                            } else {
                                writer.println(toReturn);
                            }
                            writer.flush();
                            break;
                        }
                        case "3": {
                            String toReturn = CustomerServer.sortPrice();
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }
                        case "4": {
                            String toReturn = CustomerServer.sortQuantity();
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }
                        case "5": {
                            String toReturn = CustomerServer.viewCustomer((Customer) user);
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }
                        case "6": {
                            try {
                                String toReturn = CustomerServer.exportPurchaseHistory((Customer) user);
                                writer.println(toReturn);
                                writer.println("finished");
                                writer.flush();
                            } catch (IOException e) {
                                writer.println("Failed");
                                writer.flush();
                            }
                            break;
                        }
                        case "7": {
                            String toReturn = CustomerServer.viewMarket();
                            writer.println(toReturn);
                            writer.flush();
                            toReturn = br.readLine();
                            Product product = ThreadedMarketPlaceServer.getProduct(toReturn);
                            File f = new File(product.getStore() + " Market.txt");
                            String quan = br.readLine();
                            int quantity = Integer.parseInt(quan);
                            if (quantity <= 0) {
                                writer.println("false");
                            } else {
                                BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
                                ArrayList<String> lines = new ArrayList<>();
                                ArrayList<String> otherLines = new ArrayList<>();
                                String x = "";
                                while ((x = bufferedReader.readLine()) != null) {
                                    if (x.contains("------")) break;
                                    lines.add(x);
                                }
                                while (x != null) {
                                    otherLines.add(x);
                                    x = bufferedReader.readLine();
                                }
                                int index = -1;
                                for (int i = 0; i < lines.size(); i++) {
                                    if (lines.get(i).contains(product.getName())) {
                                        index = i;
                                    }
                                }
                                PrintWriter printWriter = new PrintWriter(new FileOutputStream(f, false));
                                String temp = lines.get(index);
                                lines.remove(index);
                                if (quantity > product.getQuantity()) quantity = product.getQuantity();
                                String[] productInfo = temp.split(",");
                                productInfo[3] = String.valueOf(Integer.parseInt(productInfo[3]) - quantity);
                                if (Integer.parseInt(productInfo[3]) > 0) {
                                    printWriter.println(productInfo[0] + "," + productInfo[1] + "," + productInfo[2] + ","
                                            + productInfo[3] + "," + productInfo[4]);
                                }
                                for (String lin : lines) printWriter.println(lin);
                                for (String lin : otherLines) printWriter.println(lin);
                                printWriter.close();
                                assert user != null;
                                f = new File(user.getUsername() + "'s File.txt");
                                lines = ThreadedMarketPlaceServer.getTextInfo(f);
                                boolean bol = false;
                                index = -1;
                                for (int i = 0; i < lines.size(); i++) {
                                    if (lines.get(i).contains(product.getName()) && lines.get(i).contains(product.getDescription()) && lines.get(i).contains(product.getStore())) {
                                        bol = true;
                                        index = i;
                                    }
                                }
                                if (bol) {
                                    printWriter = new PrintWriter(new FileOutputStream(f, false));
                                    temp = lines.get(index);
                                    lines.remove(index);
                                    for (String lin : lines) printWriter.println(lin);
                                    productInfo = temp.split(",");
                                    productInfo[3] = String.valueOf(Integer.parseInt(productInfo[3]) + quantity);
                                    printWriter.println(productInfo[0] + "," + productInfo[1] + "," + productInfo[2] + ","
                                            + productInfo[3] + "," + productInfo[4]);
                                    printWriter.close();
                                } else {
                                    printWriter = new PrintWriter(new FileOutputStream(f, true));
                                    printWriter.println(product.getName() + "," + product.getStore() + "," + product.getDescription() + ","
                                            + quantity + "," + product.getPrice());
                                    printWriter.close();
                                }
                            }
                            writer.println("success");
                            writer.flush();
                            break;
                        }
                        case "8": {
                            assert user != null;
                            String toReturn = CustomerServer.viewShoppingCart((Customer) user);
                            writer.println(toReturn);
                            writer.flush();
                            toReturn = br.readLine();
                            Product product = ThreadedMarketPlaceServer.getProduct(toReturn);
                            File f = new File(user.getUsername() + "'s File.txt");
                            String quan = br.readLine();
                            int quantity = Integer.parseInt(quan);
                            if (quantity <= 0) {
                                writer.println("false");
                            } else {
                                ArrayList<String> lines = ThreadedMarketPlaceServer.getTextInfo(f);
                                int index = -1;
                                for (int i = 0; i < lines.size(); i++) {
                                    if (lines.get(i).contains(product.getName())) {
                                        index = i;
                                    }
                                }
                                PrintWriter printWriter = new PrintWriter(new FileOutputStream(f, false));
                                String temp = lines.get(index);
                                lines.remove(index);
                                if (quantity > product.getQuantity()) {
                                    quantity = product.getQuantity();
                                }
                                String[] productInfo = temp.split(",");

                                productInfo[3] = String.valueOf(Integer.parseInt(productInfo[3]) - quantity);
                                for (String x : lines) printWriter.println(x);
                                if (Integer.parseInt(productInfo[3]) > 0) {
                                    printWriter.println(productInfo[0] + "," + productInfo[1] + "," + productInfo[2] + ","
                                            + productInfo[3] + "," + productInfo[4]);
                                }
                                printWriter.close();
                                f = new File(product.getStore() + " Market.txt");
                                BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
                                lines = new ArrayList<>();
                                String x = "";
                                while ((x = bufferedReader.readLine()) != null) {
                                    if (x.contains("------")) break;
                                    lines.add(x);
                                }
                                ArrayList<String> otherLines = new ArrayList<>();
                                while (x != null) {
                                    otherLines.add(x);
                                    x = bufferedReader.readLine();
                                }
                                boolean bol = false;
                                index = -1;
                                for (int i = 0; i < lines.size(); i++) {
                                    if (lines.get(i).contains(product.getName()) && lines.get(i).contains(product.getDescription()) && lines.get(i).contains(product.getStore())) {
                                        bol = true;
                                        index = i;
                                    }
                                }
                                if (bol) {
                                    printWriter = new PrintWriter(new FileOutputStream(f, false));
                                    temp = lines.get(index);
                                    lines.remove(index);

                                    productInfo = temp.split(",");

                                    productInfo[3] = String.valueOf(Integer.parseInt(productInfo[3]) + quantity);
                                    printWriter.println(productInfo[0] + "," + productInfo[1] + "," + productInfo[2] + ","
                                            + productInfo[3] + "," + productInfo[4]);
                                    for (String lin : lines) printWriter.println(lin);
                                    for (String lin : otherLines) printWriter.println(lin);
                                    printWriter.close();
                                } else {
                                    printWriter = new PrintWriter(new FileOutputStream(f, true));
                                    printWriter.println(product.getName() + "," + product.getStore() + "," + product.getDescription() + ","
                                            + quantity + "," + product.getPrice());
                                    printWriter.close();
                                }
                            }
                            writer.println("success");
                            writer.flush();
                            break;
                        }
                        case "9": {
                            String toReturn = CustomerServer.buyShoppingCart((Customer) user);
                            writer.println(toReturn);
                            writer.println("finished");
                            writer.flush();
                            break;
                        }
                        case "10": {
                            String toReturn = CustomerServer.viewShoppingCart((Customer) user);
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }
                        case "11":
                            String info = "Option 1 - You can view what products are for sale in each market;" +
                                    "Option 2 - You can search for a Product by name, quantity, or description;" +
                                    "Option 3 - You can sort all products by price least to greatest;" +
                                    "Option 4 - You can sort all products by quantity least to greatest;" +
                                    "Option 5 - You can view the dashboard;" +
                                    "Option 6 - You can export file of your purchase history;" +
                                    "Option 7 - You can add item to your shopping cart;" +
                                    "Option 8 - You can remove item in your shopping cart;" +
                                    "Option 9 - You can purchase all items in your shopping cart;" +
                                    "Option 10 - You can view your shopping cart";
                            writer.println(info);
                            writer.flush();
                            break;
                        default:
                            break label1;
                    }
                }


            } else {
                // Seller Options
                label:
                while (true) {
                    option = br.readLine();

                    switch (option) {
                        case "1": {
                            String toReturn = CustomerServer.viewMarket();
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }
                        case "2": {
                            option = br.readLine();
                            ArrayList<String> lines = ThreadedMarketPlaceServer.getTextInfo(new File("Markets.txt"));
                            String toReturn = "";
                            for (String x : lines) {
                                toReturn = toReturn + x + ";";
                            }
                            writer.println(toReturn);
                            writer.flush();
                            String market = br.readLine();
                            if (option.equals("Create")) {
                                String info = br.readLine();
                                SellerServer.createNewItem(info, market);
                            } else if (option.equals("Delete")) {
                                ArrayList<String> info = ThreadedMarketPlaceServer.getTextInfo(new File(market + " Market.txt"));
                                toReturn = "";
                                boolean bol = true;
                                for (String x : info) {
                                    if (!x.contains("-----") && bol) {
                                        toReturn = toReturn + x + ";";
                                    } else {
                                        bol = false;
                                    }
                                }
                                writer.println(toReturn);
                                writer.flush();
                                String item = br.readLine();
                                SellerServer.deleteItem(item, market);
                            } else {
                                ArrayList<String> info = ThreadedMarketPlaceServer.getTextInfo(new File(market + " Market.txt"));
                                toReturn = "";
                                boolean bol = true;
                                for (String x : info) {
                                    if (!x.contains("-----") && bol) {
                                        toReturn = toReturn + x + ";";
                                    } else {
                                        bol = false;
                                    }
                                }
                                writer.println(toReturn);
                                writer.flush();
                                String item = br.readLine();
                                String newInfo = br.readLine();
                                SellerServer.editItem(item, newInfo, market);
                            }
                            break;
                        }
                        case "3": {
                            String market = br.readLine();
                            String toReturn = SellerServer.viewSales(market);
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }
                        case "4": {
                            String market = br.readLine();
                            String toReturn = SellerServer.viewSeller(market);
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }
                        case "5":
                            //TODO Import Products CSV File
                            break;
                        case "6":
                            //TODO Export Products CSV File
                            break;
                        case "7":
                            //TODO View Shopping Carts
                            break;
                        case "8": {
                            String market = br.readLine();
                            ArrayList<String> lines = ThreadedMarketPlaceServer.getTextInfo(new File("Markets.txt"));
                            boolean bol = true;
                            for (String x : lines) {
                                if (x.equals(market)) {
                                    writer.println("Market already exists");
                                    writer.flush();
                                    bol = false;
                                }
                            }
                            if (bol) {
                                try {
                                    PrintWriter pw = new PrintWriter(new FileWriter("Markets.txt", true));
                                    pw.println(market);
                                    pw.flush();
                                    pw.close();
                                    FileWriter fw = new FileWriter(market + " Market.txt");
                                    pw = new PrintWriter(market + " Market.txt");
                                    pw.println("--------");
                                    pw.println("--------");
                                    fw.write("");
                                    fw.flush();
                                    pw.close();
                                    writer.println("Success");
                                    writer.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    writer.println("Fail");
                                    writer.flush();
                                }
                            }

                            break;
                        }
                        case "9": {
                            break;
                        }
                        case "10":
                            String info = "Option 1 - You can view what products are for sale in each market;" +
                                    "Option 2 - You can create, delete, or edit a product from a store;" +
                                    "Option 3 - You can view sales in each market in the market place;" +
                                    "Option 4 - You can view the dashboard;" +
                                    "Option 5 - You can import products using the CSV file;" +
                                    "Option 6 - You can export products using the CSV file;" +
                                    "Option 7 - You can view shopping carts from customers;" +
                                    "Option 8 - You can create a brand new market;" +
                                    "Option 9 - You can delete a market in the market place";
                            writer.println(info);
                            writer.flush();
                            break;
                        case "11":
                            break label;
                    }
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }



    }




    // Checks the login information, and checks if the info matches the login
    public static String verifyLogin(String username, String password) throws IOException {
        ArrayList<String> lines = ThreadedMarketPlaceServer.getTextInfo(new File("login.txt"));
        for (String line : lines) {
            String[] contents = line.split(";");
            if (contents[0].equals(username) && contents[1].equals(password)) {
                return line;
            }
        }
        return null;
    }

    // Creates a new account based on the information given on the create account page
    public static User createAccount(String username, String password, String name, String option) {
        try {

            PrintWriter pw = new PrintWriter(new FileOutputStream("login.txt", true));
            pw.println("");
            pw.println(username + ";" + password + ";" + name);
            pw.close();

            File f = new File(username + "'s File.txt");
            pw = new PrintWriter(new FileOutputStream(f, true));
            pw.println(("Name: " + name));
            pw.println("User: " + option);
            pw.close();

            // Sellers have access to all Customer information
            if (option.equals("Customer")) {
                f = new File("Customers.txt");
                pw = new PrintWriter(new FileOutputStream(f, true));
                if (f.createNewFile()) {
                    ArrayList<String> lines = ThreadedMarketPlaceServer.getTextInfo(f);
                    for (String x : lines) {
                        pw.println(x);
                    }
                }

                pw.println(name);
                pw.close();
                return new Customer(name, username, password);

            } else {
                return new Seller(name, username, password);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
