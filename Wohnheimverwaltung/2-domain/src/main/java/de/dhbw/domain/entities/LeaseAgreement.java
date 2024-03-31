package de.dhbw.domain.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.miscellaneous.NthDayOfMonthAdjuster;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@json_id")
public class LeaseAgreement {
    private final LocalDate inclusiveStartDate;
    private LocalDate inclusiveEndDate;
    private final int monthlyDayOfPayment;
    private LocalDate nextPaymentDate;
    private final Rent rent;
    private final List<Tenant> tenants;
    private final LeaseAgreementId id;
    private final RentalId associatedRentalId;

    public LeaseAgreement(List<Tenant> tenants, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment, RentalId associatedRentalId) {
        // Validate monthly day of payment
        if (monthlyDayOfPayment < 1 || monthlyDayOfPayment > 31)
            throw new IllegalArgumentException("Monthly day of payment must be between 1 and 31. Shorter months are accounted for automatically.");


        this.inclusiveStartDate = inclusiveStartDate;
        this.nextPaymentDate = inclusiveStartDate;
        this.monthlyDayOfPayment = monthlyDayOfPayment;
        this.tenants = tenants;
        this.id = new LeaseAgreementId();
        this.rent = rent;
        this.associatedRentalId = associatedRentalId;

        // Notifies the tenants of the new lease agreement
        tenants.forEach(tenant -> tenant.addLeaseAgreement(this));
    }

    public LocalDate getInclusiveStartDate() {
        return inclusiveStartDate;
    }

    public LocalDate getInclusiveEndDate() {
        return inclusiveEndDate;
    }

    public void setInclusiveEndDate(LocalDate inclusiveEndDate) {
        // TODO Minimum rental period

        // Validate that the end date is after the start date
        if (inclusiveEndDate.isBefore(inclusiveStartDate))
            throw new IllegalArgumentException("End date must be after start date");

        this.inclusiveEndDate = inclusiveEndDate;
    }

    public List<Tenant> getTenants() {
        return new ArrayList<>(tenants);
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

    public LeaseAgreementId getId() {
        return id;
    }

    public RentalId getAssociatedRentalId() {
        return associatedRentalId;
    }

    public void chargeRent() {
        NthDayOfMonthAdjuster nthDayOfMonthAdjuster = new NthDayOfMonthAdjuster(monthlyDayOfPayment);

        while (nextPaymentDate.isBefore(LocalDate.now())) {
            tenants.forEach(tenant -> tenant.addTransaction(new RentCharge(-rent.getAmount(), nextPaymentDate, id)));
            nextPaymentDate = nextPaymentDate.plusMonths(1).with(nthDayOfMonthAdjuster);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaseAgreement that = (LeaseAgreement) o;
        return monthlyDayOfPayment == that.monthlyDayOfPayment && Objects.equals(inclusiveStartDate, that.inclusiveStartDate) && Objects.equals(inclusiveEndDate, that.inclusiveEndDate) && Objects.equals(tenants, that.tenants) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inclusiveStartDate, monthlyDayOfPayment, inclusiveEndDate, tenants, id);
    }
}
