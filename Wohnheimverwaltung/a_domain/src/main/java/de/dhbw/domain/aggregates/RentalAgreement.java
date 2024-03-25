package de.dhbw.domain.aggregates;

import de.dhbw.domain.entities.Tenant;
import de.dhbw.domain.utilities.Rentable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class RentalAgreement {
    private final LocalDate inclusiveStartDate;
    private final int monthlyDayOfPayment;
    private LocalDate inclusiveEndDate;
    private final Rentable rentable;
    private final List<Tenant> tenants;
    private final UUID id = UUID.randomUUID();

    public RentalAgreement(Rentable rentable, List<Tenant> tenants, LocalDate inclusiveStartDate, int monthlyDayOfPayment) {
        // Validate monthly day of payment
        if (monthlyDayOfPayment < 1 || monthlyDayOfPayment > 31)
            throw new IllegalArgumentException("Monthly day of payment must be between 1 and 31. Shorter months are accounted for automatically.");



        this.inclusiveStartDate = inclusiveStartDate;
        this.monthlyDayOfPayment = monthlyDayOfPayment;
        this.rentable = rentable;
        this.tenants = tenants;
    }

    public LocalDate getInclusiveStartDate() {
        return inclusiveStartDate;
    }

    public void setInclusiveEndDate(LocalDate inclusiveEndDate) {
        // TODO

        // Validate that the end date is after the start date
        if (inclusiveEndDate.isBefore(inclusiveStartDate))
            throw new IllegalArgumentException("End date must be after start date");

        this.inclusiveEndDate = inclusiveEndDate;
    }

    public LocalDate getInclusiveEndDate() {
        return inclusiveEndDate;
    }

    public Rentable getRentable() {
        return rentable;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public UUID getId() {
        return id;
    }

    public void rentRentableToTenant(Rentable rentable, Tenant tenant) {
        // TODO implement
    }
}
