import javax.swing.*;
import java.io.*;
import java.util.ArrayList;





public class CustomerOptions {

    public static void options(User user) {

        JFrame frame = new JFrame("Customer Options");
        frame.setSize(490, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Welcome " + user.getCustomerName());
        userLabel.setBounds(10, 20, 200, 25);
        panel.add(userLabel);

        JButton option1 = new JButton("Option 1");
        option1.setBounds(10, 50, 100, 100);
        option1.addActionListener(ev -> {

        });
        panel.add(option1);

        JButton option2 = new JButton("Option 2");
        option2.setBounds(130, 50, 100, 100);
        option2.addActionListener(ev -> {

        });
        panel.add(option2);

        JButton option3 = new JButton("Option 3");
        option3.setBounds(250, 50, 100, 100);
        option3.addActionListener(ev -> {

        });
        panel.add(option3);

        JButton option4 = new JButton("Option 4");
        option4.setBounds(370, 50, 100, 100);
        option4.addActionListener(ev -> {

        });
        panel.add(option4);

        JButton option5 = new JButton("Option 5");
        option5.setBounds(10, 170, 100, 100);
        option5.addActionListener(ev -> {

        });
        panel.add(option5);

        JButton option6 = new JButton("Option 6");
        option6.setBounds(130, 170, 100, 100);
        option6.addActionListener(ev -> {

        });
        panel.add(option6);

        JButton option7 = new JButton("Option 7");
        option7.setBounds(250, 170, 100, 100);
        option7.addActionListener(ev -> {

        });
        panel.add(option7);

        JButton option8 = new JButton("Option 8");
        option8.setBounds(370, 170, 100, 100);
        option8.addActionListener(ev -> {

        });
        panel.add(option8);

        JButton option9 = new JButton("Option 9");
        option9.setBounds(10, 290, 100, 100);
        option9.addActionListener(ev -> {

        });
        panel.add(option9);

        JButton option10 = new JButton("Option 10");
        option10.setBounds(130, 290, 100, 100);
        option10.addActionListener(ev -> {

        });
        panel.add(option10);

        JButton option11 = new JButton("Option 11");
        option11.setBounds(250, 290, 100, 100);
        option11.addActionListener(ev -> {

        });
        panel.add(option10);
        panel.add(option11);

        JButton option12 = new JButton("Option 12");
        option12.setBounds(370, 290, 100, 100);
        option12.addActionListener(ev -> {

        });
        panel.add(option12);





        frame.add(panel);
        frame.setVisible(true);

    }

}
