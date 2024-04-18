package de.dhbw.domain.valueObjects;

import static org.junit.Assert.*;
import org.junit.Test;

import java.math.BigDecimal;

public class SizeTest {

    @Test
    public void testValidSizeCreation() {
        BigDecimal validValue = new BigDecimal("100");
        Size size = new Size(validValue, SizeUnit.SQUARE_METERS);
        assertEquals(validValue, size.getValue());
        assertEquals(SizeUnit.SQUARE_METERS, size.getUnit());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeSizeValue() {
        BigDecimal negativeValue = new BigDecimal("-100");
        new Size(negativeValue, SizeUnit.SQUARE_METERS);
    }

    @Test
    public void testSizeEquality() {
        Size size1 = new Size(new BigDecimal("200"), SizeUnit.SQUARE_METERS);
        Size size2 = new Size(new BigDecimal("200"), SizeUnit.SQUARE_METERS);
        Size size3 = new Size(new BigDecimal("300"), SizeUnit.SQUARE_FEET);

        assertEquals(size1, size2); // Same value and unit
        assertNotEquals(size1, size3); // Different value or unit
    }

    @Test
    public void testGetSizeValueInPreferredUnit() {
        BigDecimal squareMetersValue = new BigDecimal("50");
        Size size = new Size(squareMetersValue, SizeUnit.SQUARE_METERS);

        BigDecimal expectedSquareFeetValue = new BigDecimal("538.196");
        BigDecimal actualSquareFeetValue = size.getValueInPreferredUnit(SizeUnit.SQUARE_FEET);

        assertEquals(expectedSquareFeetValue.setScale(3, BigDecimal.ROUND_HALF_UP), actualSquareFeetValue.setScale(3, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void testSquareMetersToSquareFeetConversion() {
        BigDecimal squareMetersValue = new BigDecimal("100");
        BigDecimal expectedSquareFeetValue = new BigDecimal("1076.391");

        Size size = new Size(squareMetersValue, SizeUnit.SQUARE_METERS);
        BigDecimal actualSquareFeetValue = size.getValueInPreferredUnit(SizeUnit.SQUARE_FEET);

        assertEquals(expectedSquareFeetValue.setScale(3, BigDecimal.ROUND_HALF_UP), actualSquareFeetValue.setScale(3, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void testSquareFeetToSquareMetersConversion() {
        BigDecimal squareFeetValue = new BigDecimal("1000");
        BigDecimal expectedSquareMetersValue = new BigDecimal("92.903");

        Size size = new Size(squareFeetValue, SizeUnit.SQUARE_FEET);
        BigDecimal actualSquareMetersValue = size.getValueInPreferredUnit(SizeUnit.SQUARE_METERS);

        assertEquals(expectedSquareMetersValue.setScale(3, BigDecimal.ROUND_HALF_UP), actualSquareMetersValue.setScale(3, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void testSizeHashCode() {
        Size size1 = new Size(new BigDecimal("150"), SizeUnit.SQUARE_METERS);
        Size size2 = new Size(new BigDecimal("150"), SizeUnit.SQUARE_METERS);

        assertEquals(size1.hashCode(), size2.hashCode());
    }
}