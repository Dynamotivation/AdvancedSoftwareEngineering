package de.dhbw.domain.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.valueObjects.Address;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;
import de.dhbw.domain.valueObjects.ids.RentalId;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@json_id")
public class ApartmentComplex {
    private final Address address;
    private final LocalDate dateOfConstruction;
    private final List<RentalId> rentalApartmentUnits;
    private final ApartmentComplexId id;

    public ApartmentComplex(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction) {
        this(new Address(streetName, houseNumber, postalCode, city), new ArrayList<RentalId>(), dateOfConstruction, new ApartmentComplexId());
    }

    // Powerful constructor for deserialization
    @JsonCreator
    private ApartmentComplex(
            @JsonProperty("address") @NonNull Address address,
            @JsonProperty("rentalApartmentUnits") @NonNull List<RentalId> rentalApartmentUnits,
            @JsonProperty("dateOfConstruction") @NonNull LocalDate dateOfConstruction,
            @JsonProperty("id") @NonNull ApartmentComplexId id
    ) {
        // Validate date of construction
        if (dateOfConstruction.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date of construction may not be in the future");

        this.address = address;
        this.rentalApartmentUnits = rentalApartmentUnits;
        this.dateOfConstruction = dateOfConstruction;
        this.id = id;
    }

    public ApartmentComplexId getId() {
        return id;
    }

    public void addApartment(RentalApartmentUnit rentalApartmentUnit) {
        rentalApartmentUnits.add(rentalApartmentUnit.getId());
    }

    public List<RentalId> getRentalApartmentUnits() {
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
        return address.equals(that.address) && dateOfConstruction.equals(that.dateOfConstruction) && rentalApartmentUnits.equals(that.rentalApartmentUnits) && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = address.hashCode();
        result = 31 * result + dateOfConstruction.hashCode();
        result = 31 * result + rentalApartmentUnits.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
