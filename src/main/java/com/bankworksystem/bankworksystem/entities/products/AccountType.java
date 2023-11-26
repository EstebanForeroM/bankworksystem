package com.bankworksystem.bankworksystem.entities.products;

public enum AccountType {
    SAVINGS("Savings"),
    CHECKING("Checking");

    private String name;

    AccountType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AccountType getAccountType(String name) {
        String normalizedInput = name.toUpperCase();
        for (AccountType accountType : AccountType.values()) {
            String normalizedEnumName = accountType.getName().toUpperCase();
            if (normalizedEnumName.equals(normalizedInput)) {
                return accountType;
            }
        }
        throw new IllegalArgumentException("Invalid account type: " + name);
    }
}
