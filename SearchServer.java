import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A GUI Server for Marketplace
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Homework 11 -- Challenge</p>
 *
 * @author Stanley Kim
 * @version November 19, 2022
 */




public class SearchServer {


    public static void main(String[] args) {
        Random random = new Random();
        try {
            ArrayList<String> lines = Login.getTextInfo(new File("ServerPortNumbers.txt"));
            int portNum = random.nextInt(1050, 10000);
            for (String line : lines) {
                if (Integer.parseInt(line) == portNum) {
                    portNum = random.nextInt(1050, 10000);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
