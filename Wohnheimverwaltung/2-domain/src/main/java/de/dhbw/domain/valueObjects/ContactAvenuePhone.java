package de.dhbw.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import de.dhbw.domain.miscellaneous.ContactAvenue;

public class ContactAvenuePhone implements ContactAvenue {
    // A phone number as a contact avenue should be a value object.
    // Even if multiple tenants have the same phone number we do not discriminate against a shared landline.
    private final PhoneNumber phone;

    @JsonCreator
    public ContactAvenuePhone(
            @JsonProperty("phone") PhoneNumber phone
    ) {
        if (!PhoneNumberUtil.getInstance().isValidNumber(phone))
            throw new IllegalArgumentException("Invalid phone number");

        this.phone = phone;
    }

    public PhoneNumber getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactAvenuePhone that = (ContactAvenuePhone) o;

        return phone.equals(that.phone);
    }
}
