package de.dhbw.domain.aggregateRoots;

import de.dhbw.domain.entities.RentalAgreement;
import de.dhbw.domain.utilities.Rentable;
import de.dhbw.domain.valueObjects.Address;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.ids.RentableId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Property implements Rentable {
    // Implementation specific variables
    private final Address address;
    private final LocalDate dateOfConstruction;

    // Required variables
    private final RentableId id;
    private int maxTenants; // TODO make into a value object or annotate verifications
    private RentalAgreement rentalAgreement;
    private double size; // TODO make into a value object or annotate verifications

    public Property(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction, double size, int maxTenants) {
        // Validate date of construction (implicitly checked for null)
        if (dateOfConstruction.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date of construction may not be in the future");

        this.address = new Address(streetName, houseNumber, postalCode, city);
        this.dateOfConstruction = dateOfConstruction;
        this.id = new RentableId();
        setSize(size);
        setMaxTenants(maxTenants);
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getDateOfConstruction() {
        return dateOfConstruction;
    }

    public void setSize(double size) {
        if (size <= 0)
            throw new IllegalArgumentException("Invalid size");

        this.size = size;
    }

    @Override
    public void remodel(double size, int maxTenants) {
        setSize(size);
        setMaxTenants(maxTenants);
    }

    @Override
    public void setMaxTenants(int maxTenants) {
        if (maxTenants <= 0)
            throw new IllegalArgumentException("Invalid max tenants");

        this.maxTenants = maxTenants;
    }

    @Override
    public RentalAgreement getRentalAgreement() {
        return rentalAgreement;
    }

    @Override
    public void rentToTenant(List<Tenant> tenants, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment) {
        // Validate that the number of tenants does not exceed the maximum number of tenants
        if (tenants.size() > maxTenants)
            throw new IllegalArgumentException("Too many tenants");

        rentalAgreement = new RentalAgreement(tenants, inclusiveStartDate, rent, monthlyDayOfPayment);
    }

    @Override
    public double getSize() {
        return size;
    }

    @Override
    public RentableId getId() {
        return id;
    }

    @Override
    public List<TenantId> getTenantIds() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public RentalAgreement GetRentalAgreement() {
        return rentalAgreement;
    }
}