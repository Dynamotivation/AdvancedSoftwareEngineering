package de.dhbw.application.snapshotObjects;

import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.miscellaneous.NthDayOfMonthAdjuster;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class LeaseAgreementSnapshotDTO {
    private final LocalDate inclusiveStartDate;
    private final LocalDate inclusiveEndDate;
    private final NthDayOfMonthAdjuster monthlyDayOfPayment;
    private final LocalDate nextPaymentDate;
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

    public NthDayOfMonthAdjuster getMonthlyDayOfPayment() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LeaseAgreementSnapshotDTO that = (LeaseAgreementSnapshotDTO) o;
        return inclusiveStartDate.equals(that.inclusiveStartDate) && Objects.equals(inclusiveEndDate, that.inclusiveEndDate) && monthlyDayOfPayment.equals(that.monthlyDayOfPayment) && nextPaymentDate.equals(that.nextPaymentDate) && rent.equals(that.rent) && tenants.equals(that.tenants) && id.equals(that.id) && associatedRentalId.equals(that.associatedRentalId);
    }

    @Override
    public int hashCode() {
        int result = inclusiveStartDate.hashCode();
        result = 31 * result + Objects.hashCode(inclusiveEndDate);
        result = 31 * result + monthlyDayOfPayment.hashCode();
        result = 31 * result + nextPaymentDate.hashCode();
        result = 31 * result + rent.hashCode();
        result = 31 * result + tenants.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + associatedRentalId.hashCode();
        return result;
    }
}
