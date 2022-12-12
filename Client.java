import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.net.*;

/**
 * A Clients class that gets the information from the server, and displays on the GUI
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Project 5</p>
 *
 * @author Stanley Kim
 * @version December 7, 2022
 */



public class Client {
    final static int PORT = 7776;


    public static void main(String[] args) {
        Socket socket;
        try {
            socket = new Socket("localhost", PORT);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            Login.login(br, writer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "The Server is down. Please try again later",
                    "Cannot connect to the server", JOptionPane.ERROR_MESSAGE);
        }
    }
}