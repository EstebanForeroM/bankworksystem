package com.bankworksystem.bankworksystem.useCases;

import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.ProductType;

import java.util.HashSet;
import java.util.Set;

public class ProductSearcher {
    ProductRepository productRepository;

    Set<Product> products;

    private String productName;

    public ProductSearcher(ProductRepository productRepository) {
        this.productRepository = productRepository;
        productRepository.setChangeListener(this::onRepositoryChange);
        products = new HashSet<>();
        reloadDTO();
    }

    private void onRepositoryChange() {
        reloadDTO();
    }

    private void reloadDTO() {
        products = productRepository.getProducts();
    }

    public Set<Product> getProductsById(String id) {
        Set<Product> productsById = new HashSet<>();

        for (Product product : products) {
            if (product.getId().contains(id))
                productsById.add(product);
        }

        return productsById;
    }

    public Set<Product> getUniqueProductById(String id) {
        Set<Product> productsById = new HashSet<>();

        for (Product product : products) {
            if (product.getId().contains(id))
                productsById.add(product);
        }

        return productsById;
    }

    public String getProductName() {
        return productName;
    }

    public Set<Product> getProductsByOwner(String ownerId) {
        Set<Product> productsByOwner = new HashSet<>();

        for (Product product : products) {
            if (product.getOwnerId().contains(ownerId))
                productsByOwner.add(product);
        }

        return productsByOwner;
    }

    public Set<Product> getProductsByUniqueOwner(String ownerId) {
        Set<Product> productsByOwner = new HashSet<>();

        for (Product product : products) {
            if (product.getOwnerId().equals(ownerId))
                productsByOwner.add(product);
        }

        return productsByOwner;
    }

    public Set<Product> getProductsByType(ProductType productType) {
        Set<Product> productsByType = new HashSet<>();

        for (Product product : products) {
            if (product.getProductName().contains(productType.getName()))
                productsByType.add(product);
        }

        return productsByType;
    }
}
