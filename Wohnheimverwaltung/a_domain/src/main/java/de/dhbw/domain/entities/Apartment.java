package de.dhbw.domain.entities;

import de.dhbw.domain.valueObjects.Rent;

import java.util.ArrayList;
import java.util.List;

public class Apartment {
    // In case of remodelling the apartmentNumber and size should be mutable. Rent and Tenants can change.
    private int apartmentNumber;
    private double size;
    private Rent rent;
    private final int floor;
    private List<Renter> renters = new ArrayList<>();
    private Property parentProperty;

    // An Apartment should always be part of a property, thus external classes may not create them uncontrollably.
    // Creation will be possible through the Property class.
    protected Apartment(int apartmentNumber, int floor, double size, Rent rent, Property parentProperty) {
        setApartmentNumber(apartmentNumber);
        this.floor = floor;
        setSize(size);
        setRent(rent);
        this.parentProperty = parentProperty;
    }

    public void rentApartmentTo(Renter renter) {
        if (renters.contains(renter))
            throw new IllegalArgumentException("Renter already rents this apartment");

        renters.add(renter);
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        if (apartmentNumber <= 0)
            throw new IllegalArgumentException("Invalid apartment number");

        // Check if an apartment with the same number already exists
        for (Apartment apartment : this.parentProperty.getApartments()) {
            if (apartment.getApartmentNumber() == apartmentNumber)
                throw new IllegalArgumentException("Apartment with this number already exists");
        }

        this.apartmentNumber = apartmentNumber;
    }

    public int getFloor() {
        return floor;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        if (size <= 0)
            throw new IllegalArgumentException("Invalid size");
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public List<Renter> getRenters() {
        return renters;
    }

    public Property getParentProperty() {
        return parentProperty;
    }
}
