package com.bankworksystem.bankworksystem.frameworks.persistency;

import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TextProductPersistencyTest {

    private TextProductPersistency productPersistency;
    private static final String FILE_PATH = "src/test/resources/";
    private ProductSerializer serializer;

    @BeforeEach
    public void setUp() {
        serializer = new ProductSerializer();
        productPersistency = new TextProductPersistency(FILE_PATH, serializer);
    }

    @AfterEach
    public void tearDown() {
        productPersistency.eraseAll();
    }

    @Test
    public void testSaveAndGetProduct() {
        Product product = new Account("001", "1001", new Date(), AccountType.CHECKING);
        productPersistency.saveProduct(product);

        Product retrievedProduct = productPersistency.getProduct("001");
        assertNotNull(retrievedProduct);
        assertEquals(product.getId(), retrievedProduct.getId());
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Account("002", "1002", new Date(), AccountType.CHECKING);
        productPersistency.saveProduct(product);
        productPersistency.deleteProduct("002");

        assertNull(productPersistency.getProduct("002"));
    }

    @Test
    public void testUpdateProduct() {
        Product originalProduct = new Account("003", "1003", new Date(), AccountType.CHECKING);
        productPersistency.saveProduct(originalProduct);

        Account updatedProduct = new Account("003", "1003", new Date(), AccountType.SAVINGS);
        productPersistency.updateProduct("003", updatedProduct);

        Product retrievedProduct = productPersistency.getProduct("003");
        assertNotNull(retrievedProduct);
        assertTrue(retrievedProduct instanceof Account);
        assertEquals(AccountType.SAVINGS, ((Account) retrievedProduct).getType());
    }

    @Test
    public void testGetProducts() {
        Product product1 = new Account("004", "1004", new Date(), AccountType.CHECKING);
        Product product2 = new Account("005", "1005", new Date(), AccountType.SAVINGS);
        productPersistency.saveProduct(product1);
        productPersistency.saveProduct(product2);

        Set<Product> products = productPersistency.getProducts();
        assertEquals(2, products.size());
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
    }
}
