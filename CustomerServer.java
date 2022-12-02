import javax.swing.*;
import javax.swing.table.TableColumn;
import java.io.*;
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
        System.out.println(printer);
        return printer;

    }



    // Customer Option 2
    public static String searchProducts(String option, String search) {
        String results = "";
        try {
            ArrayList<String> markets = Login.getTextInfo(new File("Markets.txt"));
            for (String market : markets) {
                BufferedReader br = new BufferedReader(new FileReader(new File(market + " Market.txt")));
                String line = br.readLine();
                ArrayList<String> lines = new ArrayList<>();


                while (line != null) {
                    if (!line.contains("-----")) {
                        lines.add(line);
                        line = br.readLine();
                    } else {
                        break;
                    }
                }

                for (String productInfo : lines) {
                    Product product = SearchServer.getProduct(productInfo);
                    if (product.getName().contains(search) && option.equals("Name")) {
                        results += productInfo + ";";
                    } else if (product.getDescription().contains(search) && option.equals("Description")) {
                        results += productInfo + ";";
                    } else if (product.getStore().contains(search) && option.equals("Store")) {
                        results += productInfo + ";";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

        return results;
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
    public static String viewCustomer(Customer customer) {
        String customerName = customer.getCustomerName();
        String purchaseHistory = "";
        try {
            FileReader fr = new FileReader("Markets.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            ArrayList<String[]> products = new ArrayList<>(); // ArrayList for each product in the store
            ArrayList<String[]> purchases = new ArrayList<>(); // ArrayList for each purchase in the store
            ArrayList<String[]> customerPurchases = new ArrayList<>(); // ArrayList for each purchase the customer made
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
                purchaseHistory = purchaseHistory + String.format("%s has %d products for sale.\n",
                        line, productsNumber);
                for (int i = 0; i < products.size(); i++) {
                    purchaseHistory = purchaseHistory + String.format("%d: %s\n",
                            i + 1, Arrays.toString(products.get(i)));
                } // Adds all the products available for sale to the purchaseHistory string
                for (String[] currentPurchase : purchases) {
                    if (customerName.equals(currentPurchase[5])) {
                        customerPurchases.add(currentPurchase);
                    }
                } // Checks if the customer has purchased, then adds to the arraylist of customer purchases
                purchaseHistory = purchaseHistory +
                        String.format("From %s, %s has purchased the following products:\n", line, customerName);
                for (int i = 0; i < customerPurchases.size(); i++) {
                    purchaseHistory = purchaseHistory + String.format("%d: %s\n", i + 1,
                            Arrays.toString(customerPurchases.get(i)));
                } // Adds the products that the customer has purchased from the store
                if (customerPurchases.isEmpty()) {
                    purchaseHistory = purchaseHistory + "There have been no purchases from this store.\n";
                }
                products.clear();
                purchases.clear();
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return purchaseHistory;
    }

    // Customer Option 6

    static String exportPurchaseHistory(Customer customer) throws IOException {
        ArrayList<Product> products = new ArrayList<Product>();
        File marketsFile = new File("Markets.txt");
        BufferedReader bfr = new BufferedReader(new FileReader(marketsFile));
        ArrayList<String> markets = new ArrayList<String>();
        String line = bfr.readLine();
        while (line != null) {
            markets.add(line);
            line = bfr.readLine();
        }
        bfr.close();
        for (int i = 0; i < markets.size(); i++) {
            File file = new File(markets.get(i) + " Market.txt");
            bfr = new BufferedReader(new FileReader(file));
            line = bfr.readLine();
            while (!line.contains("------")) {
                line = bfr.readLine();
            }
            line = bfr.readLine();
            while (!line.contains("------")) {
                line = bfr.readLine();
            }
            line = bfr.readLine();
            while (line != null) {
                String[] splitLine = line.split(",");
                if (splitLine[5].equals(customer.getUsername())) {
                    products.add(new Product(splitLine[0], splitLine[1], splitLine[2],
                            Integer.parseInt(splitLine[3]), Double.parseDouble(splitLine[4])));
                }
                line = bfr.readLine();
            }
        }
        bfr.close();
        String printer = "";
        printer += customer.getCustomerName() + "'s Purchase History\n";
        printer += "--------\n";
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            printer += "Purchased " + product.getQuantity() + " " + product.getName() + " for\n" +
                    product.getPrice();
            printer += String.format(" each ($%.2f) total from %s.\n", (product.getPrice() * (double) product.getQuantity()), product.getStore());
            printer += "Description: " + product.getDescription() + "\n\n";

        }
        return printer;
    }





    // Customer Option 8
    public static Object[] removeShoppingCart(User user) throws IOException { //Returns object array of shopping cart
        File f = new File(user.getUsername() + "'s File.txt");
        BufferedReader bfr = new BufferedReader(new FileReader(f));
        String line;
        ArrayList<String> shoppingCartArrayList = new ArrayList<String>();

        while ((line = bfr.readLine()) != null) {
            if (line.contains(",")) {
                shoppingCartArrayList.add(line);
            }
        }
        bfr.close();
        Object[] toReturn = (shoppingCartArrayList.toArray());
        return toReturn;
    }


    // Customer Option 9
    static String buyShoppingCart(Customer customer) {
        String printer = "";
        try {
            File f = new File(customer.getUsername() + "'s File.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            String name = bfr.readLine();
            String userType = bfr.readLine();
            ArrayList<Product> products = new ArrayList<Product>();
            String line = bfr.readLine();
            while (line != null) {
                products.add(SearchServer.getProduct(line));
                line = bfr.readLine();
            }
            bfr.close();
            PrintWriter pw = new PrintWriter(new FileOutputStream(f));
            pw.println(name);
            pw.println(userType);
            pw.close();
            double total = 0;
            for (Product product : products) {
                String market = product.getStore();
                f = new File(market + " Market.txt");
                FileOutputStream fos = new FileOutputStream(f, true);
                pw = new PrintWriter(fos);
                pw.println(product.toString() + "," + customer.getUsername());
                double cost = product.getPrice() * product.getQuantity();
                total += cost;
                pw.close();
                printer += String.format("Purchased %d %s for %.2f total\n",
                        product.getQuantity(), product.getName(), cost);
                bfr = new BufferedReader(new FileReader(f));
                line = bfr.readLine();
                String before = "";
                boolean isCustomer = false;
                while (!line.contains("------")) {
                    before += line + "\n";
                    line = bfr.readLine();
                }
                before += "--------\n";
                line = bfr.readLine();
                ArrayList<String> customers = new ArrayList<String>();
                while (!line.contains("------")) {
                    customers.add(line);
                    if (line.equals(customer.getUsername())) {
                        isCustomer = true;
                    }
                    line = bfr.readLine();
                }
                if (!isCustomer) {
                    customers.add(customer.getUsername());
                }
                String after = line;
                line = bfr.readLine();
                while (line != null) {
                    after += "\n" + line;
                    line = bfr.readLine();
                }
                bfr.close();
                pw = new PrintWriter(new FileOutputStream(f));
                pw.print(before);
                for (int i = 0; i < customers.size(); i++) {
                    pw.println(customers.get(i));
                }
                pw.println(after);
                pw.close();
            }
            printer += String.format("\nShopping cart purchased! Total cost: %.2f\n", total);
        } catch (IOException e) {
            printer = "An unexpected error occurred while accessing files!" + e.getMessage();
        }
        return printer;
    }

    // Customer Option 10
    public static String shoppingCartArray(Customer user) { // Returns string of shopping cart
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
                bfr.close();
                return "None";
            }
            bfr.close();

        } catch (IOException e) {
            return "Error";
        }
        toReturn = toReturn.substring(0 , toReturn.length() - 1);
        return toReturn;
    }


}
