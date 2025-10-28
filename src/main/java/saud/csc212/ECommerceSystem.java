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
            
            
            this.products.insert(new Product(productId, name, price, stock));
        }
    }
}

private void loadCustomers(String filePath) throws IOException {
    System.out.println("Loading customers from: " + filePath);
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        br.readLine(); 
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            int customerId = Integer.parseInt(values[0]);
            String name = values[1];
            String email = values[2];

      
            this.customers.insert(new Customer(customerId, name, email));
        }
    }
}

 

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
             
                targetProduct.getReviews().insert(newReview);
            }
        }
    }
}


private void loadOrders(String filePath) throws IOException {
    System.out.println("Loading orders from: " + filePath);
   

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        br.readLine(); 
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            int orderId = Integer.parseInt(values[0]);
            int customerId = Integer.parseInt(values[1]);
            String productIdsStr = values[2].replace("\"", "");
            double totalPrice = Double.parseDouble(values[3]);
            String orderDate = values[4]; 
            String status = values[5];

            Customer customer = findCustomerById(customerId);
            if (customer != null) {
                
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
   
}
public Product findProductById(int id) {
    if (this.products.empty()) {
        return null;
    }

    this.products.findfirst(); 
    while (true) {
        Product currentProduct = this.products.retrieve(); 
        if (currentProduct.getProductId() == id) {
            return currentProduct; 
        }

        if (this.products.last()) {
            break; 
        }
        
        this.products.findnext(); 
    }
    
    return null;
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
 
 


public void addProduct(Product product) {
    this.products.insert(product);
}


public boolean removeProduct(int productId) {
    Product productToRemove = findProductById(productId);
    if (productToRemove != null) {
      
        products.findfirst();
        while(true) {
            if(products.retrieve().equals(productToRemove)){
                products.remove();
                return true;
            }
            if(products.last()){
                break;
            }
            products.findnext();
        }
    }
    return false;
}

public void updateProductStock(int productId, int newStock) {
    Product productToUpdate = findProductById(productId);
    if (productToUpdate != null) {
        productToUpdate.setStock(newStock);
    } else {
        System.err.println("Product with ID " + productId + " not found.");
    }
}

public Product findProductByName(String name) {
    if (products.empty()) {
        return null;
    }
    products.findfirst();
    while (true) {
        Product currentProduct = products.retrieve();
        if (currentProduct.getName().equalsIgnoreCase(name)) {
            return currentProduct; // Found it
        }
        if (products.last()) {
            break;
        }
        products.findnext();
    }
    return null; 
}

public LinkedList<Product> getOutOfStockProducts() {
    LinkedList<Product> outOfStock = new LinkedList<>();
    if (products.empty()) {
        return outOfStock;
    }
    products.findfirst();
    while (true) {
        Product currentProduct = products.retrieve();
        if (currentProduct.getStock() == 0) {
            outOfStock.insert(currentProduct);
        }
        if (products.last()) {
            break;
        }
        products.findnext();
    }
    return outOfStock;
}



public Order findOrderById(int id) {
    if (this.orders.empty()) {
        return null;
    }
    this.orders.findfirst();
    while (true) {
        Order currentOrder = this.orders.retrieve();
        if (currentOrder.getOrderId() == id) {
            return currentOrder;
        }
        if (this.orders.last()) {
            break;
        }
        this.orders.findnext();
    }
    return null;
}
 
public void registerCustomer(Customer customer) {
    this.customers.insert(customer);
}
 
public LinkedList<Order> viewOrderHistory(int customerId) {
    Customer customer = findCustomerById(customerId);
    if (customer != null) {
        return customer.getOrders();
    }
    return new LinkedList<Order>();
}


public Order placeOrder(int customerId, LinkedList<Integer> productIds) {
    Customer customer = findCustomerById(customerId);
    if (customer == null) {
        System.err.println("Order failed: Customer ID " + customerId + " not found.");
        return null;
    }


    int newOrderId = 301; // Base ID
    if(!this.orders.empty()){
         this.orders.findfirst();
         while(!this.orders.last()) { this.orders.findnext(); } // Go to the end
         newOrderId = this.orders.retrieve().getOrderId() + 1;
    }

    Order newOrder = new Order(newOrderId, customer, 0.0, "2025-10-28", "Pending"); // Using today's date

    double totalPrice = 0;
    
    if (!productIds.empty()) {
        productIds.findfirst();
        while (true) {
            int productId = productIds.retrieve();
            Product product = findProductById(productId);

            if (product == null) {
                System.err.println("Warning: Product " + productId + " does not exist. Skipping.");
            } else if (product.getStock() <= 0) {
                System.err.println("Warning: Product '" + product.getName() + "' is out of stock. Skipping.");
            } else {
            
                newOrder.addProduct(product);
                totalPrice += product.getPrice();
               
                product.setStock(product.getStock() - 1);
            }

            if (productIds.last()) {
                break;
            }
            productIds.findnext();
        }
    }

    if (newOrder.getProducts().empty()) {
        System.err.println("Order failed: No valid products were added.");
        return null;
    }

 
    newOrder.setTotalPrice(totalPrice);
    this.orders.insert(newOrder);
        customer.getOrders().insert(newOrder);
    
    System.out.println("Order " + newOrderId + " placed successfully for " + customer.getName() + ".");
    return newOrder;
}


public boolean cancelOrder(int orderId) {
    Order order = findOrderById(orderId);
    if (order == null) {
        System.err.println("Cannot cancel: Order ID " + orderId + " not found.");
        return false;
    }

    if (order.getStatus().equals("Cancelled") || order.getStatus().equals("Delivered")) {
        System.err.println("Cannot cancel: Order is already " + order.getStatus() + ".");
        return false;
    }

   
    LinkedList<Product> productsToRestock = order.getProducts();
    if (!productsToRestock.empty()) {
        productsToRestock.findfirst();
        while (true) {
            Product p = productsToRestock.retrieve();
            p.setStock(p.getStock() + 1); // Add it back

            if (productsToRestock.last()) {
                break;
            }
            productsToRestock.findnext();
        }
    }

    order.setStatus("Cancelled"); // You need to add this setter to Order.java
    System.out.println("Order " + orderId + " has been cancelled. Items restocked.");
    return true;
}

public double getAverageRating(int productId) {
    Product product = findProductById(productId);

    
    if (product == null || product.getReviews().empty()) {
        return 0.0;
    }

    double totalRating = 0;
    int reviewCount = 0;
    LinkedList<Review> reviews = product.getReviews();
    
   
    reviews.findfirst();
    while (true) {
        Review currentReview = reviews.retrieve();
        totalRating += currentReview.getRating();
        reviewCount++;

        if (reviews.last()) {
            break;
        }
        reviews.findnext();
    }
    
   
    if (reviewCount == 0) {
        return 0.0;
    }
    
    return totalRating / reviewCount;
}
public boolean updateOrderStatus(int orderId, String newStatus) {
    Order order = findOrderById(orderId);
    if (order == null) {
        System.err.println("Cannot update: Order ID " + orderId + " not found.");
        return false;
    }
    order.setStatus(newStatus); // Assumes you have a setStatus method
    System.out.println("Order " + orderId + " status updated to " + newStatus + ".");
    return true;
}

 
public LinkedList<Product> getTopRatedProducts() {
   
    Product top1 = null;
    Product top2 = null;
    Product top3 = null;

    double avg1 = -1.0; 
    double avg2 = -1.0; 
    double avg3 = -1.0; 

    if (products.empty()) {
        return new LinkedList<>(); 
    }
    
  
    products.findfirst();
    while (true) {
        Product currentProduct = products.retrieve();
        double currentAvg = getAverageRating(currentProduct.getProductId());

       
        if (currentAvg > avg1) {
         
            avg3 = avg2;
            top3 = top2;
            avg2 = avg1;
            top2 = top1;
            avg1 = currentAvg;
            top1 = currentProduct;
        } else if (currentAvg > avg2) {
        
            avg3 = avg2;
            top3 = top2;
            avg2 = currentAvg;
            top2 = currentProduct;
        } else if (currentAvg > avg3) {
          
            avg3 = currentAvg;
            top3 = currentProduct;
        }

        if (products.last()) {
            break;
        }
        products.findnext();
    }

   
    LinkedList<Product> topProducts = new LinkedList<>();
    if (top1 != null) topProducts.insert(top1);
    if (top2 != null) topProducts.insert(top2);
    if (top3 != null) topProducts.insert(top3);
    
    return topProducts;
}

public LinkedList<Order> getOrdersBetweenDates(String startDate, String endDate) {
    LinkedList<Order> dateRangeOrders = new LinkedList<>();
    if (orders.empty()) {
        return dateRangeOrders;
    }
    
    orders.findfirst();
    while (true) {
        Order currentOrder = orders.retrieve();
        String orderDate = currentOrder.getOrderDate();

        if (orderDate.compareTo(startDate) >= 0 && orderDate.compareTo(endDate) <= 0) {
            dateRangeOrders.insert(currentOrder);
        }

        if (orders.last()) {
            break;
        }
        orders.findnext();
    }
    
    return dateRangeOrders;
}



public LinkedList<Product> getCommonHighlyRatedProducts(int customerId1, int customerId2) {
    System.out.println("--- DEBUG: Finding highly rated products for customer " + customerId1 + " ---");
    LinkedList<Product> customer1Products = getHighlyRatedReviewedProducts(customerId1, 4);
    

    if (customer1Products.empty()) {
        System.out.println("--- DEBUG: List for customer " + customerId1 + " is EMPTY. ---");
    } else {
        System.out.print("--- DEBUG: List for customer " + customerId1 + " contains: ");
        customer1Products.findfirst();
        while(true) {
            System.out.print(customer1Products.retrieve().getName() + "; ");
            if(customer1Products.last()) break;
            customer1Products.findnext();
        }
        System.out.println();
    }
    

    System.out.println("--- DEBUG: Finding highly rated products for customer " + customerId2 + " ---");
    LinkedList<Product> customer2Products = getHighlyRatedReviewedProducts(customerId2, 4);

  
    if (customer2Products.empty()) {
        System.out.println("--- DEBUG: List for customer " + customerId2 + " is EMPTY. ---");
    } else {
        System.out.print("--- DEBUG: List for customer " + customerId2 + " contains: ");
        customer2Products.findfirst();
        while(true) {
            System.out.print(customer2Products.retrieve().getName() + "; ");
            if(customer2Products.last()) break;
            customer2Products.findnext();
        }
        System.out.println();
    }
  

    LinkedList<Product> commonProducts = new LinkedList<>();

    if (customer1Products.empty() || customer2Products.empty()) {
        return commonProducts;
    }

  
    customer1Products.findfirst();
    while (true) {
        Product p1 = customer1Products.retrieve();
        
        customer2Products.findfirst();
        while (true) {
            Product p2 = customer2Products.retrieve();
            if (p1.getProductId() == p2.getProductId()) {
                commonProducts.insert(p1);
                break;
            }
            if (customer2Products.last()) {
                break;
            }
            customer2Products.findnext();
        }

        if (customer1Products.last()) {
            break;
        }
        customer1Products.findnext();
    }
    
    return commonProducts;
}

private LinkedList<Product> getHighlyRatedReviewedProducts(int customerId, int ratingThreshold) {
    LinkedList<Product> reviewedProducts = new LinkedList<>();
    if (products.empty()) {
        return reviewedProducts;
    }

    products.findfirst();
    while (true) {
        Product currentProduct = products.retrieve();
        LinkedList<Review> reviews = currentProduct.getReviews();
        
        if (!reviews.empty()) {
            reviews.findfirst();
            while (true) {
                Review currentReview = reviews.retrieve();
                if (currentReview.getCustomerId() == customerId && currentReview.getRating() > ratingThreshold) {
                    reviewedProducts.insert(currentProduct);
                    break;
                }
                if (reviews.last()) {
                    break; 
                }
                reviews.findnext();
            }
        }

 
        if (products.last()) {
            break; 
        }
        products.findnext();
    }
    return reviewedProducts;
}
}
