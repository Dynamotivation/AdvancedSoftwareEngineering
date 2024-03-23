package de.dhbw.domain.valueObjects;

import org.apache.commons.validator.routines.EmailValidator;

public class ContactAvenueMail {
    private final Address address;

    public ContactAvenueMail(String streetName, String houseNumber, String postalCode, String city) {
        this.address = new Address(streetName, houseNumber, postalCode, city);
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactAvenueMail that = (ContactAvenueMail) o;

        return address.equals(that.getAddress());
    }
}
