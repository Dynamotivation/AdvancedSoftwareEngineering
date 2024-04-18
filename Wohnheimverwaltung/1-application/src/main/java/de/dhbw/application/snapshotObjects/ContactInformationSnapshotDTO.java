package de.dhbw.application.snapshotObjects;

import de.dhbw.domain.miscellaneous.ContactAvenue;

import java.util.ArrayList;
import java.util.List;

public class ContactInformationSnapshotDTO {
    private final List<ContactAvenue> contactAvenues;
    private final ContactAvenue preferredContactAvenue;

    public ContactInformationSnapshotDTO(ContactAvenue preferredContactAvenue, List<ContactAvenue> contactAvenues) {
        this.contactAvenues = contactAvenues;
        this.preferredContactAvenue = preferredContactAvenue;
    }

    public List<ContactAvenue> getContactAvenues() {
        return new ArrayList<>(contactAvenues);
    }

    public ContactAvenue getPreferredContactAvenue() {
        return preferredContactAvenue;
    }
}
