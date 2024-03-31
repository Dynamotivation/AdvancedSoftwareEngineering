package de.dhbw.application.transferObjects;

import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.valueObjects.Size;

import java.util.UUID;

public class RentalApartmentUnitSnapshotDTO {
    private int apartmentNumber;
    private final int floor;
    private final UUID parentApartmentComplexId;
    private final UUID rentalId;
    private LeaseAgreement leaseAgreement;
    private int maxTenants;
    private Size size;

    public RentalApartmentUnitSnapshotDTO(RentalApartmentUnit rentalApartmentUnit) {
        this.parentApartmentComplexId = UUID.randomUUID(); // TODO fix
        this.apartmentNumber = rentalApartmentUnit.getApartmentNumber();
        this.floor = rentalApartmentUnit.getFloor();
        this.size = rentalApartmentUnit.getSize();
        this.maxTenants = rentalApartmentUnit.getMaxTenants();
        this.rentalId = rentalApartmentUnit.getId().getId();
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public int getFloor() {
        return floor;
    }

    public UUID getParentApartmentComplexId() {
        return parentApartmentComplexId;
    }

    public UUID getRentalId() {
        return rentalId;
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
