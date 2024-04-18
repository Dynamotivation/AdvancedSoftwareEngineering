package de.dhbw.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class Size {
    private final BigDecimal value;
    private final SizeUnit unit;

    @JsonCreator
    public Size(
            @JsonProperty("value") BigDecimal value,
            @JsonProperty("unit") SizeUnit unit
    ) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Size must be positive");
        }

        this.value = value;
        this.unit = unit;
    }

    public static Size squareMeters(BigDecimal bigDecimal) {
        return new Size(bigDecimal, SizeUnit.SQUARE_METERS);
    }

    public static Size squareFeet(BigDecimal bigDecimal) {
        return new Size(bigDecimal, SizeUnit.SQUARE_FEET);
    }

    private static BigDecimal convertSquareFeetToSquareMeters(BigDecimal value) {
        return BigDecimal.valueOf(0.09290304).multiply(value);
    }

    private static BigDecimal convertSquareMetersToSquareFeet(BigDecimal value) {
        return BigDecimal.valueOf(10.76391041671).multiply(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    public SizeUnit getUnit() {
        return unit;
    }

    public BigDecimal getValueInPreferredUnit(SizeUnit newUnit) {
        if (unit == newUnit) {
            return value;
        }

        return switch (newUnit) {
            case SQUARE_METERS -> convertSquareFeetToSquareMeters(value);
            case SQUARE_FEET -> convertSquareMetersToSquareFeet(value);
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Size size = (Size) o;
        return Objects.equals(value, size.value) && unit == size.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }
}