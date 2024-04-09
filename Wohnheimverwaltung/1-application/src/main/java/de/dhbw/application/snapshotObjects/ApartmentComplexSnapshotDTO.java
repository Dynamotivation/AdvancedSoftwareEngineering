package de.dhbw.application.snapshotObjects;

import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.valueObjects.Address;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApartmentComplexSnapshotDTO {
    private final Address address;
    private final LocalDate dateOfConstruction;
    private final List<RentalApartmentUnitSnapshotDTO> rentalApartmentUnits = new ArrayList<>();
    private final ApartmentComplexId id;

    public ApartmentComplexSnapshotDTO(ApartmentComplex parentApartmentComplex) {
        this.address = parentApartmentComplex.getAddress();
        this.dateOfConstruction = parentApartmentComplex.getDateOfConstruction();
        this.rentalApartmentUnits.addAll(parentApartmentComplex.getRentalApartmentUnits().stream().map(RentalApartmentUnitSnapshotDTO::new).toList());
        this.id = parentApartmentComplex.getId();
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getDateOfConstruction() {
        return dateOfConstruction;
    }

    public List<RentalApartmentUnitSnapshotDTO> getRentalApartmentUnits() {
        return rentalApartmentUnits;
    }

    public ApartmentComplexId getId() {
        return id;
    }
}
