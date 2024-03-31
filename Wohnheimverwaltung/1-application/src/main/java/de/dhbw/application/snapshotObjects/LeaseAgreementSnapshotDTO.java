package de.dhbw.application.snapshotObjects;

import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.time.LocalDate;
import java.util.List;

public class LeaseAgreementSnapshotDTO {
    private final LocalDate inclusiveStartDate;
    private LocalDate inclusiveEndDate;
    private final int monthlyDayOfPayment;
    private LocalDate nextPaymentDate;
    private final Rent rent;
    private final List<TenantSnapshotDTO> tenants;
    private final LeaseAgreementId id;
    private final RentalId associatedRentalId;

    public LeaseAgreementSnapshotDTO(LeaseAgreement leaseAgreement) {
        this.inclusiveStartDate = leaseAgreement.getInclusiveStartDate();
        this.inclusiveEndDate = leaseAgreement.getInclusiveEndDate();
        this.monthlyDayOfPayment = leaseAgreement.getMonthlyDayOfPayment();
        this.nextPaymentDate = leaseAgreement.getNextPaymentDate();
        this.rent = leaseAgreement.getRent();
        this.tenants = leaseAgreement.getTenants().stream().map(TenantSnapshotDTO::new).toList();
        this.id = leaseAgreement.getId();
        this.associatedRentalId = leaseAgreement.getAssociatedRentalId();
    }

    public LocalDate getInclusiveStartDate() {
        return inclusiveStartDate;
    }

    public LocalDate getInclusiveEndDate() {
        return inclusiveEndDate;
    }

    public int getMonthlyDayOfPayment() {
        return monthlyDayOfPayment;
    }

    public LocalDate getNextPaymentDate() {
        return nextPaymentDate;
    }

    public Rent getRent() {
        return rent;
    }

    public List<TenantSnapshotDTO> getTenants() {
        return tenants;
    }

    public LeaseAgreementId getId() {
        return id;
    }

    public RentalId getAssociatedRentalId() {
        return associatedRentalId;
    }
}
