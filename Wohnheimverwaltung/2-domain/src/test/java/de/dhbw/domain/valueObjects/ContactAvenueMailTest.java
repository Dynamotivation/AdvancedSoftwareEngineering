package de.dhbw.domain.valueObjects;

import static org.junit.Assert.*;
import org.junit.Test;

public class ContactAvenueMailTest {

    @Test
    public void testContactAvenueMailCreation() {
        Address address1 = new Address("Main St", "123", "12345", "Springfield");
        Address address2 = new Address("Broad St", "456", "54321", "Springfield");

        ContactAvenueMail mail1 = new ContactAvenueMail(address1);
        ContactAvenueMail mail2 = new ContactAvenueMail(address2);

        assertEquals("Main St 123, 12345 Springfield", mail1.getContactDetails());
        assertEquals("Broad St 456, 54321 Springfield", mail2.getContactDetails());
    }

    @Test
    public void testContactAvenueMailEquality() {
        Address address1 = new Address("Main St", "123", "12345", "Springfield");
        Address address2 = new Address("Main St", "123", "12345", "Springfield");

        ContactAvenueMail mail1 = new ContactAvenueMail(address1);
        ContactAvenueMail mail2 = new ContactAvenueMail(address2);

        // Test equals method
        assertEquals(mail1, mail2);

        // Test hashCode method
        assertEquals(mail1.hashCode(), mail2.hashCode());
    }

    @Test
    public void testContactAvenueToString() {
        Address address = new Address("Main St", "123", "12345", "Springfield");
        ContactAvenueMail mail = new ContactAvenueMail(address);

        assertEquals("Main St 123, 12345 Springfield", mail.toString());
    }

    @Test
    public void testContactAvenueEquality() {
        ContactAvenueMail mail1 = new ContactAvenueMail(new Address("Main St", "123", "12345", "Springfield"));
        ContactAvenueMail mail2 = new ContactAvenueMail(new Address("Broad St", "456", "54321", "Springfield"));

        assertFalse(mail1.equals(mail2)); // Different addresses should not be equal
    }
}