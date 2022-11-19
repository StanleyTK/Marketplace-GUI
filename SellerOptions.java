import javax.swing.*;
import java.io.*;
import java.util.ArrayList;





public class SellerOptions {

    public static void options(User user) {

        // TODO this is where we will work on the Customer Options


        JFrame frame = new JFrame("YO IT WORKED ON SELLER AS WELL!");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);
        frame.add(panel);
        frame.setVisible(true);

    }

}
