import javax.swing.*;
import javax.swing.table.TableColumn;
import java.io.*;
import java.util.ArrayList;





public class SellerOptions {

    public static void options(User user, BufferedReader br, PrintWriter writer) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }


        JFrame frame = new JFrame("Seller Options");
        JPanel panel = new JPanel();
        frame.setSize(510, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Welcome " + user.getCustomerName());
        userLabel.setBounds(10, 20, 200, 25);
        panel.add(userLabel);
        writer.println("Seller");
        writer.flush();


        //View the MarketPlace
        JButton option1 = new JButton("1. View Market");
        option1.setBounds(10, 50, 230, 40);
        option1.addActionListener(ev -> {
            writer.println("1");
            writer.flush();
            String printer = "";
            try {
                printer = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CustomerOptions.showTable(printer);

        });
        panel.add(option1);

        JButton option2 = new JButton("2. Edit Product from Store");
        option2.setBounds(260, 50, 230, 40);
        option2.addActionListener(ev -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException |
                     IllegalAccessException e) {
                e.printStackTrace();
            }

            writer.println("2");
            writer.flush();
            String[] info = new String[]{"Create", "Delete", "Edit"};
            String item = (String) JOptionPane.showInputDialog(null, "Select an option ", "Option",
                    JOptionPane.PLAIN_MESSAGE, null, info, null);
            writer.println(item);
            writer.flush();


        });
        panel.add(option2);

        JButton option3 = new JButton("3. View Sales by Store");
        option3.setBounds(10, 100, 230, 40);
        option3.addActionListener(ev -> {
            writer.println("3");
            writer.flush();


        });
        panel.add(option3);

        JButton option4 = new JButton("4. View Dashboard");
        option4.setBounds(260, 100, 230, 40);
        option4.addActionListener(ev -> {
            writer.println("4");
            writer.flush();


        });
        panel.add(option4);

        JButton option5 = new JButton("5. Import Products (CSV File)");
        option5.setBounds(10, 150, 230, 40);
        option5.addActionListener(ev -> {
            writer.println("5");
            writer.flush();


        });
        panel.add(option5);

        JButton option6 = new JButton("6. Export Products (CSV File)");
        option6.setBounds(260, 150, 230, 40);
        option6.addActionListener(ev -> {
            writer.println("6");
            writer.flush();


        });
        panel.add(option6);

        JButton option7 = new JButton("7. View Shopping Carts");
        option7.setBounds(10, 200, 230, 40);
        option7.addActionListener(ev -> {
            writer.println("7");
            writer.flush();


        });
        panel.add(option7);

        JButton option8 = new JButton("8. Create Market");
        option8.setBounds(260, 200, 230, 40);
        option8.addActionListener(ev -> {
            writer.println("8");
            writer.flush();
            String message = JOptionPane.showInputDialog(null, "What is your new Market name?",
                    "Server", JOptionPane.QUESTION_MESSAGE);
            writer.println(message);
            writer.flush();
            try {
                String line = br.readLine();
                if (line.equals("Success")) {
                    JOptionPane.showMessageDialog(null, message + " Market has been created!",
                            "Marketplace", JOptionPane.INFORMATION_MESSAGE);
                } else if (line.equals("Fail")){
                    JOptionPane.showMessageDialog(null, "There was an error while creating " + message,
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Market already exists! " + message,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        });
        panel.add(option8);

        JButton option9 = new JButton("9. Delete Market");
        option9.setBounds(10, 250, 230, 40);
        option9.addActionListener(ev -> {
            writer.println("9");
            writer.flush();


        });
        panel.add(option9);

        JButton option10 = new JButton("10. More Information");
        option10.setBounds(260, 250, 230, 40);
        option10.addActionListener(ev -> {
            writer.println("10");
            writer.flush();
            String line = "";
            try {
                line = br.readLine();
            } catch (IOException e) {
                System.out.println("There was an error");
            }


            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException |
                     IllegalAccessException e) {
                e.printStackTrace();
            }
            final JFrame jFrame = new JFrame("More Information");
            jFrame.setSize(400, 500);
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jFrame.getContentPane();
            String[] lines = line.split(";");
            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            int x = 10;
            int y = 20;
            for (String a : lines) {
                JLabel label = new JLabel(a);
                label.setBounds(x, y, 400, 30);
                panel2.add(label);
                y += 40;
            }
            jFrame.add(panel2);
            jFrame.setVisible(true);





        });
        panel.add(option10);

        JButton option11 = new JButton("Exit");
        option11.setBounds(10, 300, 230, 40);
        option11.addActionListener(ev -> {
            // Exit the program
            if (JOptionPane.showConfirmDialog(frame, "Confirm if you Want to Exit", "Confirmation",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                writer.println("11");
                writer.flush();
                System.exit(0);


            }
        });
        panel.add(option11);




        frame.add(panel);
        frame.setVisible(true);


    }
}
