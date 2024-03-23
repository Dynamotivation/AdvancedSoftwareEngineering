package de.dhbw.domain.entities;

import de.dhbw.domain.utilities.ContactAvenue;
import de.dhbw.domain.utilities.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Tenant {
    private final String name;
    private final String surname;
    private final ContactInformation contactInformation;
    private final List<Transaction> outstandingBalanceHistory = new ArrayList<>();
    //TODO List rental agreements

    public Tenant(String name, String surname, ContactAvenue contactAvenue) {
        this.name = name;
        this.surname = surname;
        this.contactInformation = new ContactInformation(contactAvenue);

        // TODO Validate name and surname and check if contactAvenue is not MAIL TO HIS RENTED ADDRESS
    }

    protected void addTransaction(Transaction transaction) {
        if (outstandingBalanceHistory.contains(transaction))
            throw new IllegalArgumentException("Transaction already exists");

        outstandingBalanceHistory.add(transaction);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public int getBalance() {
        int balance = 0;

        for (Transaction transaction : outstandingBalanceHistory) {
            balance += transaction.getAmount();
        }

        return balance;
    }
}
