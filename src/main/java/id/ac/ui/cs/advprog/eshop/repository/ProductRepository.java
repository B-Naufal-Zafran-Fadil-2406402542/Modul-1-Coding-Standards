package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.exception.*;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {

        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product getProductById(String productId) {
        for(Product p : productData) {
            if(p.getProductId().equals(productId)) return p;
        }
        throw new ProductNotFoundException("Product id " + productId + " is not found.");
    }

    public void updateProductData(Product updatedProduct) {
        Product product = getProductById(updatedProduct.getProductId());
        product.setProductName(updatedProduct.getProductName());
        product.setProductQuantity(updatedProduct.getProductQuantity());
    }

    public void deleteProductById(String productId) {
        productData.removeIf(p -> p.getProductId().equals(productId));
    }
}
