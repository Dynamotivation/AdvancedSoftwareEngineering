package de.dhbw.domain.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.miscellaneous.NthDayOfMonthAdjuster;
import de.dhbw.domain.miscellaneous.RentCharger;
import de.dhbw.domain.services.DefaultRentCharger;
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
    private final NthDayOfMonthAdjuster monthlyDayOfPayment;
    private final int monthsOfNotice;
    private final Rent rent;
    private final List<Tenant> tenants; // Subscribers
    private final LeaseAgreementId id;
    private final RentalId associatedRentalId;
    private final RentCharger rentCharger;
    private LocalDate inclusiveEndDate;
    private LocalDate nextPaymentDate;

    public LeaseAgreement(List<Tenant> tenants, LocalDate inclusiveStartDate,
                          Rent rent, int monthlyDayOfPayment, int monthsOfNotice, RentalId associatedRentalId) {
        this(
                inclusiveStartDate,
                null,
                new NthDayOfMonthAdjuster(monthlyDayOfPayment),
                monthsOfNotice,
                inclusiveStartDate,
                rent,
                tenants,
                new LeaseAgreementId(),
                associatedRentalId,
                new DefaultRentCharger()
        );

        // Notifies the tenants of the new lease agreement
        tenants.forEach(tenant -> tenant.registerLeaseAgreementSubscription(this.id));

        // Notify subscribers of events since saved state
        update();
    }

    @JsonCreator
    private LeaseAgreement(
            @JsonProperty("inclusiveStartDate") LocalDate inclusiveStartDate,
            @JsonProperty("inclusiveEndDate") LocalDate inclusiveEndDate,
            @JsonProperty("monthlyDayOfPayment") NthDayOfMonthAdjuster monthlyDayOfPayment,
            @JsonProperty("monthsOfNotice") int monthsOfNotice,
            @JsonProperty("nextPaymentDate") LocalDate nextPaymentDate,
            @JsonProperty("rent") Rent rent,
            @JsonProperty("tenants") List<Tenant> tenants,
            @JsonProperty("id") LeaseAgreementId id,
            @JsonProperty("associatedRentalId") RentalId associatedRentalId,
            @JsonProperty("rentCharger") RentCharger rentCharger
    ) {
        this.inclusiveStartDate = inclusiveStartDate;
        this.monthlyDayOfPayment = monthlyDayOfPayment;
        this.monthsOfNotice = monthsOfNotice;
        this.nextPaymentDate = nextPaymentDate;
        this.inclusiveEndDate = inclusiveEndDate;
        this.tenants = new ArrayList<>(tenants); // Register subscribers defensively
        this.id = id;
        this.rent = rent;
        this.associatedRentalId = associatedRentalId;
        this.rentCharger = rentCharger;
    }

    public LocalDate getInclusiveStartDate() {
        return inclusiveStartDate;
    }

    public LocalDate getInclusiveEndDate() {
        return inclusiveEndDate;
    }

    public void setInclusiveEndDate(LocalDate submissionDate, LocalDate inclusiveEndDate) {
        if (submissionDate.isBefore(inclusiveStartDate))
            throw new IllegalArgumentException("End date must be after start date");

        if (submissionDate.plusMonths(monthsOfNotice).isAfter(inclusiveEndDate))
            throw new IllegalArgumentException("First possible end date is " + submissionDate.plusMonths(monthsOfNotice));

        this.inclusiveEndDate = inclusiveEndDate;
    }

    public List<Tenant> getTenants() {
        return new ArrayList<>(tenants);
    }

    public NthDayOfMonthAdjuster getMonthlyDayOfPayment() {
        return monthlyDayOfPayment;
    }

    public int getMonthsOfNotice() {
        return monthsOfNotice;
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

    // Notify subscribers of new due payment
    public void chargeRent() {
        nextPaymentDate = rentCharger.chargeRent(this);
    }

    public void update() {
        chargeRent();

        if (inclusiveEndDate != null && inclusiveEndDate.isBefore(LocalDate.now())) {
            tenants.forEach(tenant -> tenant.deregisterLeaseAgreementSubscription(id, inclusiveEndDate));
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