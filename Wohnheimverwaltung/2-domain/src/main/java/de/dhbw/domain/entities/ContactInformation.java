package de.dhbw.domain.entities;

import de.dhbw.domain.miscellaneous.ContactAvenue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactInformation implements Iterable<ContactAvenue> {
    private ContactAvenue preferredContactAvenue;
    private final List<ContactAvenue> contactAvenues = new ArrayList<>();

    public ContactInformation(ContactAvenue preferedContactAvenue, ContactAvenue... contactAvenues) {
        this.preferredContactAvenue = preferedContactAvenue;
        this.contactAvenues.add(preferedContactAvenue);

        this.contactAvenues.addAll(List.of(contactAvenues));
    }

    public ContactAvenue getPreferredContactAvenue() {
        return preferredContactAvenue;
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

    public void setPreferredContactAvenue(ContactAvenue contactAvenue) {
        if (!contactAvenues.contains(contactAvenue))
            throw new IllegalArgumentException("Contact avenue not found in this contact information. If this is deliberate, add it first.");
        if (preferredContactAvenue.equals(contactAvenue))
            throw new IllegalArgumentException("Contact avenue is already the preferred one");

        preferredContactAvenue = contactAvenue;
    }

    @Override
    public Iterator<ContactAvenue> iterator() {
        return contactAvenues.iterator();
    }
}
