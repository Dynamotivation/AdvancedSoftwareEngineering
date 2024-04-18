package de.dhbw.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.dhbw.domain.miscellaneous.ContactAvenue;
import org.apache.commons.validator.routines.EmailValidator;

public class ContactAvenueEmail extends ContactAvenue {
    // An email address as a contact avenue should be a value object.
    // Tenants are free to register a shared inbox for their living space.
    public ContactAvenueEmail(Email contactDetails) {
        super(contactDetails.getEmail());
    }
}
