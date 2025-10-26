
package saud.csc212;


public class Order {
    private int orderId;
    private Customer customer; // A direct reference to a Customer object
    private LinkedList<Product> products; // References to Product objects
    private double totalPrice;
    private String orderDate; // Or you can use String if date logic is simple
    private String status;

    public Order(int orderId, Customer customer, double totalPrice, String orderDate, String status) {
        this.orderId = orderId;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
        this.products = new LinkedList<>(); // CRITICAL: Initialize the list!
    }

    // --- Getters and a method to add products ---
    
    public int getOrderId() {
        return orderId;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public LinkedList<Product> getProducts() {
        return products;
    }
    

  
    public String toString() {
        return "Order{" +
               "id=" + orderId +
               ", customer=" + customer.getName() + 
               ", total=" + totalPrice +
               ", status='" + status + '\'' +
               '}';
    }
}