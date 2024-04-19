package de.dhbw.domain.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.dhbw.domain.miscellaneous.Transaction;
import de.dhbw.domain.valueObjects.ids.TransactionId;

import java.time.LocalDate;

public class Deposit implements Transaction {
    private final int amount;
    @JsonProperty("creditDate")
    private final LocalDate creditDate;
    private final TransactionId transactionId;


    public Deposit(int amount, LocalDate creditDate) {
        this(amount, creditDate, new TransactionId());
    }

    @JsonCreator
    private Deposit(
            @JsonProperty("amount") int amount,
            @JsonProperty("creditDate") LocalDate creditDate,
            @JsonProperty("transactionId") TransactionId transactionId
    ) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount");

        this.amount = amount;
        this.creditDate = creditDate;
        this.transactionId = transactionId;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    @JsonIgnore
    public LocalDate getDate() {
        return creditDate;
    }

    @Override
    public TransactionId getTransactionId() {
        return transactionId;
    }
}
