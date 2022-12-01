import javax.swing.*;
import javax.swing.table.TableColumn;
import java.io.*;
import java.util.ArrayList;





public class SellerOptions {

    public static void options(User user, BufferedReader br, PrintWriter writer) {

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
        JButton option1 = new JButton("View Market");
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


        });
        panel.add(option1);

        JButton option2 = new JButton("Edit Product from Store");
        option2.setBounds(260, 50, 230, 40);
        option2.addActionListener(ev -> {
            writer.println("2");
            writer.flush();


        });
        panel.add(option2);

        JButton option3 = new JButton("View Sales by Store");
        option3.setBounds(10, 100, 230, 40);
        option3.addActionListener(ev -> {
            writer.println("3");
            writer.flush();


        });
        panel.add(option3);

        JButton option4 = new JButton("View Dashboard");
        option4.setBounds(260, 100, 230, 40);
        option4.addActionListener(ev -> {
            writer.println("4");
            writer.flush();


        });
        panel.add(option4);

        JButton option5 = new JButton("Import Products (CSV File)");
        option5.setBounds(10, 150, 230, 40);
        option5.addActionListener(ev -> {
            writer.println("5");
            writer.flush();


        });
        panel.add(option5);

        JButton option6 = new JButton("Export Products (CSV File)");
        option6.setBounds(260, 150, 230, 40);
        option6.addActionListener(ev -> {
            writer.println("6");
            writer.flush();


        });
        panel.add(option6);

        JButton option7 = new JButton("View Shopping Carts");
        option7.setBounds(10, 200, 230, 40);
        option7.addActionListener(ev -> {
            writer.println("7");
            writer.flush();


        });
        panel.add(option7);

        JButton option8 = new JButton("Create Market");
        option8.setBounds(260, 200, 230, 40);
        option8.addActionListener(ev -> {
            writer.println("8");
            writer.flush();


        });
        panel.add(option8);

        JButton option9 = new JButton("Delete Market");
        option9.setBounds(10, 250, 230, 40);
        option9.addActionListener(ev -> {
            writer.println("9");
            writer.flush();


        });
        panel.add(option9);

        JButton option10 = new JButton("More Information");
        option10.setBounds(260, 250, 230, 40);
        option10.addActionListener(ev -> {
            writer.println("10");
            writer.flush();
            frame.getContentPane().removeAll();
            frame.repaint();
            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            String line;
            try {
                line = br.readLine();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] lines = line.split(";");

            int x = 10;
            int y = 20;
            for (String a : lines) {
                JLabel label = new JLabel(a);
                label.setBounds(x, y, 400, 30);
                panel2.add(label);
                y += 40;
            }


            JButton back = new JButton("Go back to menu");
            back.setBounds(300, 400, 200, 40);
            option10.addActionListener(eve -> {
                frame.getContentPane().removeAll();
                frame.repaint();
                options(user, br, writer);
            });
            panel2.add(back);
            frame.add(panel2);
            frame.setVisible(true);




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
