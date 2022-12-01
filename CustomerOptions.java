import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;





public class CustomerOptions {

    public static void options(User user, BufferedReader br, PrintWriter writer) {

        JFrame frame = new JFrame("Customer Options");
        JPanel panel = new JPanel();
        frame.setSize(490, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Welcome " + user.getCustomerName());
        userLabel.setBounds(10, 20, 200, 25);
        panel.add(userLabel);
        writer.println("Customer");
        writer.flush();

        //View the MarketPlace
        JButton option1 = new JButton("View the Marketplace");
        option1.setBounds(10, 50, 100, 100);
        option1.addActionListener(ev -> {
            writer.println("1");
            writer.flush();
            try {
                String line = br.readLine();
                System.out.println(line);
//                String [] split = line.split(";;");
//                for (String s : split) {
//                    System.out.println(s);
//                }
            } catch (IOException e) {
                System.out.println("There was an error");
            }


        });
        panel.add(option1);

        JButton option2 = new JButton("Option 2");
        option2.setBounds(130, 50, 100, 100);
        option2.addActionListener(ev -> {
            writer.println("2");
            writer.flush();


        });
        panel.add(option2);

        JButton option3 = new JButton("Sort by Price");
        option3.setBounds(250, 50, 100, 100);
        option3.addActionListener(ev -> {
            writer.println("3");
            writer.flush();


        });
        panel.add(option3);

        JButton option4 = new JButton("Sort by Quantity");
        option4.setBounds(370, 50, 100, 100);
        option4.addActionListener(ev -> {
            writer.println("4");
            writer.flush();


        });
        panel.add(option4);

        JButton option5 = new JButton("Option 5");
        option5.setBounds(10, 170, 100, 100);
        option5.addActionListener(ev -> {
            writer.println("5");
            writer.flush();


        });
        panel.add(option5);

        JButton option6 = new JButton("Option 6");
        option6.setBounds(130, 170, 100, 100);
        option6.addActionListener(ev -> {
            writer.println("6");
            writer.flush();


        });
        panel.add(option6);

        JButton option7 = new JButton("Option 7");
        option7.setBounds(250, 170, 100, 100);
        option7.addActionListener(ev -> {
            writer.println("7");
            writer.flush();


        });
        panel.add(option7);

        JButton option8 = new JButton("Option 8");
        option8.setBounds(370, 170, 100, 100);
        option8.addActionListener(ev -> {
            writer.println("8");
            writer.flush();


        });
        panel.add(option8);

        JButton option9 = new JButton("Option 9");
        option9.setBounds(10, 290, 100, 100);
        option9.addActionListener(ev -> {
            writer.println("9");
            writer.flush();


        });
        panel.add(option9);

        JButton option10 = new JButton("View your Shopping Cart");
        option10.setBounds(130, 290, 100, 100);
        option10.addActionListener(ev -> {
            writer.println("10");
            writer.flush();
            try {
                System.out.println(br.readLine());
            } catch (IOException e) {
                System.out.println("There was an error");
            }

        });
        panel.add(option10);

        JButton option11 = new JButton("Option 11");
        option11.setBounds(250, 290, 100, 100);
        option11.addActionListener(ev -> {
            writer.println("11");
            writer.flush();


        });
        panel.add(option10);
        panel.add(option11);

        JButton option12 = new JButton("Exit");
        option12.setBounds(370, 290, 100, 100);
        option12.addActionListener(ev -> {
            writer.println("12");
            writer.flush();
            // Exit the program
            if (JOptionPane.showConfirmDialog(frame, "Confirm if you Want to Exit", "Confirmation",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                System.exit(0);
        });
        panel.add(option12);


        frame.add(panel);
        frame.setVisible(true);


    }

}
