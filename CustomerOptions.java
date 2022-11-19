import javax.swing.*;
import java.io.*;
import java.util.ArrayList;





public class CustomerOptions {

    public static void options(User user) {

        // TODO this is where we will work on the Customer Options
        JFrame frame = new JFrame("YO IT WORKED!");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Welcome " + user.getCustomerName());
        userLabel.setBounds(10, 20, 200, 25);
        panel.add(userLabel);
        frame.add(panel);
        frame.setVisible(true);

    }

}
