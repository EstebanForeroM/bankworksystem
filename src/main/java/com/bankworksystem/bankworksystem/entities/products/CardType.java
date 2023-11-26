package com.bankworksystem.bankworksystem.entities.products;

public enum CardType {
    VISA("Visa"),
    MASTERCARD("Mastercard"),
    AMERICAN_EXPRESS("American Express");

    private String name;

    CardType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CardType getCardType(String name) {
        String normalizedInput = name.toUpperCase();
        for (CardType cardType : CardType.values()) {
            String normalizedEnumName = cardType.getName().toUpperCase();
            if (normalizedEnumName.equals(normalizedInput)) {
                return cardType;
            }
        }
        throw new IllegalArgumentException("Invalid product type: " + name);
    }
}
