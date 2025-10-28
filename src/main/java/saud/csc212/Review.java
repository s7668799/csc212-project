
package saud.csc212;


public class Review {
    
    private int reviewId;
    private int productId;
    private int customerId; 
    private int rating;
    private String comment;

    public Review(int reviewId, int productId, int customerId, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
    }



    public int getCustomerId() {
        return customerId;
    }

    public int getRating() {
        return rating;
    }
    // In Review.java
public void setRating(int rating) {
    this.rating = rating;
}

public void setComment(String comment) {
    this.comment = comment;
}
   
    public String toString() {
        return "Review{" +
               "rating=" + rating +
               ", comment='" + comment + '\'' +
               ", by_customer=" + customerId +
               '}';
    }
}