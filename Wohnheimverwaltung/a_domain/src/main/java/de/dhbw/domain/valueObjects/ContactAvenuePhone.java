package de.dhbw.domain.valueObjects;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import de.dhbw.domain.utilities.ContactAvenue;

public class ContactAvenuePhone implements ContactAvenue {
    private final PhoneNumber phone;

    public ContactAvenuePhone(PhoneNumber phone) {
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
