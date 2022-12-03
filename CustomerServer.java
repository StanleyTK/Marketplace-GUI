import javax.swing.*;
import javax.swing.table.TableColumn;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;

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

    // Customer Option 7
    public static Object[] addShoppingCartItem(User user) throws IOException { // Customer Option 7
        String line;

        ArrayList<Object> toReturnArrayList = new ArrayList<>();

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
                productReader.close();

            }
        } catch (IOException e) {
            System.out.println("There are no stores/products found. Sorry");
        }
        for (Product product : products) {
            toReturnArrayList.add(product.toString());
        }
        Object[] toReturn = (toReturnArrayList.toArray());

        return toReturn;
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
                return "You do not have any products in your shopping cart.";
            }
            bfr.close();

        } catch (IOException e) {
            return "There are no stores/products found. Sorry";
        }
        toReturn = toReturn.substring(0 , toReturn.length() - 1);
        return toReturn;
    }
}
