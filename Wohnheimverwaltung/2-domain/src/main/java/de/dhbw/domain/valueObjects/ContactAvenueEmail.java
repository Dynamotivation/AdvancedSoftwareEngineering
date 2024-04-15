package de.dhbw.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.dhbw.domain.miscellaneous.ContactAvenue;
import org.apache.commons.validator.routines.EmailValidator;

public class ContactAvenueEmail implements ContactAvenue {
    // An email address as a contact avenue should be a value object.
    // Tenants are free to register a shared inbox for their living space.
    private final String email;

    @JsonCreator
    public ContactAvenueEmail(
            @JsonProperty("email") String email
    ) {
        if (!EmailValidator.getInstance(true, true).isValid(email))
            throw new IllegalArgumentException("Invalid email address");

        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactAvenueEmail that = (ContactAvenueEmail) o;

        return email.equals(that.getEmail());
    }
}
