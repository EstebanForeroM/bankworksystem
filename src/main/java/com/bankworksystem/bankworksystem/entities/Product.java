package com.bankworksystem.bankworksystem.entities;

import java.util.Date;
import java.util.Objects;

public abstract class Product implements Identifiable {

    final private String id;

    final private String ownerId;

    protected double balance;

    private Date openingDate;

    public Product(String id, String ownerId, Date openingDate) {
        trimValues(id, ownerId);
        comproveOnlyNubers(id);
        comproveOnlyNubers(ownerId);

        if (openingDate == null)
            throw new NullPointerException("Opening date can't be null");

        if (id.isEmpty())
            throw new IllegalArgumentException("Product ID can't be empty");

        if (ownerId.isEmpty())
            throw new IllegalArgumentException("Owner ID can't be empty");

        this.openingDate = openingDate;
        this.ownerId = ownerId;
        this.id = id;
        balance = 0;
    }

    public abstract String getProductName();

    public double setBalance(double balance) {
        return this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    private void comproveOnlyNubers(String id) {
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                throw new IllegalArgumentException("ID's must contain only numbers");
            }
        }
    }

    public Date getOpeningDate() {
        if (openingDate == null) {
            throw new NullPointerException("Product not initialized");
        }

        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        if (openingDate == null)
            throw new NullPointerException("Opening date can't be null");

        this.openingDate = openingDate;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            Product product = (Product) obj;
            return product.getId().equals(this.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    private void trimValues(String... values) {
        for (String value : values) {
            value = value.trim();
        }
    }
}
