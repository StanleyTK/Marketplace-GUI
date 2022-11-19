import java.util.ArrayList;
import java.io.*;
/**
 * A Market class for Marketplace
 *
 * <p>Purdue University -- CS18000 -- Fall 2022</p>
 *
 * @author Stanley Kim
 * @version November 14, 2022
 */

public class Market {

    private ArrayList<Product> products;
    private String name;

    public Market(String name, ArrayList<Product> products) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Store{" + "name=" + name +
                ", products=" + products +
                '}';
    }
}
