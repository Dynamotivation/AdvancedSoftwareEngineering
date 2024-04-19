package de.dhbw.application.snapshotObjects;

import de.dhbw.domain.aggregateRoots.RentalProperty;
import de.dhbw.domain.valueObjects.Address;
import de.dhbw.domain.valueObjects.Size;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.time.LocalDate;
import java.util.Objects;

public class RentalPropertySnapshotDTO {
    private final Address address;
    private final LocalDate dateOfConstruction;
    private final RentalId id;
    private final LeaseAgreementSnapshotDTO leaseAgreement;
    private final int maxTenants;
    private final Size size;

    public RentalPropertySnapshotDTO(RentalProperty rentalProperty) {
        this.address = rentalProperty.getAddress();
        this.dateOfConstruction = rentalProperty.getDateOfConstruction();
        this.id = rentalProperty.getId();
        this.size = rentalProperty.getSize();
        this.maxTenants = rentalProperty.getMaxTenants();
        this.leaseAgreement = rentalProperty.getLeaseAgreement() == null ? null : new LeaseAgreementSnapshotDTO(rentalProperty.getLeaseAgreement());
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getDateOfConstruction() {
        return dateOfConstruction;
    }

    public RentalId getId() {
        return id;
    }

    public LeaseAgreementSnapshotDTO getLeaseAgreement() {
        return leaseAgreement;
    }

    public int getMaxTenants() {
        return maxTenants;
    }

    public Size getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RentalPropertySnapshotDTO that = (RentalPropertySnapshotDTO) o;
        return maxTenants == that.maxTenants && address.equals(that.address) && dateOfConstruction.equals(that.dateOfConstruction) && id.equals(that.id) && Objects.equals(leaseAgreement, that.leaseAgreement) && size.equals(that.size);
    }

    @Override
    public int hashCode() {
        int result = address.hashCode();
        result = 31 * result + dateOfConstruction.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + Objects.hashCode(leaseAgreement);
        result = 31 * result + maxTenants;
        result = 31 * result + size.hashCode();
        return result;
    }
}
