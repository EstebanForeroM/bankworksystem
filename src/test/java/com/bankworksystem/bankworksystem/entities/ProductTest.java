package com.bankworksystem.bankworksystem.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    public void testProductCreation() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Product("e04fsde", "004175", new Date()) {
                @Override
                public String getProductName() {
                    return "productName";
                }
            };
        }, "Product ID must contain only numbers");
    }

    @Test
    public void testGetProductId() {
        Product product = new Product("004175", "004175", new Date()) {
            @Override
            public String getProductName() {
                return "productName";
            }
        };

        assert product.getId().equals("004175");
    }

    @Test
    public void testGetProductName() {
        Product product = new Product("004175", "004175", new Date()) {
            @Override
            public String getProductName() {
                return "productName";
            }
        };

        assert product.getProductName().equals("productName");
    }

    @Test
    protected void getProductBalance() {
        Product product = new Product("001257", "001257", new Date()) {
            @Override
            public String getProductName() {
                return "productName";
            }
        };

        assert product.getBalance() == 0;
    }

    @Test
    public void getOpeningDate() {
        Product product = new Product("001257", "001257", new Date()) {
            @Override
            public String getProductName() {
                return "productName";
            }
        };

        assertThrows(NullPointerException.class, () -> {
            product.getOpeningDate();
        }, "Product not initialized");
    }

    @Test
    public void getOwnerId() {
        Product product = new Product("001257", "001257", new Date()) {
            @Override
            public String getProductName() {
                return "productName";
            }
        };

        assertThrows(NullPointerException.class, () -> {
            new Product("001257", "123", null) {
                @Override
                public String getProductName() {
                    return "productName";
                }
            };
        });

        assertThrows(NullPointerException.class, () -> {
            new Product("001257", null, new Date()) {
                @Override
                public String getProductName() {
                    return "productName";
                }
            };
        });

        assertThrows(NullPointerException.class, () -> {
            new Product(null, "123", new Date()) {
                @Override
                public String getProductName() {
                    return "productName";
                }
            };
        });

        assert product.getOwnerId().equals("001257");
    }
}