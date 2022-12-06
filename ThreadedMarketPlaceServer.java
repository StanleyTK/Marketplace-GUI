import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A GUI Server for Marketplace
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Homework 11 -- Challenge</p>
 *
 * @author Stanley Kim
 * @version November 19, 2022
 */


public class ThreadedMarketPlaceServer {
    static final int PORT = 8888;
    static final Object object = new Object();

    public static void main(String[] args) {

//        Socket socket = null;
//        try {
//            ServerSocket server = new ServerSocket(PORT);
//
//            System.out.println("Waiting for the client to connect...");
//            socket = server.accept();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        }
//        new Thread(new MarketPlaceThread(socket)).start();

        Socket socket = null;
        try {
            ServerSocket server = new ServerSocket(PORT);
            server.setReuseAddress(true);

            System.out.println("Waiting for the client to connect...");
            while (true) {
                Socket client = server.accept();
                System.out.println("New Client connected!" + client.getInetAddress().getHostAddress());
                ClientHandler clientSock = new ClientHandler(client);
                new Thread(clientSock).start();

            }

        } catch (IOException e) {
            e.printStackTrace();

        }

    }


    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        AtomicInteger users = new AtomicInteger(0);


        // Constructor
        public ClientHandler(Socket socket)
        {

            this.clientSocket = socket;

        }
        public void run()
        {
            PrintWriter pw = null;
            BufferedReader br = null;

            Socket socket = null;
            try {
                pw = new PrintWriter(
                        clientSocket.getOutputStream(), true);
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.printf("Client %d connected!", users.incrementAndGet());

            } catch (IOException e) {
                e.printStackTrace();

            }
            new Thread(new MarketPlaceThread(clientSocket, pw, br)).start();
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





