package com.bankworksystem.bankworksystem.frameworks.persistency;

import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.useCases.ProductRepository;

import java.util.Set;

public class TextProductPersistency implements ProductRepository {

    private TextPersistency<Product> textPersistency;

    public TextProductPersistency(String filePath, Serializer<Product> serializer) {
        textPersistency = new TextPersistency<>(filePath, "products.txt", serializer);
    }

    @Override
    public void saveProduct(Product product) {
        textPersistency.saveObject(product);
    }

    @Override
    public Product getProduct(String id) {
        return textPersistency.getObject(id);
    }

    @Override
    public void deleteProduct(String id) {
        textPersistency.deleteObject(id);
    }

    @Override
    public void updateProduct(String id, Product product) {
        textPersistency.updateObject(id, product);
    }

    @Override
    public Set<Product> getProducts() {
        return textPersistency.getObjects();
    }

    @Override
    public void setChangeListener(Runnable callback) {
        textPersistency.addChangeListener(callback);
    }

    public void eraseAll() {
        textPersistency.eraseAll();
    }
}
