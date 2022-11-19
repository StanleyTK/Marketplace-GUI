import java.util.ArrayList;

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

    //Method to find the total cost of items in a shopping cart.
    public double totalCost() {
        double totalCost = 0;
        for (Product cartItem : cartItems) {
            double itemCost = cartItem.getQuantity() * cartItem.getPrice(); //Cost of each item.
            totalCost += itemCost;
        }
        return totalCost; //Cost of all the items in the cart
    }

    //Method to add an Item to the cart
    public void addItem(Product product) {
        cartItems.add(product); //Adds the product to the cartItems list
        this.setCartItems(cartItems); //Changes the shopping cart
    }

    //Method to remove an item from the cart
    public void removeItem(Product product) {
        for (int i = 0; i < cartItems.size(); i++) { //Iterates through each item in cartItems
            if (cartItems.get(i).equals(product)) { //Checks if it equals product
                cartItems.remove(i); //Removes the required item
            }
        }
        this.setCartItems(cartItems); //Changes the shopping cart
    }

    //Method to remove an item from the cart
    public void removeItem(Product product, int amount) {
        int amountremoved = 0;
        while (amountremoved < amount) {
            for (int i = 0; i < cartItems.size(); i++) { //Iterates through each item in cartItems
                if (cartItems.get(i).equals(product)) { //Checks if it equals product
                    cartItems.remove(i); //Removes the required item
                    amountremoved++;
                }
            }
        }
        this.setCartItems(cartItems); //Changes the shopping cart
    }
}