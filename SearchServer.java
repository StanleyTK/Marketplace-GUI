import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A GUI Server for Marketplace
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Homework 11 -- Challenge</p>
 *
 * @author Stanley Kim
 * @version November 19, 2022
 */




public class SearchServer {


    public static void main(String[] args) {
        boolean createNewAccount = false;
        BufferedReader br;
        PrintWriter writer;
        User user = null;

        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Waiting for the client to connect...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");
            br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            writer = new
                    PrintWriter(socket.getOutputStream());
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
                    String[] info = line.split(";");
                    JOptionPane.showMessageDialog(null, "Welcome " + info[2],
                            "Welcome!", JOptionPane.INFORMATION_MESSAGE);
                    user = getUser(line);
                    writer.println(user.toString());
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
                while (true) {
                    option = br.readLine();

                    if (option.equals("1")) {
                        String toReturn = viewMarket();
                        writer.println(toReturn);
                        writer.flush();
                    } else if (option.equals("2")) {
                        //TODO Search for Products
                    } else if (option.equals("3")) {
                        String toReturn = sortPrice();
                        writer.println(toReturn);
                        writer.flush();
                    } else if (option.equals("4")) {
                        String toReturn = sortQuantity();
                        writer.println(toReturn);
                        writer.flush();
                    } else if (option.equals("5")) {
                        //TODO View Dashboard
                    } else if (option.equals("6")) {
                        //TODO Export File with Purchase History
                    } else if (option.equals("7")) {
                        //TODO Add Items to the Shopping Cart
                    } else if (option.equals("8")) {
                        //TODO Remove Items to the Shopping Cart
                    } else if (option.equals("9")) {
                        //TODO Purchase All Items in the Shopping Cart
                    } else if (option.equals("10")) {
                        String toReturn = viewShoppingCart((Customer) user);
                        writer.println(toReturn);
                        writer.flush();
                    } else if (option.equals("11")) {
                        //TODO More Information
                    } else {
                        break;
                    }
                }


            } else {
                // Seller Options
                option = br.readLine();
                if (option.equals("1")) {

                } else if (option.equals("2")) {

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }






    }



