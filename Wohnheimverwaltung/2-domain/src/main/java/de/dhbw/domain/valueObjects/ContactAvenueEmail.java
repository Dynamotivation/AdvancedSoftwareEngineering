package de.dhbw.domain.valueObjects;

import de.dhbw.domain.miscellaneous.ContactAvenue;

public class ContactAvenueEmail extends ContactAvenue {
    // An email address as a contact avenue should be a value object.
    // Tenants are free to register a shared inbox for their living space.
    public ContactAvenueEmail(Email contactDetails) {
        super(contactDetails.getEmail());
    }
}
