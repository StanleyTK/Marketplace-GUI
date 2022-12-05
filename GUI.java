import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.net.*;


public class GUI {
    final static int PORT = 8888;


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