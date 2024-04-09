package de.dhbw.domain.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.valueObjects.Address;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApartmentComplex {
    private final Address address;
    private final LocalDate dateOfConstruction;
    private final List<RentalApartmentUnit> rentalApartmentUnits = new ArrayList<>();
    private final ApartmentComplexId id;

    public ApartmentComplex(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction) {
        // Validate date of construction (implicitly checked for null)
        if (dateOfConstruction.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date of construction may not be in the future");

        this.address = new Address(streetName, houseNumber, postalCode, city);
        this.dateOfConstruction = dateOfConstruction;
        this.id = new ApartmentComplexId();
    }

    // Powerful constructor for deserialization
    @JsonCreator
    private ApartmentComplex(
            @JsonProperty("address") Address address,
            @JsonProperty("rentalApartmentUnits") List<RentalApartmentUnit> rentalApartmentUnits,
            @JsonProperty("dateOfConstruction") LocalDate dateOfConstruction) {
        this(address.getStreetName(), address.getHouseNumber(), address.getPostalCode(), address.getCity(), dateOfConstruction);

        for (RentalApartmentUnit rentalApartmentUnit : rentalApartmentUnits) {
            addApartment(rentalApartmentUnit);
        }
    }

    public ApartmentComplexId getId() {
        return id;
    }

    public void addApartment(RentalApartmentUnit rentalApartmentUnit) {
        rentalApartmentUnits.add(rentalApartmentUnit);
    }

    public List<RentalApartmentUnit> getRentalApartmentUnits() {
        return new ArrayList<>(rentalApartmentUnits);
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
