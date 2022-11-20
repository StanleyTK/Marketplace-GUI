import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.net.*;


public class GUI {


    public static void main(String[] args) {
        int WIDTH = 490;
        int HEIGHT = 500;
        Font header = new Font("Calibri", Font.BOLD, 25);
        Font font = new Font("Calibri", Font.PLAIN, 18);

        try {
            Socket socket = new Socket("localhost", 1234);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            Login.login(br, writer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "The Server is down. Please try again later",
                    "Cannot connect to the server", JOptionPane.ERROR_MESSAGE);

        }



    }

}