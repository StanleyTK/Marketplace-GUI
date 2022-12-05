import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Login {
    public static void login(BufferedReader br, PrintWriter writer) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Welcome to Marketplace!");
        frame.setSize(350, 300);
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

                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException |
                         IllegalAccessException ev) {
                    ev.printStackTrace();
                }
                String username = userText.getText();
                // Ignore the getText getting crossed out, it still works
                String password = passwordText.getText();
                writer.println(username + ";" + password);
                writer.flush();
                try {
                    String line = br.readLine();
                    if (line.equals("Incorrect Username or Password, try again")) {
                        JOptionPane.showMessageDialog(null, line,
                                "Incorrect Login!", JOptionPane.ERROR_MESSAGE);
                    } else {
                        User user = convertUser(line);
                        assert user != null;
                        JOptionPane.showMessageDialog(null, "Welcome " + user.getCustomerName(),
                                "Welcome!", JOptionPane.INFORMATION_MESSAGE);

                        if (user instanceof Customer) {
                            frame.dispose();
                            writer.println("Break out of the loop");
                            writer.flush();
                            CustomerOptions.options(user, br, writer);
                        }
                        if (user instanceof Seller) {
                            frame.dispose();
                            writer.println("Break out of the loop");
                            writer.flush();
                            SellerOptions.options(user, br, writer);
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error, invalid line",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        );
        panel.add(loginButton);
        JButton createAccount = new JButton("Create Account");
        createAccount.setBounds(100, 80, 150, 25);
        createAccount.addActionListener(e -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException |
                     IllegalAccessException a) {
                a.printStackTrace();
            }
            writer.println("Creating a new account");
            writer.flush();
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
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException |
                         IllegalAccessException eve) {
                    eve.printStackTrace();
                }
                String username = userText2.getText();
                // Ignore the getText getting crossed out, it still works
                String password = passwordText2.getText();
                String name = nameText.getText();
                String option = (String) cb.getSelectedItem();
                writer.println(username + ";" + password + ";" + name + ";" + option);
                writer.flush();
                String line = "";
                try {
                    line = br.readLine();
                } catch (IOException error) {
                    error.printStackTrace();
                }
                if (!line.equals("ERROR")) {
                    JOptionPane.showMessageDialog(null, "You have created a new " + option + " account!",
                            "Welcome!", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Welcome " + name,
                            "Welcome!", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    User user;
                    assert option != null;
                    if (option.equals("Customer")) {
                        user = new Customer(name, username, password);
                    } else {
                        user = new Seller(name, username, password);
                    }
                    if (user instanceof Customer) {
                        CustomerOptions.options(user, br, writer);
                    } else {
                        SellerOptions.options(user, br, writer);
                    }
                } else {
                    System.out.println("you scuk and you cant dot eh prject cuz ur stupdi");
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

    public static User convertUser(String line) {
        String[] info = line.split(";"); //makes line into array of information about user
        try {
            if (info[0].contains("Customer")) { //Customer information
                ArrayList<String> fileInfo = getTextInfo(new File(info[2] + "'s File.txt")); //ArrayList of customer info
                ArrayList<Product> products = new ArrayList<>();
                for (String x : fileInfo) { //for each string in customers info
                    if (x.contains(",")) {
                        products.add(new Product(x));
                    }

                }
                return new Customer(info[1], info[2], info[3], products);


            } else if (info[0].contains("Seller")) {
                return new Seller(info[1], info[2], info[3]);



            } else {
                JOptionPane.showMessageDialog(null, "Error, invalid line",
                        "Error", JOptionPane.ERROR_MESSAGE);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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

}
