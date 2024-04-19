package de.dhbw.domain.valueObjects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RentTest {

    @Test
    public void testValidRentCreation() {
        int validAmount = 1000;
        Rent rent = new Rent(validAmount);
        assertEquals(validAmount, rent.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroRentAmount() {
        int zeroAmount = 0;
        new Rent(zeroAmount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeRentAmount() {
        int negativeAmount = -1000;
        new Rent(negativeAmount);
    }

    @Test
    public void testRentEquality() {
        Rent rent1 = new Rent(1500);
        Rent rent2 = new Rent(1500);
        Rent rent3 = new Rent(2000);

        assertEquals(rent1, rent2); // Same rent amount
        assertNotEquals(rent1, rent3); // Different rent amounts
    }

    @Test
    public void testRentToString() {
        // Test rent amount 1500 with locale default (e.g., en_US)
        Rent rent = new Rent(1500);

        assertEquals("1.500,00 €", rent.toString());
    }
}