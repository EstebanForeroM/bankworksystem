package com.bankworksystem.bankworksystem.useCases;

import com.bankworksystem.bankworksystem.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class DeletionService {
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private ProductSearcher productSearcher;

    private List<Consumer<String>> listeners;

    public DeletionService(ClientRepository clientRepository, ProductRepository productRepository, ProductSearcher productSearcher) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.productSearcher = productSearcher;
        listeners = new ArrayList<>();
    }

    public void deleteClient(String clientId) {
        clientRepository.deleteClient(clientId);
        deleteClientProducts(clientId);
        listeners.forEach(listener -> listener.accept(clientId));
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

    public void addOnClientDeletedListener(Consumer<String> listener) {
        listeners.add(listener);
    }
}
