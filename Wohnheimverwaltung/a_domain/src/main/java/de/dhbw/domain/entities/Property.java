package de.dhbw.domain.entities;

import de.dhbw.domain.utilities.Rentable;
import de.dhbw.domain.valueObjects.Address;
import de.dhbw.domain.valueObjects.Rent;

import java.time.LocalDate;
import java.util.List;

public class Property implements Rentable {
    private final Address address;
    private final LocalDate dateOfConstruction;
    private List<Tenant> tenants;
    private double size;
    private int maxTenants;
    private Rent rent;


    public Property(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction, double size, int maxTenants, Rent rent) {
        // Validate date of construction (implicitly checked for null)
        if (dateOfConstruction.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date of construction may not be in the future");

        this.address = new Address(streetName, houseNumber, postalCode, city);
        this.dateOfConstruction = dateOfConstruction;
        setSize(size);
        setMaxTenants(maxTenants);
        setRent(rent);
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getDateOfConstruction() {
        return dateOfConstruction;
    }

    @Override
    public void rentTo(Tenant tenant) {
        // TODO start work on the renters agreement
        if (tenants.contains(tenant))
            throw new IllegalArgumentException("Renter already rents this apartment");

        tenants.add(tenant);
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
    public void setSize(double size) {
        if (size <= 0)
            throw new IllegalArgumentException("Invalid size");

        this.size = size;
    }

    @Override
    public Rent getRent() {
        return rent;
    }

    @Override
    public void setRent(Rent rent) {
        this.rent = rent;
    }

    @Override
    public double getSize() {
        return size;
    }

    @Override
    public List<Tenant> getTenants() {
        return null;
    }
}
