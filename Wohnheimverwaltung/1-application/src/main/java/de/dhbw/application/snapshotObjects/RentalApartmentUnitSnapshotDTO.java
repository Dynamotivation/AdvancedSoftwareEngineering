package de.dhbw.application.snapshotObjects;

import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.valueObjects.DoorNumber;
import de.dhbw.domain.valueObjects.Size;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.util.Objects;
import java.util.UUID;

public class RentalApartmentUnitSnapshotDTO {
    private final DoorNumber doorNumber;
    private final ApartmentComplexSnapshotDTO parentApartmentComplex;
    private final RentalId id;
    private LeaseAgreementSnapshotDTO leaseAgreement;
    private final int maxTenants;
    private final Size size;

    public RentalApartmentUnitSnapshotDTO(RentalApartmentUnit rentalApartmentUnit) {
        this.parentApartmentComplex = new ApartmentComplexSnapshotDTO(rentalApartmentUnit.getParentApartmentComplex());
        this.doorNumber = rentalApartmentUnit.getDoorNumber();
        this.size = rentalApartmentUnit.getSize();
        this.maxTenants = rentalApartmentUnit.getMaxTenants();
        this.id = rentalApartmentUnit.getId();
        this.leaseAgreement = rentalApartmentUnit.getLeaseAgreement() == null ? null : new LeaseAgreementSnapshotDTO(rentalApartmentUnit.getLeaseAgreement());
    }

    public DoorNumber getDoorNumber() {
        return doorNumber;
    }

    public ApartmentComplexSnapshotDTO getParentApartmentComplex() {
        return parentApartmentComplex;
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

        RentalApartmentUnitSnapshotDTO that = (RentalApartmentUnitSnapshotDTO) o;
        return maxTenants == that.maxTenants && doorNumber.equals(that.doorNumber) && parentApartmentComplex.equals(that.parentApartmentComplex) && id.equals(that.id) && Objects.equals(leaseAgreement, that.leaseAgreement) && size.equals(that.size);
    }

    @Override
    public int hashCode() {
        int result = doorNumber.hashCode();
        result = 31 * result + parentApartmentComplex.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + Objects.hashCode(leaseAgreement);
        result = 31 * result + maxTenants;
        result = 31 * result + size.hashCode();
        return result;
    }
}
