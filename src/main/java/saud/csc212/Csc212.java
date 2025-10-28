
package saud.csc212;


public class Csc212 {

    public static void main(String[] args) {
    
  ECommerceSystem store = new ECommerceSystem();
  store.loadData("");

try {
    System.out.println("\n--- 🧪 STARTING SYSTEM TESTS 🧪 ---");

    System.out.println("\n1. Testing findProductByName...");
    String searchName = "Laptop Pro 15";
    Product foundProduct = store.findProductByName(searchName);
    if (foundProduct != null && foundProduct.getName().equals(searchName)) {
        System.out.println("   ✅ SUCCESS: Found '" + foundProduct.getName() + "'. Price: $" + foundProduct.getPrice());
    } else {
        System.out.println("   ❌ FAILURE: Could not find '" + searchName + "'.");
    }

    System.out.println("\n2. Testing updateProductStock...");
    int productIdToUpdate = 102; // Wireless Mouse X1
    int newStock = 500;
    System.out.println("   - Updating stock for product ID " + productIdToUpdate + " to " + newStock + ".");
    store.updateProductStock(productIdToUpdate, newStock);
    Product updatedProduct = store.findProductById(productIdToUpdate);
    if (updatedProduct != null && updatedProduct.getStock() == newStock) {
        System.out.println("   ✅ SUCCESS: Stock for '" + updatedProduct.getName() + "' is now " + updatedProduct.getStock() + ".");
    } else {
        System.out.println("   ❌ FAILURE: Stock update failed.");
    }

    
    System.out.println("\n3. Testing getOutOfStockProducts...");
    store.updateProductStock(114, 0); // Laser Printer
    LinkedList<Product> outOfStock = store.getOutOfStockProducts();
    if (!outOfStock.empty()) {
        System.out.print("   ✅ SUCCESS: Found out-of-stock products: ");
        outOfStock.findfirst();
        while(true){
            System.out.print(outOfStock.retrieve().getName() + "; ");
            if(outOfStock.last()) break;
            outOfStock.findnext();
        }
        System.out.println();
    } else {
        System.out.println("   ❌ FAILURE: Did not find any out-of-stock products.");
    }
    store.updateProductStock(114, 15);

   
    System.out.println("\n4. Testing addProduct...");
    Product newProduct = new Product(999, "Test Gadget", 99.99, 100);
    store.addProduct(newProduct);
    Product addedProduct = store.findProductById(999);
    if (addedProduct != null && addedProduct.getName().equals("Test Gadget")) {
        System.out.println("   ✅ SUCCESS: Product '" + addedProduct.getName() + "' was added.");
    } else {
        System.out.println("   ❌ FAILURE: Product was not added correctly.");
    }

    System.out.println("\n5. Testing removeProduct...");
    boolean removed = store.removeProduct(999);
    Product shouldBeNull = store.findProductById(999);
    if (removed && shouldBeNull == null) {
        System.out.println("   ✅ SUCCESS: Product 'Test Gadget' was removed.");
    } else {
        System.out.println("   ❌ FAILURE: Product was not removed correctly.");
    }

    System.out.println("\n--- ✅ ALL PRODUCT TESTS COMPLETE ✅ ---");

} catch (Exception e) {
    System.err.println("\n--- ❌ A CRITICAL ERROR OCCURRED DURING TESTING ❌ ---");
    e.printStackTrace();
}

try {
    System.out.println("\n--- 🧪 STARTING CUSTOMER & ORDER TESTS 🧪 ---");

    System.out.println("\n1. Testing registerCustomer...");
    int newCustomerId = 999;
    Customer newCustomer = new Customer(newCustomerId, "Test User", "test@user.com");
    store.registerCustomer(newCustomer);
    Customer foundCustomer = store.findCustomerById(newCustomerId);
    if (foundCustomer != null && foundCustomer.getName().equals("Test User")) {
        System.out.println("   ✅ SUCCESS: Customer 'Test User' was registered.");
    } else {
        System.out.println("   ❌ FAILURE: Customer registration failed.");
    }

    System.out.println("\n2. Testing placeOrder (Successful)...");
    LinkedList<Integer> shoppingCart = new LinkedList<>();
    shoppingCart.insert(101); 
    shoppingCart.insert(105); 
    Product laptopBeforeOrder = store.findProductById(101);
    int stockBefore = laptopBeforeOrder.getStock();
    Order newOrder = store.placeOrder(newCustomerId, shoppingCart);
    Product laptopAfterOrder = store.findProductById(101);
    int stockAfter = laptopAfterOrder.getStock();

    if (newOrder != null && stockAfter == stockBefore - 1) {
        System.out.println("   ✅ SUCCESS: Order " + newOrder.getOrderId() + " placed. Stock for '" + laptopAfterOrder.getName() + "' correctly decremented to " + stockAfter + ".");
    } else {
        System.out.println("   ❌ FAILURE: Order placement or stock decrement failed.");
    }
    
  
    System.out.println("\n3. Testing viewOrderHistory...");
    LinkedList<Order> history = store.viewOrderHistory(newCustomerId);
    if (!history.empty() && history.retrieve().getOrderId() == newOrder.getOrderId()) {
        System.out.println("   ✅ SUCCESS: Found order " + newOrder.getOrderId() + " in customer's history.");
    } else {
        System.out.println("   ❌ FAILURE: Order history is incorrect or empty.");
    }

   
    System.out.println("\n4. Testing updateOrderStatus...");
    store.updateOrderStatus(newOrder.getOrderId(), "Shipped");
    Order updatedOrder = store.findOrderById(newOrder.getOrderId());
    if (updatedOrder != null && updatedOrder.getStatus().equals("Shipped")) {
        System.out.println("   ✅ SUCCESS: Order status updated to 'Shipped'.");
    } else {
        System.out.println("   ❌ FAILURE: Order status update failed.");
    }


    System.out.println("\n5. Testing cancelOrder...");
    store.cancelOrder(newOrder.getOrderId());
    Order cancelledOrder = store.findOrderById(newOrder.getOrderId());
    int stockAfterCancel = store.findProductById(101).getStock();
    if (cancelledOrder != null && cancelledOrder.getStatus().equals("Cancelled") && stockAfterCancel == stockBefore) {
        System.out.println("   ✅ SUCCESS: Order cancelled and stock for '" + laptopAfterOrder.getName() + "' correctly restocked to " + stockAfterCancel + ".");
    } else {
        System.out.println("   ❌ FAILURE: Order cancellation or item restock failed.");
    }

    System.out.println("\n--- ✅ ALL CUSTOMER & ORDER TESTS COMPLETE ✅ ---");

} catch (Exception e) {
    System.err.println("\n--- ❌ A CRITICAL ERROR OCCURRED DURING TESTING ❌ ---");
    e.printStackTrace();
}    


try {
    System.out.println("\n--- 🧪 STARTING CHALLENGING QUERY TESTS 🧪 ---");
    
    System.out.println("\n1. Testing Top 3 Rated Products...");
    LinkedList<Product> topRated = store.getTopRatedProducts();
    
    if (topRated.empty()) {
        System.out.println("   ❌ FAILURE: Could not determine top rated products.");
    } else {
        System.out.println("   ✅ SUCCESS: Top rated products are:");
        topRated.findfirst();
        int rank = 1;
        while(true) {
            Product p = topRated.retrieve();
            
         
            double avg = store.getAverageRating(p.getProductId());
            
        
            System.out.println("      #" + rank + ": " + p.getName() + " (Avg Rating: " + avg + ")");
            // --- END OF FIX ---
            
            rank++;
            if(topRated.last()) break;
            topRated.findnext();
        }
    }

    System.out.println("\n2. Testing Orders Between Dates...");
    String startDate = "2025-02-01";
    String endDate = "2025-02-10";
    LinkedList<Order> ordersInDateRange = store.getOrdersBetweenDates(startDate, endDate);

    if (ordersInDateRange.empty()) {
        System.out.println("   ❌ FAILURE: Found no orders between " + startDate + " and " + endDate);
    } else {
        System.out.println("   ✅ SUCCESS: Found orders between " + startDate + " and " + endDate + ":");
        ordersInDateRange.findfirst();
        while(true) {
            Order o = ordersInDateRange.retrieve();
            System.out.println("      - Order ID: " + o.getOrderId() + " on " + o.getOrderDate());
            if(ordersInDateRange.last()) break;
            ordersInDateRange.findnext();
        }
    }
   System.out.println("\n3. Testing Common Highly Rated Products (with a known match)...");
int customerId1 = 203; // Charlie Brown
int customerId2 = 227;
LinkedList<Product> commonProducts = store.getCommonHighlyRatedProducts(customerId1, customerId2);

if (commonProducts.empty()) {
    System.out.println("   ❌ FAILURE: No common products with >4 rating found for customers " + customerId1 + " and " + customerId2);
} else {
    System.out.println("   ✅ SUCCESS: Found common products with >4 rating for customers " + customerId1 + " and " + customerId2 + ":");
    commonProducts.findfirst();
    while(true) {
        Product p = commonProducts.retrieve();
        System.out.println("      - " + p.getName());
        if(commonProducts.last()) break;
        commonProducts.findnext();
    }
}
    System.out.println("\n--- ✅ ALL CHALLENGING QUERY TESTS COMPLETE ✅ ---");

} catch (Exception e) {
    System.err.println("\n--- ❌ A CRITICAL ERROR OCCURRED DURING TESTING ❌ ---");
    e.printStackTrace();
}
    
 }}
    

