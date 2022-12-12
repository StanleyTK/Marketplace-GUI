import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A GUI for Customer users, displays the GUI of the buttons, with the options available
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Project 5</p>
 *
 * @author Stanley Kim
 * @version December 7, 2022
 */


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
            Thread t = new Thread(() -> {
                writer.println("1");
                writer.flush();
                String printer = "";
                try {
                    printer = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showTable(printer);
            });
            t.start();
        });
        panel.add(option1);


        JButton option2 = new JButton("2. Search for Product");
        option2.setBounds(260, 50, 230, 40);
        option2.addActionListener(ev -> {
            Thread t = new Thread(() -> {
                writer.println("2");
                writer.flush();
                String[] info = new String[]{"Name", "Description", "Store"};
                String item = (String) JOptionPane.showInputDialog(null, "Select an option ", "Option",
                        JOptionPane.PLAIN_MESSAGE, null, info, null);
                writer.println(item);
                writer.flush();

                String message = JOptionPane.showInputDialog(null, "Enter your search text", "Search by " + item,
                        JOptionPane.INFORMATION_MESSAGE);
                writer.println(message);
                writer.flush();
                String printer = "";
                try {
                    printer = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (printer.equals("None")) {
                    JOptionPane.showMessageDialog(null, "There are no items found", "Search by " + item,
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showTable(printer);
                }
            });
            t.start();


        });
        panel.add(option2);

        JButton option3 = new JButton("3. Sort by Price");
        option3.setBounds(10, 100, 230, 40);
        option3.addActionListener(ev -> {
            Thread t = new Thread(() -> {
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
            t.start();


        });
        panel.add(option3);

        JButton option4 = new JButton("4. Sort by Quantity");
        option4.setBounds(260, 100, 230, 40);
        option4.addActionListener(ev -> {
            Thread t = new Thread(() -> {
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
            t.start();


        });
        panel.add(option4);

        JButton option5 = new JButton("5. View Dashboard");
        option5.setBounds(10, 150, 230, 40);
        option5.addActionListener(ev -> {
            Thread t = new Thread(() -> {
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
            t.start();


        });
        panel.add(option5);

        JButton option6 = new JButton("6. Export Purchase History");
        option6.setBounds(260, 150, 230, 40);
        option6.addActionListener(ev -> {
            Thread t = new Thread(() -> {
                writer.println("6");
                writer.flush();
                String fileLocation = (String) JOptionPane.showInputDialog(null,
                        "What is the file name you wish to export your purchase history to?",
                        "Marketplace - Export Purchase History", JOptionPane.QUESTION_MESSAGE);
                try {
                    String line = br.readLine();
                    if (line.equals("Failed")) {
                        JOptionPane.showMessageDialog(null, "There was an error accessing the market place.",
                                "Marketplace - Export Purchase History", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            String printer = "";
                            while (!line.equals("finished")) {
                                printer += line + "\n";
                                line = br.readLine();
                            }
                            File f = new File(fileLocation);
                            PrintWriter pw = new PrintWriter(new FileOutputStream(f));
                            pw.println(printer);
                            pw.close();
                            JOptionPane.showMessageDialog(null,
                                    "Your purchase history was successfully exported to " + fileLocation,
                                    "Marketplace - Export Purchase History", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, "The provided file location is invalid.",
                                    "Marketplace - Export Purchase History", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "There was an error: " + e.getMessage(),
                            "Marketplace - Export Purchase History", JOptionPane.ERROR_MESSAGE);
                }
            });
            t.start();
        });
        panel.add(option6);

        JButton option7 = new JButton("7. Add item to Shopping Cart");
        option7.setBounds(10, 200, 230, 40);
        option7.addActionListener(ev -> {
            Thread t = new Thread(() -> {
                writer.println("7");
                writer.flush();
                String returned = "";
                try {
                    returned = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String[] productList = returned.split(";");
                String item = (String) JOptionPane.showInputDialog(null, "Which product would you like to add to shopping cart?", "Option",
                        JOptionPane.PLAIN_MESSAGE, null, productList, null);
                writer.println(item);
                writer.flush();
                int quantity;
                try {
                    quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "How much would you like to buy?",
                            "MarketPlace", JOptionPane.INFORMATION_MESSAGE));
                    String amount = String.valueOf(quantity);
                    writer.println(amount);
                    writer.flush();
                    String bol = br.readLine();
                    if (bol.equals("false")) {
                        JOptionPane.showMessageDialog(null, "Invalid input, try again", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "The product has been added to the shopping cart",
                                "MarketPlace", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input, try again", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }


//            JFrame addFrame = new JFrame("Add to Cart");
//            addFrame.setVisible(true);
//            addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            addFrame.setSize(275,150);
//            addFrame.setLocation(430,100);
//
//            JPanel pane = new JPanel();
//            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
//            addFrame.setResizable(false);
//            addFrame.add(pane);
//
//            JLabel lbl = new JLabel("Select a product to add to your shopping cart.");
//            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
//            pane.add(lbl);
//
//            JComboBox cb = null;
//
//            try {
//                cb = new JComboBox(CustomerServer.addShoppingCartItem(user));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            cb.setMaximumSize(cb.getPreferredSize()); // added code
//            cb.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//            pane.add(cb);
//
//            JLabel label = new JLabel("Select Quantity:");
//            label.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//            JTextField quantityChoice = new JTextField();
//            quantityChoice.setAlignmentX(Component.CENTER_ALIGNMENT);
//            quantityChoice.setAlignmentY(Component.TOP_ALIGNMENT);
//            quantityChoice.setPreferredSize(new Dimension(5,5));
//            Font bigFont = quantityChoice.getFont().deriveFont(Font.PLAIN, 20f);
//            quantityChoice.setFont(bigFont);
//            quantityChoice.setHorizontalAlignment(JTextField.CENTER);
//            pane.add(label);
//            pane.add(quantityChoice);
//
//
//            JButton confirm = new JButton("ADD");
//            confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
//            confirm.setAlignmentY(Component.BOTTOM_ALIGNMENT);
//            pane.add(confirm);
//
//            JComboBox finalCb = cb;
//            confirm.addActionListener(actionEvent -> { // On click of the ADD button
//                addFrame.dispose();
//                int quantity = 0;
//
//                File f = new File(user.getUsername() + "'s File.txt");
//                BufferedWriter bfw = null;
//                try {
//                    bfw = new BufferedWriter(new FileWriter(f, true));
//                } catch (IOException e) {
//                    e.printStackTrace();
//
//                }
//                try {
//                    quantity = Integer.parseInt(quantityChoice.getText());
//
//                    String productChoice = (String) finalCb.getSelectedItem();
//                    String[] product = productChoice.split(",");
//
//                    if (quantity > Integer.parseInt(product[3])) {
//                        JOptionPane.showMessageDialog(null,"You cant buy more than the store has!"
//                                , "Error" , JOptionPane.ERROR_MESSAGE);
//                        addFrame.setVisible(true);
//                        return;
//                    } else if (quantity == Integer.parseInt(product[3])) { // IF THEY BUY THE ENTIRE PRODUCT
//                        product[3] = String.valueOf(quantity);
//
//                        String concat = "";
//
//                        for (String s : product) {
//                            concat = concat + "," + s;
//                        }
//                        concat = concat.substring(1);
//                        bfw.append("\n" + concat);
//                        bfw.close();
//
//                        File productsMarket = new File(product[1] + " Market.txt");
//                        File tempFile = new File("myTempFile.txt");
//
//                        BufferedReader bfr = new BufferedReader(new FileReader(productsMarket));
//                        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(tempFile)));
//                        String removalProduct = productChoice;
//                        String line = "";
//                        boolean once = true;
//                        ArrayList<String> productsToRewrite = new ArrayList<>();
//
//                        while ((line = bfr.readLine()) != null) {
//                            if (!line.equals(removalProduct)) {
//                                productsToRewrite.add(line);
//                                //makes it so only first instance of the line is removed
//                            } else if (line.equals(removalProduct)) {
//                                once = false;
//                            }
//                        }
//
//                        for (String str : productsToRewrite) {
//                            printWriter.write(str + "\n");
//                        }
//                        printWriter.close();
//                        bfr.close();
//
//                        copyFileToFile(tempFile , productsMarket);
//                        tempFile.delete();
//                    } else { // REGULAR
//                        product[3] = String.valueOf(Integer.parseInt(product[3]) - quantity);
//
//                        String concat = "";
//
//                        for (String s : product) {
//                            concat = concat + "," + s;
//                        }
//                        concat = concat.substring(1);
//                        bfw.append("\n" + concat);
//                        bfw.close();
//
//
//                        File productsMarket = new File(product[1] + " Market.txt");
//                        File tempFile = new File("myTempFile.txt");
//
//                        BufferedReader bfr = new BufferedReader(new FileReader(productsMarket));
//                        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(tempFile)));
//                        String removalProduct = productChoice;
//                        String line = "";
//                        ArrayList<String> productsToRewrite = new ArrayList<>();
//
//                        while ((line = bfr.readLine()) != null) { //iterates through file and adds all lines that dont
//                            //equal the desired removed line
//                            if (!line.equals(removalProduct)) {
//                                productsToRewrite.add(line);
//                            } else {
//                                productsToRewrite.add(product[0] + "," + product[1] + "," + product[2] + "," +
//                                        product[3] + "," + product[4]);
//                            }
//                        }
//
//                        for (String str : productsToRewrite) {
//                            //writes all lines to a temporary file in which it will then copy
//                            //the contents of that file into the respective market.
//                            printWriter.write(str + "\n");
//                        }
//                        printWriter.close();
//                        bfr.close();
//
//                        copyFileToFile(tempFile , productsMarket);
//                        tempFile.delete(); //deletes temporary file
//
//                    }
//
//                } catch (NumberFormatException e) {
//                    JOptionPane.showMessageDialog(null, "Please enter an integer",
//                            "Error", JOptionPane.ERROR_MESSAGE);
//                    addFrame.setVisible(true);
//                    return;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                JOptionPane.showMessageDialog(null,"Success!",
//                        "Success!",JOptionPane.INFORMATION_MESSAGE);
//
//            });
            });
            t.start();

        });
        panel.add(option7);

        JButton option8 = new JButton("8. Remove Item from Shopping Cart");
        option8.setBounds(260, 200, 230, 40);
        option8.addActionListener(ev -> {
            Thread t = new Thread(() -> {
                writer.println("8");
                writer.flush();
                String returned = "";
                try {
                    returned = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String[] productList = returned.split(";");
                String item = (String) JOptionPane.showInputDialog(null, "Which product would you like to remove from shopping cart?", "Option",
                        JOptionPane.PLAIN_MESSAGE, null, productList, null);
                writer.println(item);
                writer.flush();
                int quantity;
                try {
                    quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "How much would you like to remove?",
                            "MarketPlace", JOptionPane.INFORMATION_MESSAGE));
                    String amount = String.valueOf(quantity);
                    writer.println(amount);
                    writer.flush();
                    String bol = br.readLine();
                    if (bol.equals("false")) {
                        JOptionPane.showMessageDialog(null, "Invalid input, try again", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "The product has been removed to the shopping cart",
                                "MarketPlace", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input, try again", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }


//            JFrame removalFrame = new JFrame("Remove from Cart");
//            removalFrame.setVisible(true);
//            removalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            removalFrame.setSize(300, 200);
//            removalFrame.setLocation(430, 100);
//
//            JPanel pane = new JPanel();
//            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
//
//            removalFrame.add(pane);
//
//            JLabel lbl = new JLabel("Select a product to remove from your shopping cart.");
//            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
//            pane.add(lbl);
//
//            JComboBox cb = null;
//
//            try {
//                cb = new JComboBox(CustomerServer.removeShoppingCart(user));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//            cb.setMaximumSize(cb.getPreferredSize()); // added code
//            cb.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//            pane.add(cb);
//
//            JButton btn = new JButton("REMOVE");
//            btn.setAlignmentX(Component.CENTER_ALIGNMENT); //added code
//            pane.add(btn);
//            JComboBox finalCb = cb;
//            btn.addActionListener(actionEvent -> {
//                removalFrame.dispose();
//                String removal = (String) finalCb.getSelectedItem();
//                JOptionPane.showMessageDialog(null,"Success!",
//                        "Product added to Cart",JOptionPane.INFORMATION_MESSAGE);
//                try {
//                    removeShoppingCart(removal, user);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                // Add product back into respective market
//
//                String[] product = removal.split(",");
//
//                File marketFile = new File(product[1] + " Market.txt");
//                File tempFile = new File("myTempFile.txt");
//                ArrayList<String> reWriter = new ArrayList<>();
//                try {
//                    String line = "";
//                    BufferedReader bfr = new BufferedReader(new FileReader(marketFile));
//                    BufferedWriter bfw = new BufferedWriter(new FileWriter(tempFile));
//
//                    while ((line = bfr.readLine()) != null) {
//                        reWriter.add(line);
//                    }
//                    int counter = 0;
//                    boolean found = false;
//                    boolean once = true;
//                    boolean alsoOnce = true;
//
//                    for (String s : reWriter) {
//                        if (s.contains(product[0]) && s.contains(product[1]) && alsoOnce) {
//                            String[] lines = s.split(",");
//                            String[] toReturn = new String[5];
//                            toReturn[0] = lines[0];
//                            toReturn[1] = lines[1];
//                            toReturn[2] = lines[2];
//                            toReturn[3] = String.valueOf((Integer.parseInt(product[3]) + Integer.parseInt(lines[3])));
//                            toReturn[4] = lines[4];
//                            reWriter.set(counter, toReturn[0] + "," + toReturn[1] + "," + toReturn[2] +
//                                    "," + toReturn[3] + "," + toReturn[4]);
//                            found = true;
//                            alsoOnce = false;
//                        }
//                        if (!found && once) { //goes once if it is not found.
//                            bfw.write(removal + "\n");
//                            once = false;
//                        }
//                        counter++;
//                    }
//                    for (String s : reWriter) {
//                        bfw.write(s + "\n");
//                    }
//                    bfw.close();
//                    bfr.close();
//
//                    copyFileToFile(tempFile , marketFile);
//                    tempFile.delete();
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                reWriter.add(removal);
//
//            });
            });
            t.start();
        });
        panel.add(option8);

        JButton option9 = new JButton("9. Purchase Items in Shopping Cart");
        option9.setBounds(10, 250, 230, 40);
        option9.addActionListener(ev -> {
            Thread t = new Thread(() -> {

                writer.println("9");
                writer.flush();
                String printer = "";
                try {
                    String line = br.readLine();
                    while (!line.equals("finished")) {
                        printer += line + "\n";
                        line = br.readLine();
                    }
                    JOptionPane.showMessageDialog(null,
                            printer,
                            "Marketplace - Purchase Shopping Cart", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "There was an error: " + e.getMessage(),
                            "Marketplace - Purchase Shopping Cart", JOptionPane.ERROR_MESSAGE);
                }
            });
            t.start();

        });
        panel.add(option9);

        JButton option10 = new JButton("10. View Shopping Cart");
        option10.setBounds(260, 250, 230, 40);
        option10.addActionListener(ev -> {
            Thread t = new Thread(() -> {

                writer.println("10");
                writer.flush();
                String data = "";
                try {
                    data = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (data.equals("None")) {
                    JOptionPane.showMessageDialog(null, "There are no products found in your shopping cart",
                            "None found", JOptionPane.INFORMATION_MESSAGE);
                } else if (data.equals("Error")) {
                    JOptionPane.showMessageDialog(null, "There are no products found in your shopping cart",
                            "None found", JOptionPane.ERROR_MESSAGE);
                } else {
                    showTable(data);
                }
            });
            t.start();
        });
        panel.add(option10);

        JButton option11 = new JButton("11. More Information");
        option11.setBounds(10, 300, 230, 40);
        option11.addActionListener(ev -> {
            Thread t = new Thread(() -> {

                writer.println("11");
                writer.flush();
                String line = "";
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
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
            t.start();


        });
        panel.add(option11);

        JButton option12 = new JButton("Exit");
        option12.setBounds(260, 300, 230, 40);
        option12.addActionListener(ev -> {
            Thread t = new Thread(() -> {


                // Exit the program
                if (JOptionPane.showConfirmDialog(frame, "Confirm if you Want to Exit", "Confirmation",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    writer.println("12");
                    writer.flush();
                    System.exit(0);
                }
            });
            t.start();
        });
        panel.add(option12);
        frame.add(panel);
        frame.setVisible(true);
    }

    protected static void showDashboard(String purchaseHistory) {
        JTextArea dashboard = new JTextArea();
        dashboard.append(purchaseHistory);
        JScrollPane scrollable = new JScrollPane(dashboard);
        JFrame jFrame = new JFrame("Dashboard");
        jFrame.setSize(1000, 500);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.getContentPane().add(scrollable);
        jFrame.setVisible(true);
    }

    protected static void showTable(String printer) {
        if (printer.equals("")) {
            JOptionPane.showMessageDialog(null, "There are no products found");
        } else {
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
    }


    public static void removeShoppingCart(String removal, User user) throws IOException {
        File inputFile = new File(user.getUsername() + "'s File.txt");
        File tempFile = new File("myTempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(removal)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean successful = tempFile.renameTo(inputFile);
        MarketPlaceThread.copyFileToFile(tempFile , inputFile);
    }




}
