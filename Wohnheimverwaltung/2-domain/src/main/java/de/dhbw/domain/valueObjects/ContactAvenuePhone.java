package de.dhbw.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import de.dhbw.domain.miscellaneous.ContactAvenue;

public class ContactAvenuePhone extends ContactAvenue {
    // A phone number as a contact avenue should be a value object.
    // Even if multiple tenants have the same phone number we do not discriminate against a shared landline.
    @JsonCreator
    public ContactAvenuePhone(
            @JsonProperty("contactDetails") PhoneNumber contactDetails
    ) {
        super(String.format("+%d%d", contactDetails.getCountryCode(), contactDetails.getNationalNumber()));

        if (!PhoneNumberUtil.getInstance().isValidNumber(contactDetails))
            throw new IllegalArgumentException("Invalid phone number");
    }
}
