import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * A Seller options class displays the GUI of the buttons
 * Gets the information from the server
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Project 5</p>
 *
 * @author Stanley Kim
 * @version December 7, 2022
 */



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
            String market = "";
            try {
                String[] markets = br.readLine().split(";");
                market = (String) JOptionPane.showInputDialog(null, "Which Market would you like to edit in? ", "Option",
                        JOptionPane.PLAIN_MESSAGE, null, markets, null);
                writer.println(market);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (item.equals("Create")) {
                int quantity = 0;
                double price = 0;
                String name = JOptionPane.showInputDialog(null, "What is the Product's new name?",
                        "Create new product", JOptionPane.INFORMATION_MESSAGE);
                String description = JOptionPane.showInputDialog(null, "What is the Product's new description?",
                        "Create new product", JOptionPane.INFORMATION_MESSAGE);
                while (true) {
                    try {
                        quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "What is the Product's new quantity?",
                                "Create new product", JOptionPane.INFORMATION_MESSAGE));
                        break;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid input, try again", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                while (true) {
                    try {
                        price = Double.parseDouble(JOptionPane.showInputDialog(null, "What is the Product's new price?",
                                "Create new product", JOptionPane.INFORMATION_MESSAGE));
                        break;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid input, try again", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }


                writer.println(name + ";" + description + ";" + quantity + ";" + price);
                writer.flush();
                JOptionPane.showMessageDialog(null, name + " has been created in " + market, "Error", JOptionPane.INFORMATION_MESSAGE);
//


            } else if (item.equals("Delete")) {
                try {
                    String deleteInfo = br.readLine();
                    String[] deleteInformation = deleteInfo.split(";");
                    String[] deleteNames = new String[deleteInformation.length];
                    for (int i = 0; i < deleteInformation.length; i++) {
                        String[] productInfo = deleteInformation[i].split(",");
                        deleteNames[i] = productInfo[0];
                    }
                    String product = (String) JOptionPane.showInputDialog(null, "Which product would you like to delete? ", "Option",
                            JOptionPane.PLAIN_MESSAGE, null, deleteInformation, null);
                    writer.println(product);
                    writer.flush();
                    JOptionPane.showMessageDialog(null, product + " has been deleted in " + market, "MarketPlace", JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Edit
                try {
                    String editInfo = br.readLine();
                    String[] editInformation = editInfo.split(";");
                    String product = (String) JOptionPane.showInputDialog(null, "Which product would you like to edit? ", "Option",
                            JOptionPane.PLAIN_MESSAGE, null, editInformation, null);
                    writer.println(product);
                    writer.flush();
                    String[] productInfo = product.split(",");
                    int quantity = 0;
                    double price = 0;
                    String name = JOptionPane.showInputDialog(null, "What is the Product's new name?",
                            "Create new product", JOptionPane.INFORMATION_MESSAGE);
                    String description = JOptionPane.showInputDialog(null, "What is the Product's new description?",
                            "Create new product", JOptionPane.INFORMATION_MESSAGE);
                    while (true) {
                        try {
                            quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "What is the Product's new quantity?",
                                    "Create new product", JOptionPane.INFORMATION_MESSAGE));
                            break;
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid input, try again", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    while (true) {
                        try {
                            price = Double.parseDouble(JOptionPane.showInputDialog(null, "What is the Product's new price?",
                                    "Create new product", JOptionPane.INFORMATION_MESSAGE));
                            break;
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid input, try again", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }


                    writer.println(name + ";" + description + ";" + quantity + ";" + price);
                    writer.flush();
                    JOptionPane.showMessageDialog(null, productInfo[0] + " has been edited in " + market, "MarketPlace", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });
        panel.add(option2);

        JButton option3 = new JButton("3. View Sales by Store");
        option3.setBounds(10, 100, 230, 40);
        option3.addActionListener(ev -> {
            writer.println("3");
            writer.flush();
            writer.println(marketName());
            writer.flush();
            String salesInformation = "";
            try {
                String line = br.readLine();
                while (!line.equals("")) {
                    salesInformation = salesInformation + line + "\n";
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            CustomerOptions.showDashboard(salesInformation);


        });
        panel.add(option3);

        JButton option4 = new JButton("4. View Dashboard");
        option4.setBounds(260, 100, 230, 40);
        option4.addActionListener(ev -> {
            writer.println("4");
            writer.flush();
            writer.println(marketName());
            writer.flush();
            String marketInformation = "";
            try {
                String line = br.readLine();
                while (!line.equals("")) {
                    marketInformation = marketInformation + line + "\n";
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            CustomerOptions.showDashboard(marketInformation);


        });
        panel.add(option4);
        JButton option5 = new JButton("5. Import Products (CSV File)");
        option5.setBounds(10, 150, 230, 40);
        option5.addActionListener(ev -> {
            try {
                String fileLocation = (String) JOptionPane.showInputDialog(null,
                        "What is the file name you wish to import products from?",
                        "Marketplace - Import CSV Files", JOptionPane.QUESTION_MESSAGE);
                String targetFile = (String) JOptionPane.showInputDialog(null,
                        "What is the market you wish to import your products to?",
                        "Marketplace - Import CSV Files", JOptionPane.QUESTION_MESSAGE);
                File file = new File(fileLocation);
                FileReader fr = new FileReader(file);
                BufferedReader bfr = new BufferedReader(fr);
                String products = "";
                String line = bfr.readLine();
                while (line != null) {
                    products += line + "\n";
                    line = bfr.readLine();
                }
                bfr.close();
                writer.println("5");
                writer.println(targetFile + " Market.txt");
                writer.println(products);
                writer.flush();
                String success = br.readLine();
                if (success.equals("success")) {
                    JOptionPane.showMessageDialog(null, "Products from " + fileLocation +
                                    " were successfully imported to " + targetFile,
                            "Marketplace - Import CSV Files", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "There was an error accessing the market you provided.",
                            "Marketplace - Import CSV Files", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "There was an error accessing the file you provided.",
                        "Marketplace - Import CSV Files", JOptionPane.ERROR_MESSAGE);
            }

        });
        panel.add(option5);

        JButton option6 = new JButton("6. Export Products (CSV File)");
        option6.setBounds(260, 150, 230, 40);
        option6.addActionListener(ev -> {
            try {
                writer.println("6");
                String fileLocation = (String) JOptionPane.showInputDialog(null,
                        "What is the CSV file location where the products should be exported?",
                        "Marketplace - Export Products", JOptionPane.QUESTION_MESSAGE);
                String targetMarket = (String) JOptionPane.showInputDialog(null,
                        "What is the market you wish to export product data from?",
                        "Marketplace - Export Products", JOptionPane.QUESTION_MESSAGE);
                writer.println(targetMarket);
                writer.flush();
                String printer = "";
                String line = br.readLine();
                while (!line.equals("finished")) {
                    printer += line + "\n";
                    line = br.readLine();
                }
                File f = new File(fileLocation);
                PrintWriter pw = new PrintWriter(new FileOutputStream(f));
                pw.println(printer);
                pw.close();
                JOptionPane.showMessageDialog(null,
                        "Your products from " + targetMarket + " were successfully exported to " + fileLocation,
                        "Marketplace - Export Products", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "There was an error accessing the file you provided.",
                        "Marketplace - Export Products", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(option6);

        JButton option7 = new JButton("7. View Shopping Carts");
        option7.setBounds(10, 200, 230, 40);
        option7.addActionListener(ev -> {
            writer.println("7");
            writer.flush();
            String DATA = "";
            try {
                DATA = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JFrame jFrame = new JFrame("View Shopping Carts");
            Object[] dataArray = DATA.split(";");


            jFrame.setVisible(true);
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jFrame.setSize(275,150);
            jFrame.setLocation(430,100);

            JPanel pane = new JPanel();
            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
            jFrame.setResizable(false);
            jFrame.add(pane);

            JLabel lbl = new JLabel("Select a Customer to view their Shopping Cart");
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            pane.add(lbl);

            JComboBox cb;

            cb = new JComboBox(dataArray);

            cb.setMaximumSize(cb.getPreferredSize()); // added code
            cb.setAlignmentX(Component.CENTER_ALIGNMENT);

            pane.add(cb);

            JButton confirm = new JButton("VIEW");
            confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
            confirm.setAlignmentY(Component.BOTTOM_ALIGNMENT);
            pane.add(confirm);
            confirm.addActionListener(e -> {
                jFrame.dispose();
                writer.println("Customer Selected");
                writer.flush();
                String selectedItem = (String) cb.getSelectedItem();
                writer.println(selectedItem);
                writer.flush();
                String DATA2 = "";

                try {
                    if (br.readLine().equals("Done!")) {
                        DATA2 = br.readLine();
                        if (DATA2.equals("There are no items in this user's shopping cart.")) {
                            JOptionPane.showMessageDialog(null, "There are no items in this user's shopping cart.",
                                    "Marketplace - View Shopping Carts", JOptionPane.ERROR_MESSAGE);
                        } else {
                            CustomerOptions.showTable(DATA2);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            });

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
            String DATA = "";

            try {
                DATA = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Object[] dataArray = DATA.split(";");

            JFrame deleteAMarket = new JFrame("Delete a market");

            deleteAMarket.setVisible(true);
            deleteAMarket.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            deleteAMarket.setSize(275,150);
            deleteAMarket.setLocation(430,100);

            JPanel pane = new JPanel();
            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
            deleteAMarket.setResizable(false);
            deleteAMarket.add(pane);

            JLabel lbl = new JLabel("Select a market to delete");
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            pane.add(lbl);

            JComboBox cb = null;

            cb = new JComboBox(dataArray);

            cb.setMaximumSize(cb.getPreferredSize()); // added code
            cb.setAlignmentX(Component.CENTER_ALIGNMENT);
            pane.add(cb);

            JButton confirm = new JButton("REMOVE");
            confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
            confirm.setAlignmentY(Component.BOTTOM_ALIGNMENT);
            pane.add(confirm);

            JComboBox finalCb = cb;
            confirm.addActionListener(e -> {
                deleteAMarket.dispose();
                writer.println("Remove marketplace");
                writer.flush();
                String marketToRemove = (String) finalCb.getSelectedItem();
                writer.println(marketToRemove);
                /*
                ArrayList<String> linesToAdd = new ArrayList<>();

                File markets = new File("Markets.txt");
                try {
                    BufferedReader bfr = new BufferedReader(new FileReader(markets));
                    String line = "";

                    while ((line = bfr.readLine()) != null) {
                        if (!line.contains(marketToRemove)) {
                            linesToAdd.add(line);
                        }
                    }
                    bfr.close();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    File tempFile = new File("myTempFile.txt");
                    BufferedWriter bfw = new BufferedWriter(new FileWriter(tempFile));

                    for (int i = 0; i < linesToAdd.size(); i++) {
                        bfw.write(linesToAdd.get(i) + "\n");
                    }
                    bfw.flush();
                    bfw.close();

                    CustomerOptions.copyFileToFile(tempFile, markets);
                    tempFile.delete();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                try {
                    File f = new File("DeletedMarkets.txt");
                    BufferedWriter bfw2 = new BufferedWriter(new FileWriter(f, true));
                    bfw2.append("\n" + marketToRemove);
                    bfw2.flush();
                    bfw2.close();

                    File customers = new File("Customers.txt");
                    BufferedReader bfr = new BufferedReader(new FileReader(customers));
                    String line = "";
                    ArrayList<String> customerArray = new ArrayList<>();
                    while ((line = bfr.readLine()) != null) {
                        customerArray.add(line);
                    }
                    bfr.close();

                    ArrayList<File> customerFileArray = new ArrayList<>();

                    for (String s : customerArray) {
                        File customersFile = new File(s + "'s File.txt");
                        customerFileArray.add(customersFile);
                    }

                    for (File file :  customerFileArray) {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                        ArrayList<String> linesInFile = new ArrayList<>();
                        while ((line = bufferedReader.readLine()) != null) {
                            if (!line.contains("," + marketToRemove + ",")) {
                                linesInFile.add(line);
                            }
                        }
                        bufferedReader.close();

                        File tempFile = new File("myTempFile.txt");
                        BufferedWriter bfw = new BufferedWriter(new FileWriter(tempFile));

                        for (String s : linesInFile) {
                            bfw.write(s + "\n");
                        }

                        bfw.flush();
                        bfw.close();
                        linesInFile.clear();
                        CustomerOptions.copyFileToFile(tempFile , file);
                    }
                    File deletedFile = new File(marketToRemove + " Market.txt");
                    deletedFile.delete();


                } catch (IOException exception) {
                    exception.printStackTrace();
                }

             */
                JOptionPane.showMessageDialog(null,"Success!","Market Deleted",
                        JOptionPane.INFORMATION_MESSAGE,null);
            });

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

    private static String marketName() {
        return JOptionPane.showInputDialog(null, "Please enter a market name", "Market", JOptionPane.QUESTION_MESSAGE);
    }
}
