# Marketplace GUI


Hello, this is the Project 5, Option 3!!
Authors: Stanley, Richard, Vid, Michael

Vidyaratnam - Submitted report on Brightspace

Stanley - Submitted presentation on Brightspace

Michael - Submitted code on Vocareum

# Instructions:

First, you need to start the server. to do so, run the main method in the Server class. Then, go over to any of the clients and run one of the classes. You must first create an account to use the program. To start off, we recommend to create a seller account and add a market, so that you can add products in the market that you created. You can create a Customer account after the changes. The program will prompt you for input/give instructions. You can simply click on the buttons and the program will tell you what to do.

# Class Descriptions:

Server class - 
Contains the main method for the Server part of the program, and has the ServerSocket.
Fields: static final int PORT = 7777 (server port)
Methods: main method (to start the server) getProduct (turns string into a product) getTextInfo (stores all lines in a .txt file into an arraylist)
getUser (returns a user from a specific type of string).
This class also contains the ClientHandler class which contains the public void run() method that is called when the server is started to help handle when Clients connect to the server.

--------

User class - 
Base class for a user.
Fields: private String customerName (stores the user's actual name) private String password (users password) private String username (stores the actual username of the user)
Methods: getters and setters for fields, and a toString which simply converts the information into a string.

File format for a user:
file name: username + "'s File.txt"
Name: customerName
User: (Customer or Seller)
The rest of the lines are the user's shopping cart

--------

Customer class extends User - 
Base class for a customer.
Fields: private ShoppingCart shoppingCart (users shopping cart) and all other fields specified in User.
Methods: shoppingCart getters and setters, toString which converts the information into a string.

Customers file format
File Name: "Customers.txt"
each line is the username of every customer created.

--------

Seller class extends User - 
Base class for a seller.
Fields: all fields specified in User.
Methods: toString which converts the sellers info into a string.

--------

Product class - 
Base template for a Product.
Fields: private String name (product's name) private String store (product's specific market) private String description (product's description) private int quantity (how much of the product) private double price (product's price).
Methods: getters and setters for all fields, toString which converts the product's information into a string.

--------

ShoppingCart class - 
class that handles user's shopping carts.
Fields: ArrayList<Product> cartItems (stores all user's cart items in to an arraylist).
Methods: getter and setter for cartItems, toString which converts the shopping cart into a string.

--------

Login class - 
class that handles when somebody is loggin into the program.
Fields: none.
Methods: public static void login (method that takes up the bulk of the class. Contains the GUI for logging in and communicates with the server) convertUser (makes a user) getTextInfo (stores all lines in a .txt file into an arraylist)

--------

Market class - 
Base class for a market.
Fields: private ArrayList<Product> products (all products in the specific marketplace) private String name (name of marketplace).
Methods: getters and setters for the fields, toString which converts the market into a string.
 
Market file format
File Name: name + " Market.txt"
All lines before the dashes are products currently in the market.
All lines between dashed lines are customers that have bought from the market.
All lines after all dashed lines are products purchased from the market.

--------

MarketPlaceThread class extends Thread - 
class that determines stuff like when buttons are clicked, and this communcates back and forth between the client.
Fields: protected Socket socket (socket to connect to server) protected PrintWriter writer (what is used to print information in strings to the client) protected BufferedReader br (used to read lines from the client).
Methods: public void run (method that is concurrent. It handles events like when certain buttons are clicked in order to access certain files and send information back to the client depending on that) verifyLogin (checks if the info matches the login) createAccount (creates an account based on the information given on create an account page) copyFileToFile (Copies a file's information into another file)

--------

CustomerOptions class - 
class that houses all button listeners for dealing when a button is pressed as a customer.
Fields: none.
Methods: public static void options (method that houses all button listeners for dealing when a button is pressed as a customer) protected static void showDashboard (method that creates a dashboard GUI based on purchase history) protected static void showTable (creates a GUI table of products based on a single string)

--------

CustomerServer class - 
class that houses all the methods used when each button is clicked as a customer.
Fields: none.
Methods: viewMarket (displays the market to the user) searchProducts (allows the user to search the market by product) sortPrice (sorts the market by price and displays to the user) sortQuantity (sorts the market by quantity and displays to user) viewCustomer (returns information about a dashboard with more in depth information about them) exportPurchaseHistory (exports the user's purchase history into a csv file) buyShoppingCart (purchases the user's entire shopping cart)
viewShoppingCart (aids in displaying the current user's shopping cart) 

--------

SellerOptions class - 
class that houses all button listeners for dealing when a button is pressed as a seller.
Fields: none.
Methods: public static void options (method that houses all button listeners for dealing when a button is pressed as a seller) private static String marketName (creates a simple JOptionPane that asks the user for the name of a market)

--------
 
CustomerPurchases class - 
class used in viewSeller method.
Fields: public String customer (customer associated) public ArrayList<String> purchases (list of purchases)
Methods: getters and setters
 
--------
 
ProductPurchases class -
class used in the viewSeller method.
Fields: public String product (product that was bought) public int purchaseNumber (number of purchase)
Methods: getters and setters, getProductName (returns the name of the product)

--------
 
PurchaseInformation class - 
class used in the viewSales method.
Fields: public String customer (customer of the purchase) public ArrayList<String> purchases (what the customer bought) public ArrayList<Integer> amountPurchased (the amount the customer bought) public ArrayList<Double> price (total price of what the customer bought)
Methods: getters and setters
 
--------

SellerServer class - 
class that houses all the methods used when each button is clicked as a customer.
Fields: none.
Methods: createNewItem (creates a product in a specific market) deleteItem (deletes a specific item from a market) editItem (allows the user to edit a product from a market) viewSales (method that returns sales information from a market) viewSeller (returns information for a dashboard with more in depth info) customerShoppingCarts (returns info of all customers to aid in the seller selecting a customer to view their shopping cart)

--------

Client classes - 
class that has the main method for the client and is supposed to be run after the server is.
Fields: final static int PORT = 7777 (port number used in connecting to the serversocket)
Methods: main method (essentially secures connection with server and then calls the login method which initializes the program and asks the user to login)
