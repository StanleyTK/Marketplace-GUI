import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Server that gives the information necessary to the clients
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Project 5</p>
 *
 * @author Stanley Kim
 * @version December 7, 2022
 */


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
                    user = Server.getUser(line);
                    writer.println(user);
                    writer.flush();
                }

            } // Creates a new account
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


                        // Views the overall marketplace
                        case "1": {
                            String toReturn = CustomerServer.viewMarket();
                            writer.println(toReturn);
                            writer.flush();

                            break;
                        }
                        // Searches for the product using either name, description, or market name

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
                        // Sorts the price least to greatest, displays into a table

                        case "3": {
                            String toReturn = CustomerServer.sortPrice();
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }
                        // Sorts the quantity least to greatest, displays into a table

                        case "4": {
                            String toReturn = CustomerServer.sortQuantity();
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }
                        case "5": {
                            // Allows the user to view the dashboard
                            String toReturn = CustomerServer.viewCustomer((Customer) user);
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }
                        // User can export their purchase history
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
                        // Adds the item from the marketplace into the shopping cart
                        case "7": {
                            String toReturn = CustomerServer.viewMarket();
                            writer.println(toReturn);
                            writer.flush();
                            toReturn = br.readLine();
                            Product product = Server.getProduct(toReturn);
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
                                lines = Server.getTextInfo(f);
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
                            // Removes an item from the shopping cart
                            assert user != null;
                            String toReturn = CustomerServer.viewShoppingCart((Customer) user);
                            writer.println(toReturn);
                            writer.flush();
                            toReturn = br.readLine();
                            Product product = Server.getProduct(toReturn);
                            File f = new File(user.getUsername() + "'s File.txt");
                            String quan = br.readLine();
                            int quantity = Integer.parseInt(quan);
                            if (quantity <= 0) {
                                writer.println("false");
                            } else {
                                ArrayList<String> lines = Server.getTextInfo(f);
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

                        // Buys all the items in the shopping cart
                        case "9": {
                            String toReturn = CustomerServer.buyShoppingCart((Customer) user);
                            writer.println(toReturn);
                            writer.println("finished");
                            writer.flush();
                            break;
                        }
                        // Allows user to view the shopping cart
                        case "10": {
                            String toReturn = CustomerServer.viewShoppingCart((Customer) user);
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }

                        // Shows the information of what each option does
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
                        // Allows seller to view the market
                        case "1": {
                            String toReturn = CustomerServer.viewMarket();
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }

                        // Creates, deletes, or edits a product from a store
                        case "2": {
                            option = br.readLine();
                            ArrayList<String> lines = Server.getTextInfo(new File("Markets.txt"));
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
                                ArrayList<String> info = Server.getTextInfo(new File(market + " Market.txt"));
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
                                ArrayList<String> info = Server.getTextInfo(new File(market + " Market.txt"));
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
                        // Allows users to view the sales
                        case "3": {
                            String market = br.readLine();
                            String toReturn = SellerServer.viewSales(market);
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }
                        // Allows sellers to view the dashboard
                        case "4": {
                            String market = br.readLine();
                            String toReturn = SellerServer.viewSeller(market);
                            writer.println(toReturn);
                            writer.flush();
                            break;
                        }

                        // Imports Product into CSV File
                        case "5":
                            try {
                                String lines = "";
                                String fileLocation = br.readLine();
                                line = br.readLine();
                                ArrayList<Product> products = new ArrayList<Product>();
                                while (!line.equals("")) {
                                    lines += line + "\n";
                                    products.add(Server.getProduct(line));
                                    line = br.readLine();
                                }
                                BufferedReader bfr = new BufferedReader(new FileReader(fileLocation));
                                line = bfr.readLine();
                                while (!line.contains("------")) {
                                    line = bfr.readLine();
                                }
                                String rest = "";
                                while (line != null) {
                                    rest += line + "\n";
                                    line = bfr.readLine();
                                }
                                FileOutputStream fos = new FileOutputStream(fileLocation);
                                PrintWriter pw = new PrintWriter(fos);
                                for (int i = 0; i < products.size(); i++) {
                                    pw.println(products.get(i).toString());
                                }
                                pw.print(rest);
                                pw.close();
                                bfr.close();
                                writer.println("success");
                                writer.flush();
                            } catch (IOException e) {
                                writer.println("error");
                                writer.flush();
                            }
                            break;

                        // Exports Product into CSV File
                        case "6":
                            String fileLocation = br.readLine() + " Market.txt";
                            File file = new File(fileLocation);
                            BufferedReader bfr = new BufferedReader(new FileReader(file));
                            line = bfr.readLine();
                            String products = "";
                            while (!line.contains("-----")) {
                                products += line + "\n";
                                line = bfr.readLine();
                            }
                            writer.println(products);
                            writer.println("finished");
                            writer.flush();
                            bfr.close();
                            break;

                        // Views the shopping cart
                        case "7": {
                            String toReturn = SellerServer.customerShoppingCarts();
                            writer.println(toReturn);
                            writer.flush();
                            String selectedCustomer = "";

                            if (br.readLine().equals("Customer Selected")) {
                                selectedCustomer = br.readLine();

                                System.out.println(selectedCustomer);

                                File selectedCustomerFile = new File(selectedCustomer + "'s File.txt");
                                System.out.println("u at least getting to here?");

                                ArrayList<String> fileInfo = Server.getTextInfo(selectedCustomerFile);
                                String concat = "";

                                System.out.println("1");

                                fileInfo.remove(0);
                                fileInfo.remove(0);
                                boolean isInfo = false;
                                for (String str : fileInfo) {
                                    concat = concat + ";" + str;
                                    isInfo = true;
                                }
                                System.out.println("2");
                                if (isInfo) {
                                    concat = concat.substring(1);
                                } else {
                                    concat = "There are no items in this user's shopping cart.";
                                }
                                writer.println("Done!");
                                writer.flush();
                                writer.println(concat);
                                writer.flush();
                                break;
                            }
                        }
                        // Creates a new market
                        case "8": {
                            String market = br.readLine();
                            ArrayList<String> lines = Server.getTextInfo(new File("Markets.txt"));
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

                        // Deletes an existing market
                        case "9": {
                            ArrayList<String> lines = Server.getTextInfo(new File("Markets.txt"));
                            String toReturn = "";
                            for (String x : lines) {
                                toReturn = toReturn + x + ";";
                            }
                            writer.println(toReturn);
                            writer.flush();
                            if (br.readLine().equals("Remove marketplace")) {
                                String marketToRemove = br.readLine();
                                ArrayList<String> linesToAdd = new ArrayList<>();

                                File markets = new File("Markets.txt");
                                try {
                                    bfr = new BufferedReader(new FileReader(markets));
                                    String lin = "";

                                    while ((lin = bfr.readLine()) != null) {
                                        if (!lin.contains(marketToRemove)) {
                                            linesToAdd.add(lin);
                                        }
                                    }
                                    bfr.close();
                                    PrintWriter pw = new PrintWriter(new FileOutputStream(markets));
                                    for (int i = 0; i < linesToAdd.size(); i++) {
                                        pw.println(linesToAdd.get(i));
                                    }
                                    pw.close();

                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                try {
                                    File tempFile = new File("myTempFile.txt");
                                    BufferedWriter bfw = new BufferedWriter(new FileWriter(tempFile));

                                    for (int i = 0; i < linesToAdd.size(); i++) {
                                        bfw.write(linesToAdd.get(i) + "\n");
                                    }
                                    bfw.flush();
                                    bfw.close();

                                    MarketPlaceThread.copyFileToFile(tempFile, markets);
                                    tempFile.delete();

                                } catch (IOException exception) {
                                    exception.printStackTrace();
                                }
                                try {
                                    File f = new File("DeletedMarkets.txt");
                                    BufferedWriter bfw2 = new BufferedWriter(new FileWriter(f, true));
                                    bfw2.append("\n" + marketToRemove);
                                    bfw2.flush();
                                    bfw2.close();

                                    File customers = new File("Customers.txt");
                                    bfr = new BufferedReader(new FileReader(customers));
                                    String lin = "";
                                    ArrayList<String> customerArray = new ArrayList<>();
                                    while ((lin = bfr.readLine()) != null) {
                                        customerArray.add(lin);
                                    }
                                    bfr.close();

                                    ArrayList<File> customerFileArray = new ArrayList<>();

                                    for (String s : customerArray) {
                                        File customersFile = new File(s + "'s File.txt");
                                        customerFileArray.add(customersFile);
                                    }

                                    for (File fi : customerFileArray) {
                                        BufferedReader bufferedReader = new BufferedReader(new FileReader(fi));
                                        ArrayList<String> linesInFile = new ArrayList<>();
                                        while ((lin = bufferedReader.readLine()) != null) {
                                            if (!lin.contains("," + marketToRemove + ",")) {
                                                linesInFile.add(lin);
                                            }
                                        }
                                        bufferedReader.close();

                                        File tempFile = new File("myTempFile.txt");
                                        PrintWriter pw = new PrintWriter(new FileOutputStream(tempFile));

                                        for (String s : linesInFile) {
                                            pw.write(s + "\n");
                                        }

                                        pw.close();
                                        MarketPlaceThread.copyFileToFile(tempFile, fi);
                                    }

                                } catch (IOException exception) {
                                    exception.printStackTrace();
                                }
                            }


                            break;
                        }

                        // More information for the seller options
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
        // Prints out that client has disconnected in the server
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }


    }



    // Checks the login information, and checks if the info matches the login
    public static String verifyLogin (String username, String password) throws IOException {
        ArrayList<String> lines = Server.getTextInfo(new File("login.txt"));
        for (String line : lines) {
            String[] contents = line.split(";");
            if (contents[0].equals(username) && contents[1].equals(password)) {
                return line;
            }
        }
        return null;
    }

    // Creates a new account based on the information given on the create account page
    public static User createAccount (String username, String password, String name, String option){
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
                    ArrayList<String> lines = Server.getTextInfo(f);
                    for (String x : lines) {
                        pw.println(x);
                    }
                }

                pw.println(username);
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


    // Copies a file information into another file
    public static void copyFileToFile(File src, File dest)
    {
        try {
            ArrayList<String> srcLines = Server.getTextInfo(src);
            PrintWriter pw = new PrintWriter(new FileWriter(dest, false));
            for (String x : srcLines) {
                pw.println(x);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

