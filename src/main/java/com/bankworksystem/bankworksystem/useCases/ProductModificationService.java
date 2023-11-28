package com.bankworksystem.bankworksystem.useCases;

import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.CDT;

import java.util.Date;

public class ProductModificationService {

    private ProductRepository productRepository;

    public ProductModificationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void modifyProductBalance(String id, double balance) {
        Product product = productRepository.getProduct(id);
        product.setBalance(balance);
        productRepository.updateProduct(id, product);
    }

    public void modifyProductOpeningDate(String id, Date openingDate) {
        Product product = productRepository.getProduct(id);
        product.setOpeningDate(openingDate);
        productRepository.updateProduct(id, product);
    }

    public void modifyCDTTimePeriod(String id, int timePeriod) {
        CDT cdt = (CDT) productRepository.getProduct(id);
        cdt.setExpirationMonths(timePeriod);
        productRepository.updateProduct(id, cdt);
    }
}
