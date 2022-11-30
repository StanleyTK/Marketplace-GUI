import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Login {
    public static void login(BufferedReader br, PrintWriter writer) {
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


                    String username = userText.getText();
                    // Ignore the getText getting crossed out, it still works
                    String password = passwordText.getText();
                    writer.println(username + ";" + password);
                    writer.flush();
                    try {
                        String line = br.readLine();
                        String[] info = line.split(";");
                        if (line.equals("Incorrect Username or Password, try again")) {
                            JOptionPane.showMessageDialog(null, line,
                                    "Incorrect Login!", JOptionPane.ERROR_MESSAGE);
                        } else {
                            User user = convertUser(line);

                            if (user instanceof Customer) {
                                frame.dispose();
                                writer.println("Break out of the loop");
                                writer.flush();
                                System.out.println("Did it work here?");
                                CustomerOptions.options(user, br, writer);
                            }
                            if (user instanceof Seller) {
                                frame.dispose();
                                writer.println("Break out of the loop");
                                writer.flush();
                                SellerOptions.options(user);
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
                        SellerOptions.options(user);
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
        String[] info = line.split(";");
        try {
            if (info[0].contains("Customer")) {
                ArrayList<String> fileInfo = getTextInfo(new File(info[2] + "'s File.txt"));
                ArrayList<Product> products = new ArrayList<>();
                for (String x : fileInfo) {
                    if (!x.contains("User: ") && !x.contains("Name: ")) {
                        products.add(new Product(x));
                    }

                }
                return new Customer(info[1], info[2], info[3], products);


            } else if (info[0].contains("Seller")) {
                ArrayList<String> fileInfo = getTextInfo(new File(info[1] + "'s File.txt"));
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
