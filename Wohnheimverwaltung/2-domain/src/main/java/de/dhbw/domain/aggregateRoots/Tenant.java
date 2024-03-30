package de.dhbw.domain.aggregateRoots;

import de.dhbw.domain.entities.ContactInformation;
import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.entities.Name;
import de.dhbw.domain.entities.RentCharge;
import de.dhbw.domain.miscellaneous.ContactAvenue;
import de.dhbw.domain.miscellaneous.Transaction;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.util.ArrayList;
import java.util.List;

public class Tenant {
    private final ContactInformation contactInformation;
    private final TenantId id;
    private final List<LeaseAgreement> associatedLeaseAgreements = new ArrayList<>();
    private final Name name;
    private final List<Transaction> outstandingBalanceHistory = new ArrayList<>();

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

    public List<LeaseAgreement> getAssociatedLeaseAgreements() {
        return associatedLeaseAgreements;
    }

    public void addLeaseAgreement(LeaseAgreement leaseAgreement) {
        // Validate that the lease agreement id is not already in the list
        if (associatedLeaseAgreements.contains(leaseAgreement))
            throw new IllegalArgumentException("Lease agreement id already exists");

        associatedLeaseAgreements.add(leaseAgreement);
    }

    private void removeLeaseAgreementId(LeaseAgreementId leaseAgreementId) {
        // Validate that the lease agreement id is in the list
        if (!associatedLeaseAgreements.contains(leaseAgreementId))
            throw new IllegalArgumentException("Lease agreement id does not exist");

        associatedLeaseAgreements.remove(leaseAgreementId);
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

    public void addTransaction(Transaction transaction) {
        // Validate that the tenant rents the property they are being charged for
        if (transaction instanceof RentCharge rentCharge) {
            boolean valid = false;

            for (LeaseAgreement leaseAgreement : associatedLeaseAgreements) {
                if (leaseAgreement.getId().equals(rentCharge.getAssociatedLeaseAgreementId())) {
                    valid = true;
                    break;
                }
            }

            if (!valid)
                throw new IllegalArgumentException("Tenant does not rent the property");
        }

        // Validate that the transaction has not been added before
        if (outstandingBalanceHistory.contains(transaction))
            throw new IllegalArgumentException("Transaction already exists");

        outstandingBalanceHistory.add(transaction);
    }

    // TODO move into account entity
    public int getBalance() {
        // Lazily charge rent for all associated lease agreements
        associatedLeaseAgreements.forEach(LeaseAgreement::chargeRent);

        int balance = 0;

        for (Transaction transaction : outstandingBalanceHistory) {
            balance += transaction.getAmount();
        }

        return balance;
    }

    public List<Transaction> getOutstandingBalanceHistory() {
        return outstandingBalanceHistory;
    }
}
