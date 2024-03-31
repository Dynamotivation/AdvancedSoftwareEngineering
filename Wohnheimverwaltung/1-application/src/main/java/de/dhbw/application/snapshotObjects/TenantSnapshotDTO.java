package de.dhbw.application.snapshotObjects;

import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.entities.ContactInformation;
import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.valueObjects.Name;
import de.dhbw.domain.miscellaneous.Transaction;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.util.ArrayList;
import java.util.List;

public class TenantSnapshotDTO {
    private final ContactInformation contactInformation;
    private final TenantId id;
    private final List<LeaseAgreement> associatedLeaseAgreements;
    private final Name name;
    private final List<Transaction> outstandingBalanceHistory;
    private final int balance;

    public TenantSnapshotDTO(Tenant tenant) {
        this.name = new Name(tenant.getName(), tenant.getSurname());
        this.contactInformation = tenant.getContactInformation();
        this.id = tenant.getId();
        this.associatedLeaseAgreements = tenant.getAssociatedLeaseAgreements();
        this.outstandingBalanceHistory = tenant.getOutstandingBalanceHistory();
        this.balance = tenant.getBalance();
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public TenantId getId() {
        return id;
    }

    public List<LeaseAgreement> getAssociatedLeaseAgreements() {
        return new ArrayList<>(associatedLeaseAgreements);
    }

    public Name getName() {
        return name;
    }

    public List<Transaction> getOutstandingBalanceHistory() {
        return new ArrayList<>(outstandingBalanceHistory);
    }

    public int getBalance() {
        return balance;
    }
}
