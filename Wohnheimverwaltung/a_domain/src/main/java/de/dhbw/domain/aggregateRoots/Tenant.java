package de.dhbw.domain.aggregateRoots;

import de.dhbw.domain.entities.ContactInformation;
import de.dhbw.domain.entities.Name;
import de.dhbw.domain.miscellaneous.ContactAvenue;
import de.dhbw.domain.miscellaneous.Transaction;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.util.ArrayList;
import java.util.List;

public class Tenant {
    private final ContactInformation contactInformation;
    private final TenantId id;
    private final Name name;
    private final List<Transaction> outstandingBalanceHistory = new ArrayList<>();
    //TODO List rental agreements

    public Tenant(String name, String surname, ContactAvenue contactAvenue) {
        this.name = new Name(name, surname);
        this.contactInformation = new ContactInformation(contactAvenue);
        this.id = new TenantId();
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void addContactAvenue(ContactAvenue contactAvenue) {
        contactInformation.addContactAvenue(contactAvenue);
    }

    public void removeContactAvenue(ContactAvenue contactAvenue) {
        contactInformation.removeContactAvenue(contactAvenue);
    }

    public TenantId getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public String getSurname() {
        return name.getSurname();
    }

    public String getFullName() {
        return name.getFullName();
    }

    protected void addTransaction(Transaction transaction) {
        if (outstandingBalanceHistory.contains(transaction))
            throw new IllegalArgumentException("Transaction already exists");

        outstandingBalanceHistory.add(transaction);
    }

    public int getBalance() {
        int balance = 0;

        for (Transaction transaction : outstandingBalanceHistory) {
            balance += transaction.getAmount();
        }

        return balance;
    }
}
