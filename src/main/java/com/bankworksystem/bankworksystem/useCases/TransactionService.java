package com.bankworksystem.bankworksystem.useCases;

import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.Transactional;

public class TransactionService {

    ProductRepository productRepository;
    TokenAuthenticationService tokenAuthenticationService;

    public TransactionService(ProductRepository productRepository,
                              TokenAuthenticationService tokenAuthenticationService) {
        this.productRepository = productRepository;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    public void deposit(Token token, String productId, double amount) {
        if (!tokenAuthenticationService.validate(token))
            throw new IllegalArgumentException("Invalid token");

        Product product = productRepository.getProduct(productId);

        if (product instanceof Transactional transactional) {
            transactional.deposit(amount);
            productRepository.updateProduct(productId, product);
        } else {
            throw new IllegalArgumentException("Product is not transactional");
        }
    }

    public void withdraw(Token token, String id, double amount) {
        if (!tokenAuthenticationService.validate(token))
            throw new IllegalArgumentException("Invalid token");

        Product product = productRepository.getProduct(id);

        if (product instanceof Transactional transactional) {
            transactional.withdraw(amount);
            productRepository.updateProduct(id, product);
        } else {
            throw new IllegalArgumentException("Product is not transactional");
        }
    }
}
