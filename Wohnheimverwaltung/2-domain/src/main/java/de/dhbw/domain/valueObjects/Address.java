package de.dhbw.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Address {
    // An Address should be a value object since it cannot change. If anything changes it's a different address.
    // Likewise, multiple people can live at the same address. At most the combination of their name + address is unique, never the address itself.
    private final String streetName;
    private final String houseNumber;
    private final String postalCode;
    private final String city;

    @JsonCreator
    public Address(
            @JsonProperty("streetName") @NotNull String streetName,
            @JsonProperty("houseNumber") @NotNull String houseNumber,
            @JsonProperty("postalCode") @NotNull String postalCode,
            @JsonProperty("city") @NotNull String city) {
        // Validate street name
        streetName = streetName.trim();

        if (!streetName.matches("^[a-zA-ZäöüÄÖÜß\\s]+"))
            throw new IllegalArgumentException("Invalid street name");


        // Validate house number
        houseNumber = houseNumber.trim();
        houseNumber = houseNumber.toLowerCase();

        if (!houseNumber.matches("^[1-9][0-9]*[a-z]?"))
            throw new IllegalArgumentException("Invalid house number");


        // Validate postal code
        postalCode = postalCode.trim();

        if (!postalCode.matches("^[0-9]{5}"))
            throw new IllegalArgumentException("Invalid postal code");


        // Validate city
        city = city.trim();

        if (!city.matches("^[a-zA-ZäöüÄÖÜß\\s]+"))
            throw new IllegalArgumentException("Invalid city");


        // Assign values
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return String.format("%s %s, %s %s", streetName, houseNumber, postalCode, city);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(streetName, address.streetName) && Objects.equals(houseNumber, address.houseNumber) && Objects.equals(postalCode, address.postalCode) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, houseNumber, postalCode, city);
    }
}
