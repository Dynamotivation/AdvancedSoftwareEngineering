package de.dhbw.application.snapshotObjects;

import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.valueObjects.DoorNumber;
import de.dhbw.domain.valueObjects.Size;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.util.UUID;

public class RentalApartmentUnitSnapshotDTO {
    private final DoorNumber doorNumber;
    private final ApartmentComplexSnapshotDTO parentApartmentComplex;
    private final RentalId id;
    private LeaseAgreement leaseAgreement;
    private final int maxTenants;
    private final Size size;

    public RentalApartmentUnitSnapshotDTO(RentalApartmentUnit rentalApartmentUnit) {
        this.parentApartmentComplex = new ApartmentComplexSnapshotDTO(rentalApartmentUnit.getParentApartmentComplex());
        this.doorNumber = rentalApartmentUnit.getDoorNumber();
        this.size = rentalApartmentUnit.getSize();
        this.maxTenants = rentalApartmentUnit.getMaxTenants();
        this.id = rentalApartmentUnit.getId();
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

    public LeaseAgreement getLeaseAgreement() {
        return leaseAgreement;
    }

    public int getMaxTenants() {
        return maxTenants;
    }

    public Size getSize() {
        return size;
    }
}
