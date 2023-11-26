package com.bankworksystem.bankworksystem.frameworks;

import com.bankworksystem.bankworksystem.frameworks.persistency.ClientSerializer;
import com.bankworksystem.bankworksystem.frameworks.persistency.ProductSerializer;
import com.bankworksystem.bankworksystem.frameworks.persistency.TextClientPersistency;
import com.bankworksystem.bankworksystem.frameworks.persistency.TextProductPersistency;
import com.bankworksystem.bankworksystem.frameworks.validations.TokenGenerator;
import com.bankworksystem.bankworksystem.frameworks.validations.TokenValidator;
import com.bankworksystem.bankworksystem.useCases.*;

public class Services {

    static private ClientRepository clientRepository;
    static private ProductRepository productRepository;
    static private PasswordManager passwordManager;

    private static TokenAuthenticationService tokenAuthenticationService;
    private static ClientSearcher clientSearcher;
    private static ClientCreationService userCreationService;
    private static ClientModificationService userModificationService;
    private static ProductSearcher productSearcher;
    private static ProductModificationService productModificationService;
    private static ProductCreationService productCreationService;
    private static TransactionService transactionService;
    private static DeletionService deletionService;

    public static void initializeServices() {
        clientRepository = new TextClientPersistency("src\\data\\users", new ClientSerializer());
        productRepository = new TextProductPersistency("src\\data\\products", new ProductSerializer());
        tokenAuthenticationService = new TokenGenerator(new TokenValidator(), clientRepository);
        passwordManager = new PasswordManager(clientRepository);
        clientSearcher = new ClientSearcher(clientRepository);
        userCreationService = new ClientCreationService(clientRepository, passwordManager);
        userModificationService = new ClientModificationService(tokenAuthenticationService, clientRepository,
                passwordManager);
        productSearcher = new ProductSearcher(productRepository);
        productModificationService = new ProductModificationService(productRepository);
        productCreationService = new ProductCreationService(productRepository, tokenAuthenticationService,
                productSearcher);
        transactionService = new TransactionService(productRepository, tokenAuthenticationService);
        deletionService = new DeletionService(clientRepository, productRepository, productSearcher);
    }

    public static void addOnClientAddedListener(Runnable listener) {
        if (clientRepository == null)
            throw new RuntimeException("Services not initialized");
        clientRepository.setChangeListener(listener);
    }

    public static TokenAuthenticationService getTokenAuthenticationService() {
        if (tokenAuthenticationService == null)
            throw new RuntimeException("Services not initialized");
        return tokenAuthenticationService;
    }

    public static ClientSearcher getClientSearcher() {
        if (clientSearcher == null)
            throw new RuntimeException("Services not initialized");
        return clientSearcher;
    }

    public static ClientCreationService getUserCreationService() {
        if (userCreationService == null)
            throw new RuntimeException("Services not initialized");
        return userCreationService;
    }

    public static ClientModificationService getUserModificationService() {
        if (userModificationService == null)
            throw new RuntimeException("Services not initialized");
        return userModificationService;
    }

    public static ProductSearcher getProductSearcher() {
        if (productSearcher == null)
            throw new RuntimeException("Services not initialized");
        return productSearcher;
    }

    public static ProductModificationService getProductModificationService() {
        if (productModificationService == null)
            throw new RuntimeException("Services not initialized");
        return productModificationService;
    }

    public static ProductCreationService getProductCreationService() {
        if (productCreationService == null)
            throw new RuntimeException("Services not initialized");
        return productCreationService;
    }

    public static TransactionService getTransactionService() {
        if (transactionService == null)
            throw new RuntimeException("Services not initialized");
        return transactionService;
    }

    public static DeletionService getDeletionService() {
        if (deletionService == null)
            throw new RuntimeException("Services not initialized");
        return deletionService;
    }
}
