package com.bankworksystem.bankworksystem.entities.products;

import java.util.Date;

import com.bankworksystem.bankworksystem.entities.Product;

public class CDT extends Product {

    private int expirationMonths;

    public CDT(String id, String ownerId, Date openingDate, int expirationMonths) {
        super(id, ownerId, openingDate);
        productName = "CDT";
        validateNegativeNumbers(expirationMonths);
        this.expirationMonths = expirationMonths;
    }

    public int getExpirationMonths() {
        return expirationMonths;
    }

    public void setExpirationMonths(int expirationMonths) {
        validateNegativeNumbers(expirationMonths);
        this.expirationMonths = expirationMonths;
    }

    private void validateNegativeNumbers(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Invalid amount");
    }
}
