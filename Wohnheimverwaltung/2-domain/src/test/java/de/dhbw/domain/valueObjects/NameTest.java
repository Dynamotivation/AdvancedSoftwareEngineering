package de.dhbw.domain.valueObjects;

import static org.junit.Assert.*;
import org.junit.Test;

public class NameTest {

    @Test
    public void testValidNameCreation() {
        String validName = "John";
        String validSurname = "Doe";
        Name name = new Name(validName, validSurname);
        assertEquals(validName, name.getName());
        assertEquals(validSurname, name.getSurname());
        assertEquals("John Doe", name.getFullName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
        String nullName = null;
        String validSurname = "Doe";
        new Name(nullName, validSurname);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyName() {
        String emptyName = "";
        String validSurname = "Doe";
        new Name(emptyName, validSurname);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankName() {
        String blankName = "   ";
        String validSurname = "Doe";
        new Name(blankName, validSurname);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSurname() {
        String validName = "John";
        String nullSurname = null;
        new Name(validName, nullSurname);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptySurname() {
        String validName = "John";
        String emptySurname = "";
        new Name(validName, emptySurname);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankSurname() {
        String validName = "John";
        String blankSurname = "   ";
        new Name(validName, blankSurname);
    }

    @Test
    public void testNameEquality() {
        Name name1 = new Name("John", "Doe");
        Name name2 = new Name("John", "Doe");
        Name name3 = new Name("Jane", "Smith");

        assertEquals(name1, name2); // Same name and surname
        assertNotEquals(name1, name3); // Different name or surname
    }

    @Test
    public void testNameHashCode() {
        Name name1 = new Name("John", "Doe");
        Name name2 = new Name("John", "Doe");

        assertEquals(name1.hashCode(), name2.hashCode());
    }

    @Test
    public void testNameToString() {
        Name name = new Name("John", "Doe");
        assertEquals("John Doe", name.toString());
    }
}