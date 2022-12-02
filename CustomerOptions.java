import javax.swing.*;
import javax.swing.table.TableColumn;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;


public class CustomerOptions {

    public static void options(User user, BufferedReader br, PrintWriter writer) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Customer Options");
        JPanel panel = new JPanel();
        frame.setSize(510, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Welcome " + user.getCustomerName());
        userLabel.setBounds(10, 20, 200, 25);
        panel.add(userLabel);
        writer.println("Customer");
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
            System.out.println(printer);
            showTable(printer);
        });
        panel.add(option1);

        JButton option2 = new JButton("2. Search for Product");
        option2.setBounds(260, 50, 230, 40);
        option2.addActionListener(ev -> {
            writer.println("2");
            writer.flush();


        });
        panel.add(option2);

        JButton option3 = new JButton("3. Sort by Price");
        option3.setBounds(10, 100, 230, 40);
        option3.addActionListener(ev -> {
            writer.println("3");
            writer.flush();
            String DATA = "";
            try {
                DATA = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            showTable(DATA);


        });
        panel.add(option3);

        JButton option4 = new JButton("4. Sort by Quantity");
        option4.setBounds(260, 100, 230, 40);
        option4.addActionListener(ev -> {
            writer.println("4");
            writer.flush();
            String DATA = "";
            try {
                DATA = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            showTable(DATA);


        });
        panel.add(option4);

        JButton option5 = new JButton("5. View Dashboard");
        option5.setBounds(10, 150, 230, 40);
        option5.addActionListener(ev -> {
            writer.println("5");
            writer.flush();
            String purchaseHistory = "";
            try {
                String line = br.readLine();
                while (!line.equals("")) {
                    purchaseHistory = purchaseHistory + line + "\n";
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            showDashboard(purchaseHistory);


        });
        panel.add(option5);

        JButton option6 = new JButton("6. Export Purchase History");
        option6.setBounds(260, 150, 230, 40);
        option6.addActionListener(ev -> {
            writer.println("6");
            writer.flush();


        });
        panel.add(option6);

        JButton option7 = new JButton("7. Add item to Shopping Cart");
        option7.setBounds(10, 200, 230, 40);
        option7.addActionListener(ev -> {
            writer.println("7");
            writer.flush();


        });
        panel.add(option7);

        JButton option8 = new JButton("8. Remove Item to Shopping Cart");
        option8.setBounds(260, 200, 230, 40);
        option8.addActionListener(ev -> {
            writer.println("8");
            writer.flush();


        });
        panel.add(option8);

        JButton option9 = new JButton("9. Purchase Shopping Cart");
        option9.setBounds(10, 250, 230, 40);
        option9.addActionListener(ev -> {
            writer.println("9");
            writer.flush();


        });
        panel.add(option9);

        JButton option10 = new JButton("10. View Shopping Cart");
        option10.setBounds(260, 250, 230, 40);
        option10.addActionListener(ev -> {
            writer.println("10");
            writer.flush();
            String DATA = "";
            try {
                DATA = br.readLine();
            } catch (IOException e) {
                System.out.println("There was an error");
            }
            showTable(DATA);

        });
        panel.add(option10);

        JButton option11 = new JButton("11. More Information");
        option11.setBounds(10, 300, 230, 40);
        option11.addActionListener(ev -> {
            writer.println("11");
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
        panel.add(option11);

        JButton option12 = new JButton("Exit");
        option12.setBounds(260, 300, 230, 40);
        option12.addActionListener(ev -> {

            // Exit the program
            if (JOptionPane.showConfirmDialog(frame, "Confirm if you Want to Exit", "Confirmation",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                writer.println("12");
                writer.flush();
                System.exit(0);
            }
        });
        panel.add(option12);


        frame.add(panel);
        frame.setVisible(true);


    }

    static void showTable(String printer) {
        String[] printerSplit = printer.split(";"); // Splits the list into an array of individual products
        ArrayList<String[]> toReturnArrayList = new ArrayList<>();
        for (String s : printerSplit) {
            String[] printerSplitString = s.split(",");
            toReturnArrayList.add(printerSplitString);
        } // Adds the details of each product to the array list

        String[][] toReturnSize = new String[toReturnArrayList.size()][5];
        Object[][] rowArray = toReturnArrayList.toArray(toReturnSize); // Creates a 2D array for the product details
        Object[] columnArray = {"Product Name", "Store Name", "Description", "Quantity Available", "Price"};

        JTable table = new JTable(rowArray, columnArray); // Creates a table with the product list

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
        final JFrame jFrame = new JFrame("Marketplace");
        jFrame.setSize(1000, 500);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.getContentPane().add(scrollPane);
        TableColumn tableColumn;
        for (int i = 0; i < 5; i++) {
            tableColumn = table.getColumnModel().getColumn(i);
            if (i == 2) {
                tableColumn.setPreferredWidth(150);
            } else if (i == 3) {
                tableColumn.setPreferredWidth(50);
            } else {
                tableColumn.setPreferredWidth(100);
            }
        }
        jFrame.setVisible(true); // Creates a JFrame to view the table
    }

    protected static void showDashboard(String purchaseHistory) {
        JTextArea dashboard = new JTextArea();
        dashboard.append(purchaseHistory);
        JScrollPane scrollable = new JScrollPane(dashboard);
        JFrame jFrame = new JFrame("Customer Dashboard");
        jFrame.setSize(1000, 500);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.getContentPane().add(scrollable);
        jFrame.setVisible(true);
    }

}
