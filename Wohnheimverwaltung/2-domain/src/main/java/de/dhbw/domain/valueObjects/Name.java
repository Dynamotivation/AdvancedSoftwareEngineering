package de.dhbw.domain.valueObjects;

import java.util.Objects;

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
            throw new IllegalArgumentException("Name must not be null or empty");

        name = name.trim();

        return name;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name) && Objects.equals(surname, name1.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }
}