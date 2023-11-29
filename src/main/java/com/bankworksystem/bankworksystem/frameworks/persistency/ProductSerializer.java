package com.bankworksystem.bankworksystem.frameworks.persistency;

import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductSerializer implements Serializer<Product> {

    private final String SEPARATOR = ",";
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    @Override
    public String serialize(Product product) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(serializeGeneralProduct(product));
        stringBuilder.append(serializeSpecificProduct(product));

        return stringBuilder.toString();
    }

    private String serializeGeneralProduct(Product product) {
        return (product instanceof UninitializedProduct ? "Uninitialized product" : product.getProductName()) +                    SEPARATOR
                + product.getId() +                          SEPARATOR
                + product.getOwnerId() +                     SEPARATOR
                + product.getBalance() +                     SEPARATOR
                + dateSerializer(product.getOpeningDate()) + SEPARATOR;
    }

    private String serializeSpecificProduct(Product product) {
        if (product instanceof Card) {
            return serializeCard((Card) product);
        } else if (product instanceof Account) {
            return serializeAccount((Account) product);
        } else if (product instanceof CDT) {
            return serializeCDT((CDT) product);
        } else if (product instanceof UninitializedProduct) {
            return serializeUninitializedProduct((UninitializedProduct) product);
        } else {
            throw new IllegalArgumentException("Product is not a valid type");
        }
    }

    private String dateSerializer(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(date);
    }

    private String serializeUninitializedProduct(UninitializedProduct product) {
        ProductType productType = product.getProductType();
        return productType.getName();
    }

    private String serializeCDT(CDT product) {
        return Integer.toString(product.getExpirationMonths());
    }

    private String serializeAccount(Account product) {
        return product.getType().getName();
    }

    private String serializeCard(Card product) {
        return product.getType().getName();
    }

    @Override
    public Product deserialize(String string) {
        String[] splitString = string.split(SEPARATOR);

        if (splitString.length < 5)
            throw new IllegalArgumentException("Invalid string format");

        ProductData productData = new ProductData();

        productData.setId(splitString[1])
                .setOwnerId(splitString[2])
                .setBalance(splitString[3])
                .setOpeningDate(splitString[4])
                .setProductName(splitString[0]);

        if (productData.getProductName().contains("CDT"))
            return deserializeCDT(productData, splitString[5]);
        else if (productData.getProductName().contains("Account"))
            return deserializeAccount(productData, splitString[5]);
        else if (productData.getProductName().contains("Card"))
            return deserializeCard(productData, splitString[5]);
        else if (productData.getProductName().contains("Uninitialized product"))
            return deserializeUninitializedProduct(productData, splitString[5]);
        else
            throw new IllegalArgumentException("Product is not a valid type");
    }

    private Product deserializeUninitializedProduct(ProductData productData, String productType) {
        return new UninitializedProduct(productData.getId(), productData.getOwnerId(), ProductType.getProductType(productType));
    }

    private Card deserializeCard(ProductData productData, String cardType) {
        Card card = new Card(productData.getId(), productData.getOwnerId(), parseDate(productData.getOpeningDate()), CardType.getCardType(cardType));
        setBalance(card, productData.getBalance());
        return card;
    }

    private Product deserializeAccount(ProductData productData, String accountType) {
        Account account = new Account(productData.getId(), productData.getOwnerId(), parseDate(productData.getOpeningDate()),
                AccountType.getAccountType(accountType));
        setBalance(account, productData.getBalance());
        return account;
    }

    private Product deserializeCDT(ProductData productData, String expirationMString) {
        int expirationMonths = Integer.parseInt(expirationMString);
        CDT cdt = new CDT(productData.getId(), productData.getOwnerId(), parseDate(productData.getOpeningDate()), expirationMonths);
        setBalance(cdt, productData.getBalance());
        return cdt;
    }

    private void setBalance(Product product, String balanceString) {
        double balance = Double.parseDouble(balanceString);
        product.setBalance(balance);
    }

    private Date parseDate(String serializedDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        try {
            return formatter.parse(serializedDate);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the possibility that the string was not in the expected format
        }
        return null;
    }
}
