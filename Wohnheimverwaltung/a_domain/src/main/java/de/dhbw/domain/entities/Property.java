package de.dhbw.domain.entities;

import de.dhbw.domain.valueObjects.Address;
import de.dhbw.domain.valueObjects.Rent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Property {
    private final Address address;
    private final LocalDate dateOfConstruction;
    private final List<Apartment> apartments = new ArrayList<>();

    public Property(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction) {
        // Validate date of construction (implicitly checked for null)
        if (dateOfConstruction.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date of construction may not be in the future");

        this.address = new Address(streetName, houseNumber, postalCode, city);
        this.dateOfConstruction = dateOfConstruction;
    }

    public void addApartment(int apartmentNumber, int floor, double size, Rent rent) {
        apartments.add(new Apartment(apartmentNumber, floor, size, rent, this));
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getDateOfConstruction() {
        return dateOfConstruction;
    }
}
