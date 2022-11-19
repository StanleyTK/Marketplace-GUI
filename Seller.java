import java.util.ArrayList;
import java.io.*;
public class Seller extends User {
    private ArrayList<String> customerNames = new ArrayList<>();

    public Seller(String customerName, String username, String password) {
        super(customerName, username, password);
        File f = new File("Customers.txt");
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            while ((line = br.readLine()) != null) { //iterates through lines of files and adds them to string
                customerNames.add(line);
            }


        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void setProducts(ArrayList<Product> products) throws IOException {
        for (Product product : products) {
            String market = product.getStore();
            File f = new File(market + " Market.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            StringBuilder contents = new StringBuilder();
            while (line != null) {
                contents.append(line);
                line = bfr.readLine();
            }
            FileOutputStream fos = new FileOutputStream(f);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(contents);
            pw.println(product);
            bfr.close();
            pw.close();
        }
    }


    @Override
    public String toString() {
        return "Seller{" +
                "customerNames=" + customerNames +
                '}';
    }
}
