package de.dhbw.domain.entities;

import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.valueObjects.Rent;

import java.time.LocalDate;
import java.util.List;

public class RentalAgreement {
    private final LocalDate inclusiveStartDate;
    private final int monthlyDayOfPayment;
    private LocalDate inclusiveEndDate;
    private final List<Tenant> tenants;

    public RentalAgreement(List<Tenant> tenants, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment) {
        // Validate monthly day of payment
        if (monthlyDayOfPayment < 1 || monthlyDayOfPayment > 31)
            throw new IllegalArgumentException("Monthly day of payment must be between 1 and 31. Shorter months are accounted for automatically.");


        this.inclusiveStartDate = inclusiveStartDate;
        this.monthlyDayOfPayment = monthlyDayOfPayment;
        this.tenants = tenants;
    }

    public LocalDate getInclusiveStartDate() {
        return inclusiveStartDate;
    }

    public void setInclusiveEndDate(LocalDate inclusiveEndDate) {
        // TODO Minimum rental period

        // Validate that the end date is after the start date
        if (inclusiveEndDate.isBefore(inclusiveStartDate))
            throw new IllegalArgumentException("End date must be after start date");

        this.inclusiveEndDate = inclusiveEndDate;
    }

    public LocalDate getInclusiveEndDate() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public int getMonthlyDayOfPayment() {
        return monthlyDayOfPayment;
    }
}
