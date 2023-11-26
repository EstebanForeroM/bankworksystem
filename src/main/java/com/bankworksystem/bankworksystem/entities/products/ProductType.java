package com.bankworksystem.bankworksystem.entities.products;

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
        for (ProductType productType : ProductType.values()) {
            String normalizedEnumName = productType.getName().toUpperCase();
            if (normalizedEnumName.equals(normalizedInput)) {
                return productType;
            }
        }
        throw new IllegalArgumentException("Invalid product type: " + name);
    }

}
