package com.bankworksystem.bankworksystem.useCases;

import com.bankworksystem.bankworksystem.entities.Product;

import java.util.Set;

public interface ProductRepository {

    void saveProduct(Product product);

    Product getProduct(String id);

    void deleteProduct(String id);

    void updateProduct(String id, Product product);

    Set<Product> getProducts();

    void setChangeListener(Runnable callback);
}
