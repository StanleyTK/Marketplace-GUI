import java.util.ArrayList;

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
        return "Customer{" +
                "shoppingCart=" + shoppingCart +
                '}';
    }
}
