package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product {
    private String productId;
    private String productName;
    private int productQuantity;

    public String  getProductId() {
        return productId;
    }

    public String  getProductName() {
        return productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductId(String id) {
        this.productId = id;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public void setProductQuantity(int qty) {
        this.productQuantity = qty;
    }


}
