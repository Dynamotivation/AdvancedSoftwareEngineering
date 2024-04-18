package de.dhbw.domain.valueObjects;

import static org.junit.Assert.*;
import org.junit.Test;

public class DoorNumberTest {

    @Test
    public void testValidDoorNumberCreation() {
        DoorNumber doorNumber = new DoorNumber(1, 101);
        assertEquals(1, doorNumber.getFloor());
        assertEquals(101, doorNumber.getApartmentNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidApartmentNumber() {
        new DoorNumber(2, 0); // Apartment number should be positive
    }

    @Test
    public void testDoorNumberEquality() {
        DoorNumber doorNumber1 = new DoorNumber(3, 201);
        DoorNumber doorNumber2 = new DoorNumber(3, 201);
        DoorNumber doorNumber3 = new DoorNumber(3, 202);

        assertEquals(doorNumber1, doorNumber2); // Same floor and apartment number
        assertNotEquals(doorNumber1, doorNumber3); // Different apartment number
    }

    @Test
    public void testDoorNumberHashCode() {
        DoorNumber doorNumber1 = new DoorNumber(4, 301);
        DoorNumber doorNumber2 = new DoorNumber(4, 301);

        assertEquals(doorNumber1.hashCode(), doorNumber2.hashCode());
    }

    @Test
    public void testDoorNumberToString() {
        DoorNumber doorNumber = new DoorNumber(5, 401);
        assertEquals("5-401", doorNumber.toString());
    }
}
