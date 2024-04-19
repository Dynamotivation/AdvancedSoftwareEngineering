package de.dhbw.domain.aggregateRoots;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.dhbw.domain.entities.ContactInformation;
import de.dhbw.domain.entities.Deposit;
import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.entities.RentCharge;
import de.dhbw.domain.miscellaneous.ContactAvenue;
import de.dhbw.domain.miscellaneous.Transaction;
import de.dhbw.domain.valueObjects.Name;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.TenantId;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tenant {
    @JsonProperty("contactInformation")
    private final ContactInformation contactInformation;
    private final TenantId id;
    private final List<LeaseAgreementId> associatedLeaseAgreementIds;
    private final Name name;
    private final List<Transaction> outstandingBalanceHistory;

    public Tenant(String name, String surname, ContactAvenue contactAvenue) {
        this(new ContactInformation(contactAvenue), new TenantId(), new ArrayList<LeaseAgreementId>(), new Name(name, surname), new ArrayList<Transaction>());
    }

    @JsonCreator
    private Tenant(
            @JsonProperty("contactInformation") @NonNull ContactInformation contactInformation,
            @JsonProperty("id") @NonNull TenantId id,
            @JsonProperty("associatedLeaseAgreementIds") @NonNull List<LeaseAgreementId> associatedLeaseAgreementIds,
            @JsonProperty("name") @NonNull Name name,
            @JsonProperty("outstandingBalanceHistory") @NonNull List<Transaction> outstandingBalanceHistory
    ) {
        this.contactInformation = contactInformation;
        this.id = id;
        this.associatedLeaseAgreementIds = associatedLeaseAgreementIds;
        this.name = name;
        this.outstandingBalanceHistory = outstandingBalanceHistory;
    }

    public void addContactAvenue(ContactAvenue contactAvenue) {
        contactInformation.addContactAvenue(contactAvenue);
    }

    public List<ContactAvenue> getContactAvenues() {
        return new ArrayList<>(contactInformation.getContactAvenues());
    }

    public void removeContactAvenue(ContactAvenue contactAvenue) {
        contactInformation.removeContactAvenue(contactAvenue);
    }

    @JsonIgnore
    public ContactAvenue getPreferredContactAvenue() {
        return contactInformation.getPreferredContactAvenue();
    }

    public void setPreferredContactAvenue(ContactAvenue contactAvenue) {
        contactInformation.setPreferredContactAvenue(contactAvenue);
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

    public void deregisterLeaseAgreementSubscription(LeaseAgreementId leaseAgreementId, LocalDate deregistrationDate) {
        // Validate that the lease agreement id is in the list
        if (!associatedLeaseAgreementIds.contains(leaseAgreementId))
            throw new IllegalArgumentException("Lease agreement id does not exist");

        associatedLeaseAgreementIds.remove(leaseAgreementId);

        // Cleanup the balance history
        List<Transaction> wrongfulTransactions = new ArrayList<>();

        for (Transaction transaction : outstandingBalanceHistory) {
            if (transaction instanceof RentCharge rentCharge && rentCharge.getDate().isAfter(deregistrationDate)) {
                wrongfulTransactions.add(rentCharge);
            }
        }

        outstandingBalanceHistory.removeAll(wrongfulTransactions);
    }

    public Name getName() {
        return name;
    }

    // Do not poll the balance from current leases, as this is a heavy, possibly redundant, operation
    @JsonIgnore
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
        if (!associatedLeaseAgreementIds.contains(leaseAgreement.getId()))
            throw new IllegalArgumentException("Tenant does not rent the property");

        if (outstandingBalanceHistory.contains(rentCharge))
            throw new IllegalArgumentException("Double charge");

        outstandingBalanceHistory.add(rentCharge);
    }

    public void getCredited(Deposit deposit) {
        if (outstandingBalanceHistory.contains(deposit))
            throw new IllegalArgumentException("Double credit");

        outstandingBalanceHistory.add(deposit);
    }
}
