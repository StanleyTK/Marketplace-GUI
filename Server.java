import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * A GUI Server for MarketPlace, accepts any Clients
 * Before starting any clients java class, start this module first
 *
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Project 5</p>
 *
 * @author Stanley Kim
 * @version December 7, 2022
 */


public class Server {
    static final int PORT = 7777;

    public static void main(String[] args) {

        // Server should be running without any breaks or errors
        ServerSocket server;
        try {
            server = new ServerSocket(PORT);
            server.setReuseAddress(true);
            System.out.println("Server is now active!");
            System.out.println("Waiting for the client to connect...");
            while (true) {
                try {
                    Socket client = server.accept();
                    System.out.println("Client connected!");
                    ClientHandler clientHandler = new ClientHandler(client);
                    new Thread(clientHandler).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * A Client Handler class that gets the information for the Clients
     *
     * <p>Purdue University -- CS18000 -- Fall 2022 -- Project 5</p>
     *
     * @author Stanley Kim
     * @version December 7, 2022
     */



    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        // Constructor
        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }
        public void run()
        {
            PrintWriter pw;
            BufferedReader br;
            try {
                pw = new PrintWriter(
                        clientSocket.getOutputStream(), true);
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                new Thread(new MarketPlaceThread(clientSocket, pw, br)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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



    // Gets the user using the login information
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





