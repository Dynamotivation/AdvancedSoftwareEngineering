package de.dhbw.domain.entities;

import de.dhbw.domain.utilities.Rentable;
import de.dhbw.domain.valueObjects.Rent;

import java.util.ArrayList;
import java.util.List;

public class ApartmentUnit implements Rentable {
    // In case of remodelling the apartmentNumber and size should be mutable. Rent can change.
    private int apartmentNumber;
    private double size;
    private Rent rent;
    private final int floor;
    private final List<Tenant> tenants = new ArrayList<>();
    private final ApartmentComplex parentApartmentComplex;
    private int maxTenants;

    // An Apartment should always be part of a property, thus external classes may not create them uncontrollably.
    // Creation will be possible through the Property class.
    protected ApartmentUnit(ApartmentComplex parentApartmentComplex, int apartmentNumber, int floor, double size, int maxTenants, Rent rent) {
        setApartmentNumber(apartmentNumber);
        this.floor = floor;
        setSize(size);
        setMaxTenants(maxTenants);
        setRent(rent);
        this.parentApartmentComplex = parentApartmentComplex;
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

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        if (apartmentNumber <= 0)
            throw new IllegalArgumentException("Invalid apartment number");

        // Check if an apartment with the same number already exists
        for (ApartmentUnit apartmentUnit : this.parentApartmentComplex.getApartments()) {
            if (apartmentUnit.getApartmentNumber() == apartmentNumber)
                throw new IllegalArgumentException("Apartment with this number already exists");
        }

        this.apartmentNumber = apartmentNumber;
    }

    public int getFloor() {
        return floor;
    }

    @Override
    public double getSize() {
        return size;
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
    public List<Tenant> getTenants() {
        return tenants;
    }

    public ApartmentComplex getParentComplex() {
        return parentApartmentComplex;
    }
}
