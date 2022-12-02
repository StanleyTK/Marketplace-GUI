import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

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

    public static void deleteItem() {
    }

    public static void editItem() {
    }
}
