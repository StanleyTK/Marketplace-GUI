import javax.swing.*;
import javax.swing.table.TableColumn;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CustomerServer {


    // Customer Option 1 and Seller Option 1
    static JFrame viewMarket() {
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
        for (int i = 0; i < products.size(); i++) {
            printer = printer + products.get(i).toString() + ";";
        }
        String[] printerSplit = printer.split(";"); // Splits the list into an array of individual products
        ArrayList<String[]> toReturnArrayList = new ArrayList<>();
        for (String s : printerSplit) {
            String[] printerSplitString = s.split(",");
            toReturnArrayList.add(printerSplitString);
        } // Adds the details of each product to the array list

        String[][] toReturnSize = new String[toReturnArrayList.size()][5];
        Object[][] rowArray = toReturnArrayList.toArray(toReturnSize); // Creates a 2D array for the product details
        Object[] columnArray = {"Product Name", "Store Name", "Description", "Quantity Available", "Price"};

        JTable table = new JTable(rowArray, columnArray); // Creates a table with the product list

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
        final JFrame jFrame = new JFrame("Marketplace");
        jFrame.setSize(1000, 500);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.getContentPane().add(scrollPane);
        TableColumn tableColumn;
        for (int i = 0; i < 5; i++) {
            tableColumn = table.getColumnModel().getColumn(i);
            if (i == 2) {
                tableColumn.setPreferredWidth(150);
            } else if (i == 3) {
                tableColumn.setPreferredWidth(50);
            } else {
                tableColumn.setPreferredWidth(100);
            }
        }
        jFrame.setVisible(true); // Creates a JFrame to view the table

        return jFrame;
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

    // Customer Option 10
    static String viewShoppingCart(Customer user) {
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

}
