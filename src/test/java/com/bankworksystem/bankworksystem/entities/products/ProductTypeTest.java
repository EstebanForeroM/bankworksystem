package com.bankworksystem.bankworksystem.entities.products;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {

    @Test
    void getNameTest() {
        assertEquals("CDT", ProductType.CDT.getName());
        assertEquals("Visa card", ProductType.VISA_CARD.getName());
        assertEquals("Mastercard card", ProductType.MASTERCARD.getName());
        assertEquals("American Express card", ProductType.AMERICAN_EXPRESS.getName());
        assertEquals("Checking account", ProductType.CHECKING_ACCOUNT.getName());
        assertEquals("Savings account", ProductType.SAVINGS_ACCOUNT.getName());
        assertEquals("Uninitialized product", ProductType.UninitializedProduct.getName());
    }

    @Test
    void getProductTypeValidNameTest() {
        assertEquals(ProductType.CDT, ProductType.getProductType("CDT"));
        assertEquals(ProductType.VISA_CARD, ProductType.getProductType("Visa card"));
        assertEquals(ProductType.MASTERCARD, ProductType.getProductType("Mastercard card"));
        assertEquals(ProductType.AMERICAN_EXPRESS, ProductType.getProductType("American Express card"));
        assertEquals(ProductType.CHECKING_ACCOUNT, ProductType.getProductType("Checking account"));
        assertEquals(ProductType.SAVINGS_ACCOUNT, ProductType.getProductType("Savings account"));
        assertEquals(ProductType.UninitializedProduct, ProductType.getProductType("Uninitialized product"));
    }

    @Test
    void getProductTypeInvalidNameTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ProductType.getProductType("Invalid type");
        });
        assertEquals("Invalid product type: Invalid type", exception.getMessage());
    }

    @Test
    void getProductTypeCaseInsensitiveTest() {
        assertEquals(ProductType.CDT, ProductType.getProductType("cdt"));
        assertEquals(ProductType.VISA_CARD, ProductType.getProductType("visa card"));
        assertEquals(ProductType.MASTERCARD, ProductType.getProductType("mastercard card"));
        assertEquals(ProductType.AMERICAN_EXPRESS, ProductType.getProductType("american express card"));
        assertEquals(ProductType.CHECKING_ACCOUNT, ProductType.getProductType("checking account"));
        assertEquals(ProductType.SAVINGS_ACCOUNT, ProductType.getProductType("savings account"));
        assertEquals(ProductType.UninitializedProduct, ProductType.getProductType("uninitialized product"));
    }
}
