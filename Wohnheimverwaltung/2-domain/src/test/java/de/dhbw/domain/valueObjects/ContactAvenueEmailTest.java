package de.dhbw.domain.valueObjects;

import static org.junit.Assert.*;
import org.junit.Test;

public class ContactAvenueEmailTest {

    @Test
    public void testValidContactAvenueEmail() {
        String validEmail = "test@example.com";
        ContactAvenueEmail contactAvenueEmail = new ContactAvenueEmail(new Email(validEmail));
        assertEquals(validEmail, contactAvenueEmail.getContactDetails());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidContactAvenueEmail_InvalidFormat() {
        // Invalid email format without '@'
        String invalidEmail = "invalid_email";
        new ContactAvenueEmail(new Email(invalidEmail));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidContactAvenueEmail_Null() {
        // Null email
        new ContactAvenueEmail(new Email(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidContactAvenueEmail_Empty() {
        // Empty email
        String emptyEmail = "";
        new ContactAvenueEmail(new Email(emptyEmail));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidContactAvenueEmail_Whitespace() {
        // Email with whitespace
        String emailWithWhitespace = " test@example.com ";
        new ContactAvenueEmail(new Email(emailWithWhitespace));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidContactAvenueEmail_Long() {
        // Email exceeding maximum length (254 characters)
        String longEmail = "a".repeat(247) + "@example.com";
        new ContactAvenueEmail(new Email(longEmail));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidContactAvenueEmail_DoubleAtSymbol() {
        // Invalid email with double '@' symbols
        String invalidEmail = "test@@example.com";
        new ContactAvenueEmail(new Email(invalidEmail));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidContactAvenueEmail_WrongPunctuation() {
        // Invalid email with double '@' symbols
        String invalidEmail = "test@example.com.";
        new ContactAvenueEmail(new Email(invalidEmail));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidContactAvenueEmail_NoTld() {
        // Invalid email with double '@' symbols
        String invalidEmail = "test@example";
        new ContactAvenueEmail(new Email(invalidEmail));
    }

    @Test
    public void testContactAvenueEquality() {
        String email1 = "test@example.com";
        String email2 = "another@example.com";

        ContactAvenueEmail avenueEmail1 = new ContactAvenueEmail(new Email(email1));
        ContactAvenueEmail avenueEmail2 = new ContactAvenueEmail(new Email(email1));
        ContactAvenueEmail avenueEmail3 = new ContactAvenueEmail(new Email(email2));

        // Test equals method
        assertEquals(avenueEmail1, avenueEmail2);
        assertNotEquals(avenueEmail1, avenueEmail3);

        // Test hashCode method
        assertEquals(avenueEmail1.hashCode(), avenueEmail2.hashCode());
        assertNotEquals(avenueEmail1.hashCode(), avenueEmail3.hashCode());
    }

    @Test
    public void testContactAvenueToString() {
        String email = "test@example.com";
        ContactAvenueEmail contactAvenueEmail = new ContactAvenueEmail(new Email(email));
        assertEquals(email, contactAvenueEmail.toString());
    }
}