    // Customer Option 1 and Seller Option 1
    private static String viewMarket() {
        String line;
        String printer = "";
        ArrayList<Product> products = new ArrayList<>();

        ArrayList<String> storeNames = new ArrayList<>();
        try {
            File markets = new File("Markets.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(markets));
            while ((line = bfr.readLine()) != null) { //Takes name of all markets in file
                storeNames.add(line); //adds to arraylist
            }

            bfr.close();
            for (String storeName : storeNames) {
                File f = new File(storeName + " Market.txt");
                BufferedReader productReader = new BufferedReader(new FileReader(f));
                line = productReader.readLine();
                while (line != null) { //iterates through lines of files and adds them to string
                    if (!line.contains("-----")) {
                        products.add(getProduct(line));
                        line = productReader.readLine();
                    } else {
                        break;
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("There are no stores/products found. Sorry");
        }
        for (int i = 0; i < products.size(); i++) {
            printer = printer + products.get(i).toString() + ";";
        }
        System.out.println(printer);
        return printer;
    }


    // Customer Option 3
    private static String sortPrice() {
        String line;
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<String> storeNames = new ArrayList<>();
        try {
            File markets = new File("Markets.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(markets));
            while ((line = bfr.readLine()) != null) { //Takes name of all markets in file
                storeNames.add(line); //adds to arraylist
            }

            bfr.close();
            for (String storeName : storeNames) {
                File f = new File(storeName + " Market.txt");
                BufferedReader productReader = new BufferedReader(new FileReader(f));
                line = productReader.readLine();
                while (line != null) { //iterates through lines of files and adds them to string
                    if (!line.contains("------")) {
                        products.add(getProduct(line));
                        line = productReader.readLine();
                    } else {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("There are no stores/products found. Sorry");

        }

        Product[] temp = new Product[products.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = products.get(i);
        }

        for (int i = 0; i < temp.length; i++) {
            for (int j = temp.length - 1; j > i; j--) {
                if (temp[i].getPrice() > temp[j].getPrice()) {

                    double tempI = temp[i].getPrice();
                    double tempJ = temp[j].getPrice();
                    if (tempI > tempJ) {
                        Product yolo = temp[i];
                        temp[i] = temp[j];
                        temp[j] = yolo;
                    }

                }

            }
        }
        String toReturn = "";
        for (Product product : temp) {
            toReturn = toReturn + product.toString() + ";";

        }
        System.out.println(toReturn);
        return toReturn;
    }


    // Customer Option 4

    private static String sortQuantity() {
        String line;
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<String> storeNames = new ArrayList<>();
        try {
            File markets = new File("Markets.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(markets));
            while ((line = bfr.readLine()) != null) { //Takes name of all markets in file
                storeNames.add(line); //adds to arraylist
            }

            bfr.close();
            for (String storeName : storeNames) {
                File f = new File(storeName + " Market.txt");
                BufferedReader productReader = new BufferedReader(new FileReader(f));
                line = productReader.readLine();
                while (line != null) { //iterates through lines of files and adds them to string
                    if (!line.contains("----")) {
                        products.add(getProduct(line));
                        line = productReader.readLine();
                    } else {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("There are no stores/products found. Sorry");
        }

        Product[] temp = new Product[products.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = products.get(i);
        }
        for (int i = 0; i < temp.length; i++) {
            for (int j = temp.length - 1; j > i; j--) {
                if (temp[i].getQuantity() > temp[j].getQuantity()) {

                    int tempI = temp[i].getQuantity();
                    int tempJ = temp[j].getQuantity();
                    if (tempI > tempJ) {
                        Product yolo = temp[i];
                        temp[i] = temp[j];
                        temp[j] = yolo;
                    }

                }

            }
        }
        String toReturn = "";
        for (Product product : temp) {
            toReturn = toReturn + product.toString() + ";";

        }
        System.out.println(toReturn);
        return toReturn;
    }

    // Customer Option 10
    private static String viewShoppingCart(Customer user) {
        String returnable = "";
        try {
            ArrayList<Product> lines = new ArrayList<>();
            BufferedReader bfr = new BufferedReader(new FileReader(user.getUsername() + "'s File.txt"));
            String line = bfr.readLine();

            while (line != null) {
                if (!line.contains("User: ") && !line.contains("Name: ")) {
                    Product product = SearchServer.getProduct(line);
                    returnable = returnable + "Product: %s, Description: %s, " +
                            "Price: %.2f, Quantity: %d\n";
                    String.format(returnable, product.getName(),
                            product.getDescription(), product.getPrice(), product.getQuantity());
                    lines.add(product);
                }
                line = bfr.readLine();
            }
            if (lines.size() == 0) {
                return "You do not have any products in your shopping cart.";
            }

        } catch (IOException e) {
            return "There are no stores/products found. Sorry";
        }
        return returnable;
    }


    // Checks the login information, and checks if the info matches the login
    public static String verifyLogin(String username, String password) throws IOException {
        ArrayList<String> lines = getTextInfo(new File("login.txt"));
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
                    ArrayList<String> lines = getTextInfo(f);
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


    // Returns the product from the market
    public static Product getProduct(String line) {
        String[] contents = line.split(",");
        return new Product(contents[0], contents[1], contents[2],
                Integer.parseInt(contents[3]), Double.parseDouble(contents[4]));
    }



    // Easier implementation to access a txt file
    public static ArrayList<String> getTextInfo(File f) throws IOException {
        ArrayList<String> toReturn = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        while (line != null) {
            toReturn.add(line);
            line = br.readLine();
        }
        return toReturn;

    }



    public static User getUser(String info) throws IOException {
        String[] contents = info.split(";");
        ArrayList<Product> products = new ArrayList<>();
        String user = "Customer";

        String fileName = contents[0] + "'s File.txt";
        ArrayList<String> lines = getTextInfo(new File(fileName));

        for (String productInfo : lines) {
            if (!productInfo.contains("Name:") && !productInfo.contains("User:") && productInfo.contains("User: Seller")) {
                products.add(getProduct(productInfo));
            } else if (productInfo.contains("User: Seller")) {
                user = "Seller";
            }
        }


        ShoppingCart shoppingCart = new ShoppingCart(products);

        if (user.equals("Customer")) {
            return new Customer(contents[2], contents[0], contents[1], shoppingCart.getCartItems());
        } else {
            return new Seller(contents[2], contents[0], contents[1]);
        }



    }





}

