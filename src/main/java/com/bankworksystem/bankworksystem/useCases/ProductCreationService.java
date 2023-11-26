package com.bankworksystem.bankworksystem.useCases;

import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ProductCreationService {
    Set<String> productIds;

    ProductRepository productRepository;
    private final TokenAuthenticationService tokenAuthenticationService;
    private final ProductSearcher productSearcher;

    public ProductCreationService(ProductRepository productRepository,
                                  TokenAuthenticationService tokenAuthenticationService, ProductSearcher productSearcher) {
        this.productRepository = productRepository;
        this.productSearcher = productSearcher;
        this.tokenAuthenticationService = tokenAuthenticationService;
        productIds = new HashSet<>();
    }

    public String addProduct(Token token, ProductType productName) {

        if (!tokenAuthenticationService.validate(token))
            throw new IllegalArgumentException("Invalid token");

        comproveRepetition(token, productName);

        String productId = generateId();
        UninitializedProduct uninitializedProduct = new UninitializedProduct(productId, token.getClientId(),
                productName);
        productRepository.saveProduct(uninitializedProduct);
        return productId;
    }

    private void comproveRepetition(Token token, ProductType productName) {
        Set<Product> clientProducts = productSearcher.getProductsByOwner(token.getClientId());

        for (Product product : clientProducts) {
            if (product instanceof UninitializedProduct) {
                UninitializedProduct uninitializedProduct = (UninitializedProduct) product;
                if (uninitializedProduct.getProductType().getName().equals(productName.getName())) {
                    throw new RuntimeException("Client already has this product");
                }
            } else if (product.getProductName().equals(productName.getName())) {
                throw new RuntimeException("Client already has this product");
            }
        }
    }

    public String initializeCard(String uninitializedProductId, Date date) {
        String productId = uninitializedProductId;
        UninitializedProduct uninitializedProduct = (UninitializedProduct) productRepository
                .getProduct(uninitializedProductId);
        Card card = null;
        for (CardType cardType : CardType.values()) {
            if (uninitializedProduct.getProductType().getName().contains(cardType.getName())) {
                card = new Card(productId, "1233", date, cardType);
            }
        }

        if (card == null)
            throw new RuntimeException("Invalid product type, expected Card");

        productRepository.updateProduct(productId, card);

        return productId;
    }

    public String initializeCDT(String uninitializedProductId, Date date, int expirationMonths) {
        String productId = uninitializedProductId;
        UninitializedProduct uninitializedProduct = (UninitializedProduct) productRepository
                .getProduct(uninitializedProductId);
        CDT cdt = null;
        if (uninitializedProduct.getProductType().getName().contains("CDT")) {
            cdt = new CDT(productId, uninitializedProduct.getOwnerId(), date, expirationMonths);
        } else {
            throw new RuntimeException("Invalid product type, expected CDT");
        }
        productRepository.updateProduct(productId, cdt);
        return productId;
    }

    public String initializeAccount(String uninitializedProductId, Date date) {
        String productId = uninitializedProductId;
        UninitializedProduct uninitializedProduct = (UninitializedProduct) productRepository
                .getProduct(uninitializedProductId);
        Account account = null;
        for (AccountType accountType : AccountType.values()) {
            if (uninitializedProduct.getProductName().contains(accountType.getName())) {
                account = new Account(productId, "1233", date, accountType);
            }
        }

        if (account == null)
            throw new RuntimeException("Invalid product type, expected Account");

        productRepository.updateProduct(productId, account);

        return productId;
    }

    private String generateId() {
        String id = generateRandomId();
        while (productIds.contains(id)) {
            id = generateRandomId();
        }
        return id;
    }

    private String generateRandomId() {
        StringBuilder id = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }

        return id.toString();
    }
}
