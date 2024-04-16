package de.dhbw.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;

public class DoorNumber {
    private final int floor;
    private final int apartmentNumber;

    @JsonCreator
    public DoorNumber(
            @JsonProperty("floor") int floor,
            @JsonProperty("apartmentNumber") int apartmentNumber
    ) {
        if (apartmentNumber <= 0)
            throw new IllegalArgumentException("Invalid apartment number");

        this.apartmentNumber = apartmentNumber;
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    @Override
    public String toString() {
        return String.format("%d-%d", floor, apartmentNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoorNumber that = (DoorNumber) o;
        return floor == that.floor && apartmentNumber == that.apartmentNumber;
    }

    @Override
    public int hashCode() {
        int result = floor;
        result = 31 * result + apartmentNumber;
        return result;
    }
}
