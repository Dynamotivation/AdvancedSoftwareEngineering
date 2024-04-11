package de.dhbw.domain.valueObjects;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

public class Size {
    private final BigDecimal value;
    private final SizeUnit unit;

    private Size(final BigDecimal value, final SizeUnit sizeUnit) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Size must be positive");
        }

        this.value = value;
        this.unit = sizeUnit;
    }

    public static Size squareMeters(BigDecimal value) {
        return new Size(value, SizeUnit.SQUARE_METERS);
    }

    public static Size squareFeet(BigDecimal value) {
        return new Size(value, SizeUnit.SQUARE_FEET);
    }

    public BigDecimal getValueInPreferredUnit(final SizeUnit newUnit) {
        if (unit == newUnit) {
            return value;
        }

        return switch (newUnit) {
            case SQUARE_METERS -> convertSquareFeetToSquareMeters(value);
            case SQUARE_FEET -> convertSquareMetersToSquareFeet(value);
            default -> throw new IllegalArgumentException("Unknown unit");
        };
    }

    private static BigDecimal convertSquareFeetToSquareMeters(BigDecimal value) {
        return BigDecimal.valueOf(0.09290304).multiply(value);
    }

    private static BigDecimal convertSquareMetersToSquareFeet(BigDecimal value) {
        return BigDecimal.valueOf(10.76391041671).multiply(value);
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