package de.dhbw.domain.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.dhbw.domain.miscellaneous.ContactAvenue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactInformation implements Iterable<ContactAvenue> {
    private final List<ContactAvenue> contactAvenues = new ArrayList<>();
    private ContactAvenue preferredContactAvenue;

    public ContactInformation(ContactAvenue preferedContactAvenue, ContactAvenue... contactAvenues) {
        this(preferedContactAvenue, List.of(contactAvenues));
    }

    @JsonCreator
    private ContactInformation(
            @JsonProperty("preferredContactAvenue") ContactAvenue preferredContactAvenue,
            @JsonProperty("contactAvenues") List<ContactAvenue> contactAvenues
    ) {
        this.preferredContactAvenue = preferredContactAvenue;
        this.contactAvenues.addAll(contactAvenues);

        if (!this.contactAvenues.contains(preferredContactAvenue))
            this.contactAvenues.add(preferredContactAvenue);
    }

    public ContactAvenue getPreferredContactAvenue() {
        return preferredContactAvenue;
    }

    public void setPreferredContactAvenue(ContactAvenue contactAvenue) {
        if (!contactAvenues.contains(contactAvenue))
            throw new IllegalArgumentException("Contact avenue not found in this contact information. If this is deliberate, add it first.");
        if (preferredContactAvenue.equals(contactAvenue))
            throw new IllegalArgumentException("Contact avenue is already the preferred one");

        preferredContactAvenue = contactAvenue;
    }

    public List<ContactAvenue> getContactAvenues() {
        return contactAvenues;
    }

    public void addContactAvenue(ContactAvenue contactAvenue) {
        if (contactAvenues.contains(contactAvenue))
            throw new IllegalArgumentException("Contact avenue already exists");

        contactAvenues.add(contactAvenue);
    }

    public void removeContactAvenue(ContactAvenue contactAvenue) {
        if (preferredContactAvenue.equals(contactAvenue) && contactAvenues.size() == 1)
            throw new IllegalArgumentException("Cannot remove preferred contact avenue");
        if (!contactAvenues.contains(contactAvenue))
            throw new IllegalArgumentException("Contact avenue does not exist");
        if (contactAvenues.size() == 1)
            throw new IllegalArgumentException("Cannot remove last remaining contact avenue");

        contactAvenues.remove(contactAvenue);

        if (preferredContactAvenue.equals(contactAvenue))
            preferredContactAvenue = contactAvenues.getFirst();
    }

    @Override
    public Iterator<ContactAvenue> iterator() {
        return contactAvenues.iterator();
    }
}
