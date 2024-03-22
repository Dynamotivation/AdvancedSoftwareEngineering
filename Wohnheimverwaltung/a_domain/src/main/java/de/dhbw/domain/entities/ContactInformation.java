package de.dhbw.domain.entities;

import de.dhbw.domain.utilities.ContactAvenue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactInformation implements Iterable<ContactAvenue> {
    private ContactAvenue preferedContactAvenue;
    private final List<ContactAvenue> contactAvenues = new ArrayList<>();

    public ContactInformation(ContactAvenue preferedContactAvenue, ContactAvenue... contactAvenues) {
        this.preferedContactAvenue = preferedContactAvenue;
        this.contactAvenues.add(preferedContactAvenue);

        this.contactAvenues.addAll(List.of(contactAvenues));
    }

    public ContactAvenue getPreferedContactAvenue() {
        return preferedContactAvenue;
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
        if (preferedContactAvenue.equals(contactAvenue))
            throw new IllegalArgumentException("Cannot remove preferred contact avenue");
        if (!contactAvenues.contains(contactAvenue))
            throw new IllegalArgumentException("Contact avenue does not exist");
        if (contactAvenues.size() == 1)
            throw new IllegalArgumentException("Cannot remove last remaining contact avenue");

        contactAvenues.remove(contactAvenue);
    }

    public void setPreferedContactAvenue(ContactAvenue contactAvenue) {
        if (!contactAvenues.contains(contactAvenue))
            throw new IllegalArgumentException("Contact avenue not found in this contact information. If this is deliberate, add it first.");
        if (preferedContactAvenue.equals(contactAvenue))
            throw new IllegalArgumentException("Contact avenue is already the preferred one");

        preferedContactAvenue = contactAvenue;
    }

    @Override
    public Iterator<ContactAvenue> iterator() {
        return contactAvenues.iterator();
    }
}
