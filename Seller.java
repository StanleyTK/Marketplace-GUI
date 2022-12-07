import java.util.ArrayList;
import java.io.*;

/**
 * A Seller class that stores the customer name, username, and password
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Project 5</p>
 *
 * @author Stanley Kim
 * @version December 7, 2022
 */

public class Seller extends User {
    public Seller(String customerName, String username, String password) {
        super(customerName, username, password);

    }
    @Override
    public String toString() {
        return String.format("Seller;%s;%s;%s", getCustomerName(), getUsername(), getPassword());

    }
}
