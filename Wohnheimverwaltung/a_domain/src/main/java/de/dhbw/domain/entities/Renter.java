package de.dhbw.domain.entities;

import de.dhbw.domain.utilities.ContactAvenue;

public class Renter {
    private final String name;
    private final String surname;
    private final ContactInformation contactInformation;

    public Renter(String name, String surname, ContactAvenue contactAvenue) {
        this.name = name;
        this.surname = surname;
        this.contactInformation = new ContactInformation(contactAvenue);

        // TODO Validate name and surname and check if contactAvenue is not MAIL TO HIS RENTED ADDRESS
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }
}
