package de.dhbw.domain.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.valueObjects.Address;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    // Factory for deserialization
    @JsonCreator
    private static ApartmentComplex createApartmentComplex(
            @JsonProperty("address") Address address,
            @JsonProperty("rentalApartmentUnits") List<RentalApartmentUnit> rentalApartmentUnits,
            @JsonProperty("dateOfConstruction") LocalDate dateOfConstruction) {
        ApartmentComplex apartmentComplex =  new ApartmentComplex(address.getStreetName(), address.getHouseNumber(), address.getPostalCode(), address.getCity(), dateOfConstruction);

        for (RentalApartmentUnit rentalApartmentUnit : rentalApartmentUnits) {
            apartmentComplex.addApartment(rentalApartmentUnit);
        }

        return apartmentComplex;
    }

    public void addApartment(RentalApartmentUnit rentalApartmentUnit) {
        rentalApartmentUnits.add(rentalApartmentUnit);
    }

    public List<RentalApartmentUnit> getRentalApartmentUnits() {
        return rentalApartmentUnits;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getDateOfConstruction() {
        return dateOfConstruction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApartmentComplex that = (ApartmentComplex) o;
        return Objects.equals(address, that.address) && Objects.equals(dateOfConstruction, that.dateOfConstruction) && Objects.equals(rentalApartmentUnits, that.rentalApartmentUnits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, dateOfConstruction, rentalApartmentUnits);
    }
}
