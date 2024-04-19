package de.dhbw.application.snapshotObjects;

import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.valueObjects.Address;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApartmentComplexSnapshotDTO {
    private final Address address;
    private final LocalDate dateOfConstruction;
    private final List<RentalId> rentalApartmentUnits;
    private final ApartmentComplexId id;

    public ApartmentComplexSnapshotDTO(ApartmentComplex parentApartmentComplex) {
        this.address = parentApartmentComplex.getAddress();
        this.dateOfConstruction = parentApartmentComplex.getDateOfConstruction();
        this.rentalApartmentUnits = new ArrayList<>(parentApartmentComplex.getRentalApartmentUnits());
        this.id = parentApartmentComplex.getId();
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getDateOfConstruction() {
        return dateOfConstruction;
    }

    public List<RentalId> getRentalApartmentUnits() {
        return rentalApartmentUnits;
    }

    public ApartmentComplexId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApartmentComplexSnapshotDTO that = (ApartmentComplexSnapshotDTO) o;
        return address.equals(that.address) && dateOfConstruction.equals(that.dateOfConstruction) && rentalApartmentUnits.equals(that.rentalApartmentUnits) && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = address.hashCode();
        result = 31 * result + dateOfConstruction.hashCode();
        result = 31 * result + rentalApartmentUnits.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
