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

                    //TODO implement the login function and the txt file here
                    String user = userText.getText();
                    String password = passwordText.getText();
                    System.out.println(user + ", " + password);
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

                    JPasswordField nameText = new JPasswordField();
                    nameText.setBounds(100, 120, 165, 25);
                    panel2.add(nameText);

                    frame.add(panel2);
                    frame.setVisible(true);

                }
        );
        panel.add(createAccount);
        JLabel success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        panel.add(success);
        frame.add(panel);
        frame.setVisible(true);



    }

    public static String verifyLogin(String username, String password) {
        ArrayList<String> lines = getTextInfo(new File("login.txt"));
        for (String line : lines) {
            String[] contents = line.split(";");
            if (contents[0].equals(username) && contents[1].equals(password)) {
                return line;
            }
        }
        return null;

    }


    public static boolean createAccount() {
        return false;
    }


    // Returns the product from the market
    public static Product getProduct(String line) {
        String[] contents = line.split(",");
        return new Product(contents[0], contents[1], contents[2],
                Integer.parseInt(contents[3]), Double.parseDouble(contents[4]));
    }



    // Easier implementation to access a txt file
    public static ArrayList<String> getTextInfo(File f) {
        ArrayList<String> toReturn = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            while (line != null) {
                toReturn.add(line);
                line = br.readLine();
            }

            return toReturn;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "There was an error",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return toReturn;

    }





}
