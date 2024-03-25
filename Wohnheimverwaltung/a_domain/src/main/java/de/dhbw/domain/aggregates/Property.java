package de.dhbw.domain.aggregates;

import de.dhbw.domain.entities.RentalAgreement;
import de.dhbw.domain.entities.Tenant;
import de.dhbw.domain.utilities.Rentable;
import de.dhbw.domain.valueObjects.Address;
import de.dhbw.domain.valueObjects.Rent;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Property implements Rentable {
    private final Address address;
    private final LocalDate dateOfConstruction;
    private List<Tenant> tenants;
    private double size;
    private int maxTenants;
    private RentalAgreement rentalAgreement;
    private final UUID id = UUID.randomUUID();

    public Property(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction, double size, int maxTenants, Rent rent) {
        // Validate date of construction (implicitly checked for null)
        if (dateOfConstruction.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date of construction may not be in the future");

        this.address = new Address(streetName, houseNumber, postalCode, city);
        this.dateOfConstruction = dateOfConstruction;
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
    public UUID getId() {
        return id;
    }

    @Override
    public List<Tenant> getTenants() {
        return null;
    }

    @Override
    public RentalAgreement GetRentalAgreement() {
        return rentalAgreement;
    }
}
