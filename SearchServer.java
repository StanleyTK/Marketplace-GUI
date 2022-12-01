import javax.swing.*;
import javax.swing.table.TableColumn;
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
                        String toReturn = CustomerServer.viewMarket();
                        writer.println(toReturn);
                        writer.flush();

                    } else if (option.equals("2")) {
                        //TODO Search for Products
                    } else if (option.equals("3")) {
                        String toReturn = CustomerServer.sortPrice();
                        writer.println(toReturn);
                        writer.flush();
                    } else if (option.equals("4")) {
                        String toReturn = CustomerServer.sortQuantity();
                        writer.println(toReturn);
                        writer.flush();
                    } else if (option.equals("5")) {
                        //TODO View Dashboard
                    } else if (option.equals("6")) {
                        //export purchase history
                        try {
                            String toReturn = CustomerServer.exportPurchaseHistory((Customer) user);
                            writer.println(toReturn);
                            writer.println("finished");
                            writer.flush();
                        } catch (IOException e) {
                            writer.println("Failed");
                            writer.flush();
                        }
                    } else if (option.equals("7")) {
                        //TODO Add Items to the Shopping Cart
                    } else if (option.equals("8")) {
                        //TODO Remove Items to the Shopping Cart
                    } else if (option.equals("9")) {
                        //TODO Purchase All Items in the Shopping Cart
                    } else if (option.equals("10")) {
                        String toReturn = CustomerServer.viewShoppingCart((Customer) user);
                        writer.println(toReturn);
                        writer.flush();
                    } else if (option.equals("11")) {
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
                    } else {
                        break;
                    }
                }


            } else {
                // Seller Options
                while (true) {
                    option = br.readLine();

                    if (option.equals("1")) {
                        String toReturn = CustomerServer.viewMarket();
                        writer.println(toReturn);
                        writer.flush();
                    } else if (option.equals("2")) {
                        //TODO Edit Product from Store
                    } else if (option.equals("3")) {
                        //TODO View Sales by Store

                    } else if (option.equals("4")) {
                        //TODO View Dashboard
                    } else if (option.equals("5")) {
                        //TODO Import Products CSV File
                    } else if (option.equals("6")) {
                        //TODO Export Products CSV File
                    } else if (option.equals("7")) {
                        //TODO View Shopping Carts
                    } else if (option.equals("8")) {
                        //TODO Create Market
                    } else if (option.equals("9")) {
                        //TODO Delete Market
                    } else if (option.equals("10")) {
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
                    } else if (option.equals("11")) {
                        break;
                    }
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }



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

