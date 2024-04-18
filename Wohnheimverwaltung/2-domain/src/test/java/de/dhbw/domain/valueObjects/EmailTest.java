package de.dhbw.domain.valueObjects;

import static org.junit.Assert.*;
import org.junit.Test;

public class EmailTest {

    @Test
    public void testValidEmailCreation() {
        String validEmail = "user@example.com";
        Email email = new Email(validEmail);
        assertEquals(validEmail, email.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmailFormat() {
        String invalidEmail = "invalid.email.com"; // Missing '@' symbol
        new Email(invalidEmail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullEmail() {
        String nullEmail = null;
        new Email(nullEmail);
    }

    @Test
    public void testEmailEquality() {
        Email email1 = new Email("user1@example.com");
        Email email2 = new Email("user1@example.com");
        Email email3 = new Email("user2@example.com");

        assertEquals(email1, email2); // Same email addresses
        assertNotEquals(email1, email3); // Different email addresses
    }

    @Test
    public void testEmailHashCode() {
        Email email1 = new Email("user@example.com");
        Email email2 = new Email("user@example.com");

        assertEquals(email1.hashCode(), email2.hashCode());
    }

    @Test
    public void testEmailToString() {
        String emailAddress = "user@example.com";
        Email email = new Email(emailAddress);

        assertEquals(emailAddress, email.toString());
    }

    @Test
    public void testIllegalEmailCombinations() {
        // Test some illegal email combinations
        String[] illegalEmails = {
                "invalid.email", // Missing domain
                "@example.com", // Missing local part
                "user@example", // Missing top-level domain
                "user@.com", // Invalid domain
                "user@.com." // Invalid domain with trailing dot
        };

        for (String illegalEmail : illegalEmails) {
            try {
                new Email(illegalEmail);
                fail("Expected IllegalArgumentException for: " + illegalEmail);
            } catch (IllegalArgumentException e) {
                // Expected exception for invalid email
            }
        }
    }
}