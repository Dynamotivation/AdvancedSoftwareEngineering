package de.dhbw.domain.entities;

import de.dhbw.domain.miscellaneous.Transaction;

import java.time.LocalDate;

public class Deposit implements Transaction {
    private final int amount;
    private final LocalDate dueDate;


    public Deposit(int amount, LocalDate dueDate) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount");

        this.amount = amount;
        this.dueDate = dueDate;
    }

    @Override
    public int getAmount() {
        return 0;
    }

    @Override
    public LocalDate getDate() {
        return dueDate;
    }
}
