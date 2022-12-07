import java.util.ArrayList;


/**
 * A Shopping cart class, just an arraylist for the products in each of the customers' file
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Project 5</p>
 *
 * @author Stanley Kim
 * @version December 7, 2022
 */

public class ShoppingCart {
    ArrayList<Product> cartItems;

    public ShoppingCart(ArrayList<Product> cartItems) {
        this.cartItems = cartItems;
    }

    public ArrayList<Product> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<Product> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "cartItems=" + cartItems +
                '}';
    }

}