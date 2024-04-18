package de.dhbw.application.snapshotObjects;

import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.entities.ContactInformation;
import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.miscellaneous.ContactAvenue;
import de.dhbw.domain.valueObjects.Name;
import de.dhbw.domain.miscellaneous.Transaction;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.util.ArrayList;
import java.util.List;

public class TenantSnapshotDTO {
    private final ContactInformationSnapshotDTO contactInformation;
    private final TenantId id;
    private final List<LeaseAgreementId> associatedLeaseAgreementIds;
    private final Name name;
    private final List<Transaction> outstandingBalanceHistory;
    private final int balance;

    public TenantSnapshotDTO(Tenant tenant) {
        this.name = new Name(tenant.getName(), tenant.getSurname());
        this.contactInformation = new ContactInformationSnapshotDTO(tenant.getPreferredContactAvenue(), tenant.getContactAvenues());
        this.id = tenant.getId();
        this.associatedLeaseAgreementIds = tenant.getAssociatedLeaseAgreementIds();
        this.outstandingBalanceHistory = tenant.getOutstandingBalanceHistory();
        this.balance = tenant.getBalance();
    }

    public ContactAvenue getPreferredContactAvenue() {
        return contactInformation.getPreferredContactAvenue();
    }

    public List<ContactAvenue> getContactAvenues() {
        return contactInformation.getContactAvenues();
    }

    public TenantId getId() {
        return id;
    }

    public List<LeaseAgreementId> getAssociatedLeaseAgreements() {
        return new ArrayList<>(associatedLeaseAgreementIds);
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
