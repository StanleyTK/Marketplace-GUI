import javax.swing.*;
import java.io.*;
import java.util.ArrayList;


public class Login {
    public static void login() {
        JFrame frame = new JFrame("Welcome to Marketplace!");
        frame.setSize(350, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(e -> {


                    String user = userText.getText();
                    // Ignore the getText getting crossed out, it still works
                    String password = passwordText.getText();
                    try {
                        String line = verifyLogin(user, password);
                        if (line == null) {
                            JOptionPane.showMessageDialog(null, "Username or Password is incorrect",
                                    "Incorrect Login Info", JOptionPane.ERROR_MESSAGE);
                        } else {


                            // This is where you access the Customer Options
                            String[] info = line.split(";");
                            JOptionPane.showMessageDialog(null, "Welcome " + info[2],
                                    "Welcome!", JOptionPane.INFORMATION_MESSAGE);
                            User user2 = getUser(line);
                            frame.dispose();
                            CustomerOptions.options(user2);



                        }

                    } catch (IOException error) {
                        error.printStackTrace();
                        JOptionPane.showMessageDialog(null, "There was an error",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
        panel.add(loginButton);
        JButton createAccount = new JButton("Create Account");
        createAccount.setBounds(100, 80, 150, 25);
        createAccount.addActionListener(e -> {
                    frame.getContentPane().removeAll();
                    frame.repaint();
                    JPanel panel2 = new JPanel();
                    panel2.setLayout(null);

                    JLabel lbl = new JLabel("Account Type");
                    lbl.setBounds(10, 20, 80, 25);

                    lbl.setVisible(true);
                    panel2.add(lbl);
                    String[] choices = {"Customer", "Seller"};

                    final JComboBox<String> cb = new JComboBox<>(choices);
                    cb.setBounds(100, 20, 80, 25);

                    cb.setVisible(true);
                    panel2.add(cb);
                    JButton btn = new JButton("OK");
                    panel2.add(btn);

                    JLabel userLabel2 = new JLabel("Username");
                    userLabel2.setBounds(10, 60, 80, 25);
                    panel2.add(userLabel2);

                    JTextField userText2 = new JTextField(20);
                    userText2.setBounds(100, 60, 165, 25);
                    panel2.add(userText2);

                    JLabel passwordLabel2 = new JLabel("Password");
                    passwordLabel2.setBounds(10, 90, 80, 25);
                    panel2.add(passwordLabel2);

                    JPasswordField passwordText2 = new JPasswordField();
                    passwordText2.setBounds(100, 90, 165, 25);
                    panel2.add(passwordText2);

                    JLabel nameLabel = new JLabel("Name");
                    nameLabel.setBounds(10, 120, 80, 25);
                    panel2.add(nameLabel);

                    JTextField nameText = new JTextField();
                    nameText.setBounds(100, 120, 165, 25);
                    panel2.add(nameText);

                    JButton createAccount2 = new JButton("Create New Account");
                    createAccount2.setBounds(100, 150, 150, 25);
                    createAccount2.addActionListener(ev -> {
                        String username = userText2.getText();
                        // Ignore the getText getting crossed out, it still works
                        String password = passwordText2.getText();
                        String name = nameText.getText();
                        String option = (String) cb.getSelectedItem();
                        System.out.println(option);
                        assert option != null;
                        User user = createAccount(username, password, name, option);
                        JOptionPane.showMessageDialog(null, "You have created a new " + option + " account!",
                                "Welcome!", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(null, "Welcome " + name,
                                "Welcome!", JOptionPane.INFORMATION_MESSAGE);
                        if (user instanceof Customer) {
                            CustomerOptions.options(user);
                        } else {
                            SellerOptions.options(user);
                        }
                    });
                    panel2.add(createAccount2);
                    frame.add(panel2);
                    frame.setVisible(true);

                });
        panel.add(createAccount);
        JLabel success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        panel.add(success);
        frame.add(panel);
        frame.setVisible(true);



    }


    // Checks the login information, and checks if the info matches the login
    public static String verifyLogin(String username, String password) throws IOException {
        ArrayList<String> lines = getTextInfo(new File("login.txt"));
        for (String line : lines) {
            String[] contents = line.split(";");
            if (contents[0].equals(username) && contents[1].equals(password)) {
                return line;
            }
        }
        return null;
    }

    // Creates a new account based on the information given on the create account page
    public static User createAccount(String username, String password, String name, String option) {
        try {

            PrintWriter pw = new PrintWriter(new FileOutputStream("login.txt", true));
            pw.println("");
            pw.println(username + ";" + password + ";" + name);
            pw.close();

            File f = new File(name + "'s File.txt");
            pw = new PrintWriter(new FileOutputStream(f, true));
            pw.println(("Name: " + name));
            pw.println("User: " + option);
            pw.close();

            // Sellers have access to all Customer information
            if (option.equals("Customer")) {
                f = new File("Customers.txt");
                pw = new PrintWriter(new FileOutputStream(f, false));
                if (f.createNewFile()) {
                    ArrayList<String> lines = getTextInfo(f);
                    for (String x : lines) {
                        pw.println(x);
                    }
                }

                pw.println(name);
                pw.close();
                return new Customer(name, username, password);

            } else {
                return new Seller(name, username, password);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

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

        String fileName = contents[2] + "'s File.txt";
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
