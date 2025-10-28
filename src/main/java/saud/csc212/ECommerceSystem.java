/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package saud.csc212;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author huawei
 */
public class ECommerceSystem {
 private LinkedList<Product> products;
private LinkedList<Customer> customers;
private LinkedList<Order> orders;
 public ECommerceSystem() {
    this.products = new LinkedList<>();
    this.customers = new LinkedList<>();
    this.orders = new LinkedList<>();
    System.out.println("ECommerceSystem initialized.");
}
 public void loadData(String basePath) {
        try {
            // The loading sequence is critical. Do it in this order.
            loadProducts(basePath + "prodcuts.csv");
            loadCustomers(basePath + "customers.csv");
            loadReviews(basePath + "reviews.csv");
            loadOrders(basePath + "orders.csv");
            System.out.println("All data loaded successfully!");
        } catch (IOException e) {
            System.err.println("FATAL: Could not load data files. " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- Private Loaders ---
   // In ECommerceSystem.java

private void loadProducts(String filePath) throws IOException {
    System.out.println("Loading products from: " + filePath);
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        br.readLine(); // Skip header
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            int productId = Integer.parseInt(values[0]);
            String name = values[1];
            double price = Double.parseDouble(values[2]);
            int stock = Integer.parseInt(values[3]);
            
            // Using your insert method
            this.products.insert(new Product(productId, name, price, stock));
        }
    }
}

private void loadCustomers(String filePath) throws IOException {
    System.out.println("Loading customers from: " + filePath);
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        br.readLine(); // Skip header
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            int customerId = Integer.parseInt(values[0]);
            String name = values[1];
            String email = values[2];

            // Using your insert method
            this.customers.insert(new Customer(customerId, name, email));
        }
    }
}

   // In ECommerceSystem.java

private void loadReviews(String filePath) throws IOException {
    System.out.println("Loading reviews from: " + filePath);
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        br.readLine(); // Skip header
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",", 5);
            int reviewId = Integer.parseInt(values[0]);
            int productId = Integer.parseInt(values[1]);
            int customerId = Integer.parseInt(values[2]);
            int rating = Integer.parseInt(values[3]);
            String comment = values[4].replace("\"", "");

            Product targetProduct = findProductById(productId);
            if (targetProduct != null) {
                Review newReview = new Review(reviewId, productId, customerId, rating, comment);
                // Get the product's internal review list and insert the new review
                targetProduct.getReviews().insert(newReview);
            }
        }
    }
}

// In your ECommerceSystem.java

private void loadOrders(String filePath) throws IOException {
    System.out.println("Loading orders from: " + filePath);
    // REMOVED: No need for SimpleDateFormat anymore

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        br.readLine(); // Skip header
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            int orderId = Integer.parseInt(values[0]);
            int customerId = Integer.parseInt(values[1]);
            String productIdsStr = values[2].replace("\"", "");
            double totalPrice = Double.parseDouble(values[3]);
            String orderDate = values[4]; // <-- THE CHANGE IS HERE. Read as a simple String.
            String status = values[5];

            Customer customer = findCustomerById(customerId);
            if (customer != null) {
                // Pass the 'orderDate' string directly to the constructor.
                Order newOrder = new Order(orderId, customer, totalPrice, orderDate, status);

                String[] productIds = productIdsStr.split(";");
                for (String pid : productIds) {
                    int productId = Integer.parseInt(pid);
                    Product product = findProductById(productId);
                    if (product != null) {
                        newOrder.addProduct(product);
                    }
                }
                
                this.orders.insert(newOrder);
                customer.getOrders().insert(newOrder);
            }
        }
    }
    // REMOVED: The 'catch' for ParseException is no longer needed.
}
public Product findProductById(int id) {
    if (this.products.empty()) {
        return null; // Don't search if the list is empty
    }

    this.products.findfirst(); // Move the internal 'current' pointer to the head
    while (true) {
        Product currentProduct = this.products.retrieve(); // Get the data at 'current'
        if (currentProduct.getProductId() == id) {
            return currentProduct; // Found it
        }

        if (this.products.last()) {
            break; // We're at the end of the list, stop the loop
        }
        
        this.products.findnext(); // Move to the next node
    }
    
    return null; // Looped through everything, didn't find it
}


public Customer findCustomerById(int id) {
    if (this.customers.empty()) {
        return null;
    }

    this.customers.findfirst();
    while (true) {
        Customer currentCustomer = this.customers.retrieve();
        if (currentCustomer.getCustomerId() == id) {
            return currentCustomer;
        }

        if (this.customers.last()) {
            break;
        }

        this.customers.findnext();
    }
    
    return null;
}
 
 
 
 
 
 
}
