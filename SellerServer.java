import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Customer purchase class that is used for the view sellers method
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Project 5</p>
 *
 * @author Stanley Kim
 * @version December 7, 2022
 */
class CustomerPurchases { // Class used in the viewSeller method
    public String customer;
    public ArrayList<String> purchases;
    public CustomerPurchases(String customer, ArrayList<String> purchases) {
        this.customer = customer;
        this.purchases = purchases;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ArrayList<String> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<String> purchases) {
        this.purchases = purchases;
    }
}


/**
 * A Product Purchases class is used in the viewSellers method
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Project 5</p>
 *
 * @author Stanley Kim
 * @version December 7, 2022
 */
class ProductPurchases { // Class used in the viewSeller method
    public String product;
    public int purchaseNumber;
    public ProductPurchases(String product, int purchaseNumber) {
        this.product = product;
        this.purchaseNumber = purchaseNumber;
    }

    public String getProduct() {
        String[] toReturn = product.split(",");
        return String.format("Name: %s, Description: %s, Quantity Available: %s, Price: %s", toReturn[0], toReturn[2], toReturn[3], toReturn[4]);
    }

    public String getProductName() {
        String[] toReturn = product.split(",");
        return toReturn[0];
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }
}


/**
 * A PurchaseInformation class is used for the viewSales method
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Project 5</p>
 *
 * @author Stanley Kim
 * @version December 7, 2022
 */
class PurchaseInformation { //Class used in the viewSales method.
    public String customer;
    public ArrayList<String> purchases;
    public ArrayList<Integer> amountPurchased;
    public ArrayList<Double> price;
    public PurchaseInformation(String customer, ArrayList<String> purchases,
                               ArrayList<Integer> amountPurchased, ArrayList<Double> price) {
        this.customer = customer;
        this.purchases = purchases;
        this.amountPurchased = amountPurchased;
        this.price = price;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ArrayList<String> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<String> purchases) {
        this.purchases = purchases;
    }

    public ArrayList<Integer> getAmountPurchased() {
        return amountPurchased;
    }

    public void setAmountPurchased(ArrayList<Integer> amountPurchased) {
        this.amountPurchased = amountPurchased;
    }

    public ArrayList<Double> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Double> price) {
        this.price = price;
    }
}

public class SellerServer {


    // Seller Option 2
    public static boolean createNewItem(String info, String market) {

        String[] productInfo = info.split(";");
        File f = new File(market + " Market.txt");
        Product product = new Product(productInfo[0], market, productInfo[1], Integer.parseInt(productInfo[2]), Double.parseDouble(productInfo[3]));
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();

            PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
            pw.println(product.toString());
            for (String x : lines) {
                pw.println(x);
            }
            pw.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static boolean deleteItem(String item, String market) {
        String[] productInfo = item.split(",");
        File f = new File(market + " Market.txt");
        Product product = new Product(productInfo[0], productInfo[1], productInfo[2], Integer.parseInt(productInfo[3]), Double.parseDouble(productInfo[4]));
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
            int index = -1;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains(product.toString())) {
                    index = i;
                }
            }
            if (index != -1) {
                lines.remove(index);
            } else {
                return false;
            }

            PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
            for (String x : lines) {
                pw.println(x);
            }
            pw.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean editItem(String item, String newInfo, String market) {
        String[] productInfo = item.split(",");
        File f = new File(market + " Market.txt");
        Product product = new Product(productInfo[0], productInfo[1], productInfo[2],
                Integer.parseInt(productInfo[3]), Double.parseDouble(productInfo[4]));
//
//        System.out.println(newInfo);
        String[] newProductInfo = newInfo.split(";");
//        System.out.println(Arrays.toString(newProductInfo));
//        System.out.println(newProductInfo.length);
//        System.out.println(newProductInfo[2]);
//        System.out.println(newProductInfo[3]);

        Product newProduct = new Product(newProductInfo[0], market,
                newProductInfo[1], Integer.parseInt(newProductInfo[2]), Double.parseDouble(newProductInfo[3]));
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
            int index = -1;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains(product.toString())) {
                    index = i;
                }
            }
            if (index != -1) {
                lines.remove(index);
            } else {
                return false;
            }
            PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
            pw.println(newProduct);
            for (String x : lines) {
                pw.println(x);
            }
            pw.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Seller Option 3
    public static String viewSales(String market) {
        String salesInformation = "";
        try {
            FileReader fr = new FileReader(market + " Market.txt");
            BufferedReader br = new BufferedReader(fr);
            int delineate = 0;
            ArrayList<PurchaseInformation> purchaseInformation = new ArrayList<>(); // ArrayList of customers, purchases
            ArrayList<String[]> purchases = new ArrayList<>(); // ArrayList for each purchase in the store
            String line = br.readLine();
            double totalRevenue = 0;
            while (line != null) {
                if (line.contains("------")) {
                    delineate++; // Increments delineate if it iterates through the given line
                    line = br.readLine();
                }
                while (delineate == 0 && !line.contains("------")) {
                    line = br.readLine();
                }
                while (delineate == 1 && !line.contains("------")) {
                    purchaseInformation.add(new PurchaseInformation(line, new ArrayList<>(), new ArrayList<>(),
                            new ArrayList<>())); // Adds a new customer
                    line = br.readLine();
                }
                while (delineate == 2 && line != null) {
                    String[] purchase = line.split(","); // Creates an array of the purchase
                    purchases.add(purchase); // Adds the purchase
                    line = br.readLine();
                }
            }
            br.close();
            for (PurchaseInformation currentInformation : purchaseInformation) {
                for (String[] currentPurchase : purchases) {
                    if (currentInformation.getCustomer().equals(currentPurchase[5])) {
                        ArrayList<String> currentCustomerPurchases = currentInformation.getPurchases();
                        currentCustomerPurchases.add(currentPurchase[0]);
                        currentInformation.setPurchases(currentCustomerPurchases);
                        ArrayList<Integer> currentPurchaseAmount = currentInformation.getAmountPurchased();
                        currentPurchaseAmount.add(Integer.parseInt(currentPurchase[3]));
                        currentInformation.setAmountPurchased(currentPurchaseAmount);
                        ArrayList<Double> currentPrice = currentInformation.getPrice();
                        currentPrice.add(Double.parseDouble(currentPurchase[4]));
                        currentInformation.setPrice(currentPrice);

                    }
                }
            } // Creates an arraylist of PurchaseInformation objects with customer, product, amount bought, and price

            for (int i = 0; i < purchaseInformation.size(); i++) {
                double customerAmountSpent = 0;
                PurchaseInformation currentInformation = purchaseInformation.get(i);
                salesInformation = salesInformation + String.format("%d. ", i + 1);
                salesInformation = salesInformation + String.format("%s bought:\n", currentInformation.getCustomer());
                for (int j = 0; j < currentInformation.getPurchases().size(); j++) {
                    double amountSpent = currentInformation.getAmountPurchased().get(j) *
                            currentInformation.getPrice().get(j);
                    salesInformation = salesInformation +
                            String.format("%d %s for a total of %.2f\n", currentInformation.getAmountPurchased().get(j),
                                    currentInformation.getPurchases().get(j), amountSpent);
                    customerAmountSpent = customerAmountSpent + amountSpent;
                    totalRevenue = totalRevenue + amountSpent;
                }
                salesInformation = salesInformation + String.format("%s spent %.2f dollars\n",
                        currentInformation.getCustomer(), customerAmountSpent);
            } // Prints the list of product each customer bought and the money spent on each product
            if (totalRevenue == 0.00) {
                salesInformation = salesInformation + "There is no revenue generated from this store.\n";
            } else {
                salesInformation = salesInformation + String.format("The total revenue made from the store is: %.2f\n",
                        totalRevenue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return salesInformation;
    } // Prints the total revenue made from the store

    // Seller Option 4
    public static String viewSeller(String market) {
        String marketInformation = "";
        int totalMarketPurchases = 0;
        try {
            FileReader fr = new FileReader(market + " Market.txt");
            BufferedReader br = new BufferedReader(fr);
            int delineate = 0; // Checks to see where in the market store br is
            ArrayList<ProductPurchases> productPurchases = new ArrayList<>(); // ArrayList of products and purchases
            ArrayList<CustomerPurchases> customerPurchases = new ArrayList<>(); // ArrayList of customers and purchases
            ArrayList<String[]> purchases = new ArrayList<>(); // ArrayList for each purchase in the store
            String line = br.readLine();
            while (line != null) {
                if (line.contains("------")) {
                    delineate++; // Increments delineate if it iterates through the given line
                    line = br.readLine();
                }
                while (delineate == 0 && !line.contains("------")) {
                    productPurchases.add(new ProductPurchases(line, 0)); // Adds a new product
                    line = br.readLine();
                }
                while (delineate == 1 && !line.contains("------")) {
                    customerPurchases.add(new CustomerPurchases(line, new ArrayList<>())); // Adds a new customer
                    line = br.readLine();
                }
                while (delineate == 2 && line != null) {
                    String[] purchase = line.split(","); // Creates an array of the purchase
                    purchases.add(purchase); // Adds the purchase
                    line = br.readLine();
                }
            }
            br.close();

            for (CustomerPurchases currentCustomer : customerPurchases) {
                String customerName = currentCustomer.getCustomer();
                for (String[] currentPurchase : purchases) {
                    if (customerName.equals(currentPurchase[5])) {
                        ArrayList<String> currentCustomerPurchases = currentCustomer.getPurchases();
                        currentCustomerPurchases.add(currentPurchase[0]);
                        currentCustomer.setPurchases(currentCustomerPurchases);
                    }
                }
            } // Assigns each purchase in the store to a customer

            for (ProductPurchases currentProduct : productPurchases) {
                String productName = currentProduct.getProductName();
                for (String[] currentPurchase : purchases) {
                    if (productName.equals(currentPurchase[0])) {
                        int number = currentProduct.getPurchaseNumber();
                        int purchaseNumber =
                                number + Integer.parseInt(currentPurchase[3]);
                        currentProduct.setPurchaseNumber(purchaseNumber);
                    }
                }
            } // Finds the total number of times each product has been purchased

            marketInformation = marketInformation +
                    "The list of products and the amount of times they have been purchased is: \n";
            for (int i = 0; i < productPurchases.size(); i++) {
                ProductPurchases currentProduct = productPurchases.get(i);
                marketInformation = marketInformation + String.format("%d. ", i + 1);
                marketInformation = marketInformation + currentProduct.getProduct() + "\n";
                marketInformation = marketInformation + String.format("This product has been purchased %d times.\n",
                        currentProduct.getPurchaseNumber());
                System.out.println(3);
                totalMarketPurchases += currentProduct.getPurchaseNumber();
            } // Prints the list of products in the store and the amount of times they have been purchased

            if (totalMarketPurchases == 0) {
                marketInformation = marketInformation + "No purchases have been made from this store.\n";
            } else {
                marketInformation = marketInformation + "The customers who have purchased from this store are: \n";
                for (int i = 0; i < customerPurchases.size(); i++) {
                    CustomerPurchases currentCustomer = customerPurchases.get(i);
                    marketInformation = marketInformation + String.format("%d. ", i + 1);
                    marketInformation = marketInformation + String.format("%s:\n", currentCustomer.getCustomer());
                    for (int j = 0; j < currentCustomer.getPurchases().size(); j++) {
                        marketInformation = marketInformation + (currentCustomer.getPurchases().get(j) + "\n");
                    }
                } // Prints the list of customers who have made purchases and their products
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return marketInformation;
    }



    public static String customerShoppingCarts() {
        try {
            File customerFile = new File("Customers.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(customerFile));
            String line = "";
            String concat = "";

            while ((line = bfr.readLine()) != null) {
                concat = concat + ";" + line;
            }
            concat = concat.substring(1);
            return concat;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }






}
