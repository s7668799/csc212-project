
package saud.csc212;


public class Order {
    private int orderId;
    private Customer customer; 
    private LinkedList<Product> products; 
    private double totalPrice;
    private String orderDate; 
    private String status;

    public Order(int orderId, Customer customer, double totalPrice, String orderDate, String status) {
        this.orderId = orderId;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
        this.products = new LinkedList<>(); 
    }


    
    public int getOrderId() {
        return orderId;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public LinkedList<Product> getProducts() {
        return products;
    }
    public void setTotalPrice(double d){ this.totalPrice = d ;}
   public void setStatus(String d){ this.status = d ;}  
public void addProduct(Product product) {
    
    this.products.insert(product);
}
public String getOrderDate() {
    return this.orderDate;
}
  public String getStatus(){return status;}
    public String toString() {
        return "Order{" +
               "id=" + orderId +
               ", customer=" + customer.getName() + 
               ", total=" + totalPrice +
               ", status='" + status + '\'' +
               '}';
    }
}