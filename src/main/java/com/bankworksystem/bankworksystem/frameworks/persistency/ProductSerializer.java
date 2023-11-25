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

        return stringBuilder.toString();
    }

    private String serializeGeneralProduct(Product product) {
        return product.getProductName() +                    SEPARATOR
                + product.getOwnerId() +                     SEPARATOR
                + product.getId() +                          SEPARATOR
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
        } else {
            throw new IllegalArgumentException("Product is not a valid type");
        }
    }

    private String dateSerializer(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(date);
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

        ProductData productData = new ProductData();

        productData.setId(splitString[2])
                .setOwnerId(splitString[1])
                .setBalance(splitString[3])
                .setOpeningDate(splitString[4])
                .setProductName(splitString[0]);

        switch (productData.getProductName()) {
            case "CDT":
                return deserializeCDT(productData ,splitString[5]);
            case "Account":
                return deserializeAccount(productData ,splitString[5]);
            case "Card":
                return deserializeCard(productData ,splitString[5]);
            default:
                throw new IllegalArgumentException("Product is not a valid type");
        }
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
