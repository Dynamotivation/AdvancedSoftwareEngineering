package de.dhbw.domain.valueObjects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AddressTest {

    @Test
    public void testValidAddressCreation() {
        Address address = new Address("Main St", "123", "12345", "Springfield");
        assertEquals("Main St", address.getStreetName());
        assertEquals("123", address.getHouseNumber());
        assertEquals("12345", address.getPostalCode());
        assertEquals("Springfield", address.getCity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullStreetName() {
        new Address(null, "123", "12345", "Springfield");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullHouseNumber() {
        new Address("Main St", null, "12345", "Springfield");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPostalCode() {
        new Address("Main St", "123", null, "Springfield");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullCity() {
        new Address("Main St", "123", "12345", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidStreetName() {
        new Address("123", "123", "12345", "Springfield");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHouseNumber() {
        new Address("Main St", "0", "12345", "Springfield");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPostalCode() {
        new Address("Main St", "123", "123456", "Springfield");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCity() {
        new Address("Main St", "123", "12345", "123");
    }

    @Test
    public void testAddressEquality() {
        Address address1 = new Address("Main St", "123", "12345", "Springfield");
        Address address2 = new Address("Main St", "123", "12345", "Springfield");
        Address address3 = new Address("Broad St", "456", "54321", "Springfield");

        // Test equals method
        assertEquals(address1, address2);
        assertNotEquals(address1, address3);

        // Test hashCode method
        assertEquals(address1.hashCode(), address2.hashCode());
        assertNotEquals(address1.hashCode(), address3.hashCode());
    }

    @Test
    public void testAddressToString() {
        Address address = new Address("Main St", "123", "12345", "Springfield");
        assertEquals("Main St 123, 12345 Springfield", address.toString());
    }
}