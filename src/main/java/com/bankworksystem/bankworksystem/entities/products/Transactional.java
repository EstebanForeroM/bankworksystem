package com.bankworksystem.bankworksystem.entities.products;

public interface Transactional {
    void deposit(double amount);

    void withdraw(double amount);
}