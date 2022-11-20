public class Product {
    private String name;
    private String store;
    private String description;
    private int quantity;
    private double price;

    public Product(String name, String store, String description, int quantity, double price) {
        this.name = name;
        this.store = store;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(String line) {
        String[] info = line.split(";");
        this.name = info[0];
        this.store = info[1];
        this.description = info[2];
        this.quantity = Integer.parseInt(info[3]);
        this.price = Double.parseDouble(info[4]);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }



    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%d,%.2f", name, store, description, quantity, price);
    }
}
