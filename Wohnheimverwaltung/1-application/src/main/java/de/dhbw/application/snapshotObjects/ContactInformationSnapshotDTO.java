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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactInformationSnapshotDTO that = (ContactInformationSnapshotDTO) o;
        return contactAvenues.equals(that.contactAvenues) && preferredContactAvenue.equals(that.preferredContactAvenue);
    }

    @Override
    public int hashCode() {
        int result = contactAvenues.hashCode();
        result = 31 * result + preferredContactAvenue.hashCode();
        return result;
    }
}
