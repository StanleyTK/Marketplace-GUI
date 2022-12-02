import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class SellerServer {


    // Seller Option 2
    public static void createNewItem() {
        try {
            ArrayList<String> lines = SearchServer.getTextInfo(new File("Markets.txt"));
            String[] markets = new String[lines.size()];
            for (int i = 0; i < markets.length; i++) {
                markets[i] = lines.get(i);
            }



            JFrame jFrame = new JFrame("Create new Product");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteItem() {
    }

    public static void editItem() {
    }
}
