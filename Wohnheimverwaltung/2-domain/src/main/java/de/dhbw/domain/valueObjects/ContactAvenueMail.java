package de.dhbw.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.dhbw.domain.miscellaneous.ContactAvenue;

public class ContactAvenueMail extends ContactAvenue {
    // Mail addresses as a contact avenue should be a value object.
    // It is obvious that multiple tenants can have the same address. A message would be aggregated by the tenants details.
    public ContactAvenueMail(Address contactDetails){
        super(contactDetails.toString());
    }
}
