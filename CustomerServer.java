import javax.swing.*;
import javax.swing.table.TableColumn;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomerServer {


    // Customer Option 1 and Seller Option 1
    public static String viewMarket() {
        String line;
        String printer = "";
        ArrayList<Product> products = new ArrayList<>();

        ArrayList<String> storeNames = new ArrayList<>();
        try {
            storeNames = SearchServer.getTextInfo(new File("Markets.txt"));
            for (String storeName : storeNames) {
                File f = new File(storeName + " Market.txt");
                BufferedReader productReader = new BufferedReader(new FileReader(f));
                line = productReader.readLine();
                while (line != null) { //iterates through lines of files and adds them to string
                    if (!line.contains("-----")) {
                        products.add(SearchServer.getProduct(line));
                        line = productReader.readLine();
                    } else {
                        break;
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("There are no stores/products found. Sorry");
        }
        for (Product product : products) {
            printer = printer + product.toString() + ";";
        }
        return printer;

    }


    // Customer Option 3
    static String sortPrice() {
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
                        products.add(SearchServer.getProduct(line));
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

    static String sortQuantity() {
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
                        products.add(SearchServer.getProduct(line));
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

    // Customer Option 5
    public static JFrame viewCustomer() {
        JFrame jFrame = null;
        try {
            String customerName = JOptionPane.showInputDialog(null,
                    "Please enter your username.", "Name", JOptionPane.QUESTION_MESSAGE); // Gets username
            FileReader fr = new FileReader("Markets.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            ArrayList<String[]> products = new ArrayList<>(); // ArrayList for each product in the store
            ArrayList<String[]> purchases = new ArrayList<>(); // ArrayList for each purchase in the store
            ArrayList<String[]> customerPurchases = new ArrayList<>(); // ArrayList for each purchase the customer made
            JTextArea dashboard = new JTextArea();
            while (line != null) {
                FileReader fileReader = new FileReader(line + " Market.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                int delineate = 0; // Checks to see where in the market store bufferedReader is
                String marketLine = bufferedReader.readLine();
                while (marketLine != null) {
                    if (marketLine.contains("------")) {
                        delineate++; // Increments delineate if it iterates through the given line
                        marketLine = bufferedReader.readLine();
                    }
                    while (delineate == 0 && !marketLine.contains("------")) {
                        String[] product = marketLine.split(","); // Creates an array of the product
                        products.add(product);
                        marketLine = bufferedReader.readLine(); // Creates an arraylist of products available
                    }
                    while (delineate == 1 && !marketLine.contains("------")) {
                        marketLine = bufferedReader.readLine();
                    }
                    while (delineate == 2 && marketLine != null) {
                        String[] purchase = marketLine.split(","); // Creates an array of the purchase
                        purchases.add(purchase);
                        marketLine = bufferedReader.readLine(); // Creates an arraylist of all the purchases
                    }
                }
                bufferedReader.close();
                int productsNumber = products.size();
                String store = String.format("%s has %d products for sale.\n", line, productsNumber);
                dashboard.append(store);
                for (int i = 0; i < products.size(); i++) {
                    String product = String.format("%d: %s\n", i + 1, Arrays.toString(products.get(i)));
                    dashboard.append(product);
                } // Adds all the products available for sale to the JTextArea
                for (String[] currentPurchase : purchases) {
                    if (customerName.equals(currentPurchase[5])) {
                        customerPurchases.add(currentPurchase);
                    }
                } // Checks if the customer has purchased, then adds to the arraylist of customer purchases
                String purchase = String.format("From %s, %s has purchased the following products:\n",
                        line, customerName);
                dashboard.append(purchase);
                for (int i = 0; i < customerPurchases.size(); i++) {
                    String customerPurchase = String.format("%d: %s\n", i + 1,
                            Arrays.toString(customerPurchases.get(i)));
                    dashboard.append(customerPurchase);
                } // Adds the products that the customer has purchased from the store
                if (customerPurchases.isEmpty()) {
                    dashboard.append("There have been no purchases from this store.\n");
                }
                line = br.readLine();
            }
            br.close();
            JScrollPane scrollable = new JScrollPane(dashboard);
            jFrame = new JFrame("Customer Dashboard");
            jFrame.setSize(1000, 500);
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jFrame.getContentPane().add(scrollable);
            jFrame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jFrame;
    }

    // Customer Option 10
    public static String viewShoppingCart(Customer user) {
        String toReturn = "";
        String format = "%s,%s,%s,%d,%.2f;";
        try {
            ArrayList<Product> lines = new ArrayList<>();
            BufferedReader bfr = new BufferedReader(new FileReader(user.getUsername() + "'s File.txt"));
            String line = bfr.readLine();

            while (line != null) {
                if (line.contains(",")) {
                    Product product = SearchServer.getProduct(line);
                    toReturn = String.format(format, product.getName(), product.getStore() ,
                            product.getDescription(), product.getQuantity(), product.getPrice()) + toReturn;
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
        toReturn = toReturn.substring(0 , toReturn.length() - 1);
        return toReturn;
    }

}
