import java.util.ArrayList;

/**
 * A Customer class that contains information, such as shopping cart, username, password, and name
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Project 5</p>
 *
 * @author Stanley Kim
 * @version December 7, 2022
 */

public class Customer extends User {

    private ShoppingCart shoppingCart;

    public Customer(String customerName, String username, String password, ArrayList<Product> products) {
        super(customerName, username, password);
    }
    public Customer(String customerName, String username, String password) {
        super(customerName, username, password);
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public String toString() {
        return String.format("Customer;%s;%s;%s;%s", getCustomerName(), getUsername(), getPassword(), shoppingCart);
    }
}
