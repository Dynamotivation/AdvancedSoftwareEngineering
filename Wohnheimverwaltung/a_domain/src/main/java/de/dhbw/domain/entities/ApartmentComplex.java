package de.dhbw.domain.entities;

import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.valueObjects.Address;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApartmentComplex {
    private final Address address;
    private final LocalDate dateOfConstruction;
    private final List<RentalApartmentUnit> rentalApartmentUnits = new ArrayList<>();

    public ApartmentComplex(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction) {
        // Validate date of construction (implicitly checked for null)
        if (dateOfConstruction.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date of construction may not be in the future");

        this.address = new Address(streetName, houseNumber, postalCode, city);
        this.dateOfConstruction = dateOfConstruction;
    }

    public void addApartment(RentalApartmentUnit rentalApartmentUnit) {
        rentalApartmentUnits.add(rentalApartmentUnit);
    }

    public List<RentalApartmentUnit> getApartments() {
        return rentalApartmentUnits;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getDateOfConstruction() {
        return dateOfConstruction;
    }
}
