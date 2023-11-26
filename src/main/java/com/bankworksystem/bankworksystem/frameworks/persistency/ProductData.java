package com.bankworksystem.bankworksystem.frameworks.persistency;

public class ProductData {

    private String productName;
    private String id;
    private String ownerId;
    private String openingDate;
    private String balance;

    public ProductData setId(String id) {
        this.id = id;
        return this;
    }

    public ProductData setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public ProductData setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
        return this;
    }

    public ProductData setBalance(String balance) {
        this.balance = balance;
        return this;
    }

    public ProductData setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public String getBalance() {
        return balance;
    }

    public String getProductName() {
        return productName;
    }
}
