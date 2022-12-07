import java.io.*;
public class SellerServer {
    public static String customerShoppingCarts() throws IOException {
        File customerFile = new File("Customers.txt");
        BufferedReader bfr = new BufferedReader(new FileReader(customerFile));
        String line = "";
        String concat = "";

        while ((line = bfr.readLine()) != null) {
            concat = concat + ";" + line;
        }
        concat = concat.substring(1);
        return concat;

    }

    public static String deleteMarketplace() {
        File markets = new File("Markets.txt");
        String concat = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(markets));
            String line = "";

            while ((line = bfr.readLine()) != null) {
                concat = concat + ";" + line;
            }
            concat = concat.substring(1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return concat;

    }

}
