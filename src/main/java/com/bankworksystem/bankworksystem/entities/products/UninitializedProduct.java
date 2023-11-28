package com.bankworksystem.bankworksystem.entities.products;

import java.util.Date;

import com.bankworksystem.bankworksystem.entities.Product;

public class UninitializedProduct extends Product {

    private ProductType productType;

    public UninitializedProduct(String id, String ownerId, ProductType productName) {
        super(id, ownerId, new Date());
        super.productName = productName.getName();
        this.productType = productName;
    }

    public ProductType getProductType() {
        return productType;
    }

}
