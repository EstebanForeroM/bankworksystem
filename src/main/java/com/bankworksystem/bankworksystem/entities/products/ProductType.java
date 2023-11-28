package com.bankworksystem.bankworksystem.entities.products;

import com.bankworksystem.bankworksystem.entities.Product;

public enum ProductType {
    CDT("CDT"),
    VISA_CARD("Visa card"),
    MASTERCARD("Mastercard card"),
    AMERICAN_EXPRESS("American Express card"),
    CHECKING_ACCOUNT("Checking account"),
    SAVINGS_ACCOUNT("Savings account"),
    UninitializedProduct("Uninitialized product");

    private String name;

    ProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ProductType getProductType(String name) {
        String normalizedInput = name.toUpperCase();
        normalizedInput = normalizedInput.replace("_", " ");
        for (ProductType productType : ProductType.values()) {
            String normalizedEnumName = productType.getName().toUpperCase();
            if (normalizedEnumName.equals(normalizedInput)) {
                return productType;
            }
        }
        throw new IllegalArgumentException("Invalid product type: " + name);
    }

    public static ProductType getProductType(Product product) {
        if (product instanceof CDT) {
            return CDT;
        } else if (product instanceof Card) {
            CardType cardType = ((Card) product).getType();
            return switch (cardType) {
                case VISA -> VISA_CARD;
                case MASTERCARD -> MASTERCARD;
                case AMERICAN_EXPRESS -> AMERICAN_EXPRESS;
                default -> throw new IllegalArgumentException("Invalid card type: " + cardType);
            };
        } else if (product instanceof Account) {
            AccountType accountType = ((Account) product).getType();
            return switch (accountType) {
                case CHECKING -> CHECKING_ACCOUNT;
                case SAVINGS -> SAVINGS_ACCOUNT;
                default -> throw new IllegalArgumentException("Invalid account type: " + accountType);
            };
        } else if (product instanceof UninitializedProduct) {
            return UninitializedProduct;
        } else {
            throw new IllegalArgumentException("Invalid product type: " + product.getClass().getName());
        }
    }
}
