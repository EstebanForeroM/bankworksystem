package com.bankworksystem.bankworksystem.useCases;

import com.bankworksystem.bankworksystem.entities.Product;

import java.util.Set;

public class DeletionService {
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private ProductSearcher productSearcher;

    public DeletionService(ClientRepository clientRepository, ProductRepository productRepository, ProductSearcher productSearcher) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.productSearcher = productSearcher;
    }

    public void deleteClient(String clientId) {
        clientRepository.deleteClient(clientId);
        deleteClientProducts(clientId);
    }

    public void deleteProduct(String productId) {
        productRepository.deleteProduct(productId);
    }

    private void deleteClientProducts(String productId) {
        Set<Product> products = productSearcher.getProductsByUniqueOwner(productId);
        for (Product product : products) {
            productRepository.deleteProduct(product.getId());
        }
    }
}
