package de.dhbw.domain.miscellaneous;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.dhbw.domain.valueObjects.ContactAvenueEmail;
import org.apache.commons.validator.routines.EmailValidator;

public abstract class ContactAvenue {
    private final String contactDetails;

    @JsonCreator
    public ContactAvenue(
            @JsonProperty("contactDetails") String contactDetails
    ) {
        this.contactDetails = contactDetails;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    @Override
    public String toString() {
        return getContactDetails();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactAvenue that = (ContactAvenue) o;
        return contactDetails.equals(that.contactDetails);
    }

    @Override
    public int hashCode() {
        return contactDetails.hashCode();
    }
}
