package de.dhbw.domain.entities;

import de.dhbw.domain.miscellaneous.Transaction;
import de.dhbw.domain.valueObjects.ids.TransactionId;

import java.time.LocalDate;

public class Deposit implements Transaction {
    private final int amount;
    private final LocalDate dueDate;
    private final TransactionId transactionId;


    public Deposit(int amount, LocalDate dueDate) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount");

        this.amount = amount;
        this.dueDate = dueDate;
        this.transactionId = new TransactionId();
    }

    @Override
    public int getAmount() {
        return 0;
    }

    @Override
    public LocalDate getDate() {
        return dueDate;
    }

    @Override
    public TransactionId getTransactionId() {
        return transactionId;
    }
}
