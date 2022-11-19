public class User {
    private String customerName;
    private String password;
    private String username;

    public User(String customerName, String username, String password) {
        this.customerName = customerName;
        this.password = password;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "customerName='" + customerName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
