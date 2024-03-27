package de.dhbw.domain.entities;

import de.dhbw.domain.aggregateRoots.ApartmentUnit;
import de.dhbw.domain.valueObjects.Address;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApartmentComplex {
    private final Address address;
    private final LocalDate dateOfConstruction;
    private final List<ApartmentUnit> apartmentUnits = new ArrayList<>();

    public ApartmentComplex(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction) {
        // Validate date of construction (implicitly checked for null)
        if (dateOfConstruction.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date of construction may not be in the future");

        this.address = new Address(streetName, houseNumber, postalCode, city);
        this.dateOfConstruction = dateOfConstruction;
    }

    public void addApartment(ApartmentUnit apartmentUnit) {
        apartmentUnits.add(apartmentUnit);
    }

    public List<ApartmentUnit> getApartments() {
        return apartmentUnits;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getDateOfConstruction() {
        return dateOfConstruction;
    }
}
