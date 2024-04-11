package de.dhbw.domain.valueObjects;

import de.dhbw.domain.miscellaneous.ContactAvenue;

public class ContactAvenueMail implements ContactAvenue {
    // Mail addresses as a contact avenue should be a value object.
    // It is obvious that multiple tenants can have the same address. A message would be aggregated by the tenants details.
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
