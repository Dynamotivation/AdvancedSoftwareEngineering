package de.dhbw.domain.entities;

public class Name {
    private final String name;
    private final String surname;

    public Name(String name, String surname) {
        this.name = validateName(name);
        this.surname = validateName(surname);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        return String.format("%s %s", name, surname);
    }

    private String validateName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("First name must not be null or empty");

        name = name.trim();

        return name;
    }
//TODO Add something like changing to make it into an entity for sure
    @Override
    public String toString() {
        return getFullName();
    }
}
