package de.dhbw.domain.miscellaneous;

import de.dhbw.domain.valueObjects.ids.TransactionId;

import java.time.LocalDate;

public interface Transaction {
    int getAmount();
    LocalDate getDate();
    TransactionId getTransactionId();
}
