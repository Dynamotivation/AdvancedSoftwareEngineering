package de.dhbw.domain.valueObjects;

import lombok.NonNull;
import org.apache.commons.validator.routines.EmailValidator;

public class Email {
    private final String email;

    public Email(@NonNull String email) {
        if (!EmailValidator.getInstance().isValid(email))
            throw new IllegalArgumentException("Invalid email address");

        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email1 = (Email) o;
        return email.equals(email1.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
