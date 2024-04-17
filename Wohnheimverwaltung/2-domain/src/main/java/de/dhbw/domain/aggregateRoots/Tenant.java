package de.dhbw.domain.aggregateRoots;

import de.dhbw.domain.entities.ContactInformation;
import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.valueObjects.Name;
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
    private final List<LeaseAgreementId> associatedLeaseAgreementIds = new ArrayList<>();
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

    public List<LeaseAgreementId> getAssociatedLeaseAgreementIds() {
        return new ArrayList<>(associatedLeaseAgreementIds);
    }

    public void registerLeaseAgreementSubscription(LeaseAgreementId leaseAgreementId) {
        // Validate that the lease agreement id is not already in the list
        if (associatedLeaseAgreementIds.contains(leaseAgreementId))
            throw new IllegalArgumentException("Lease agreement id registered");

        associatedLeaseAgreementIds.add(leaseAgreementId);
    }

    private void deregisterLeaseAgreementSubscription(LeaseAgreementId leaseAgreementId) {
        // Validate that the lease agreement id is in the list
        if (!associatedLeaseAgreementIds.contains(leaseAgreementId))
            throw new IllegalArgumentException("Lease agreement id does not exist");

        associatedLeaseAgreementIds.remove(leaseAgreementId);
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

    // Do not poll the balance from current leases, as this is a heavy, possibly redundant, operation
    public int getBalance() {
        int balance = 0;

        for (Transaction transaction : outstandingBalanceHistory) {
            balance += transaction.getAmount();
        }

        return balance;
    }

    public List<Transaction> getOutstandingBalanceHistory() {
        return new ArrayList<>(outstandingBalanceHistory);
    }

    public void getCharged(LeaseAgreement leaseAgreement, RentCharge rentCharge) {
        if (!associatedLeaseAgreementIds.contains(leaseAgreement))
            throw new IllegalArgumentException("Tenant does not rent the property");

        if (outstandingBalanceHistory.contains(rentCharge))
            throw new IllegalArgumentException("Double charge");

        outstandingBalanceHistory.add(rentCharge);
    }
}
