package de.dhbw.domain.aggregateRoots;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.miscellaneous.Rental;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.Size;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class RentalApartmentUnit implements Rental {
    // In case of remodelling the apartmentNumber and size should be mutable. Rent can change.
    // Implementation specific variables
    private int apartmentNumber;
    private final int floor;
    private final ApartmentComplex parentApartmentComplex;

    // Required variables
    private final RentalId id;
    private LeaseAgreement leaseAgreement;
    private int maxTenants;
    private Size size;

    @JsonCreator
    public RentalApartmentUnit(
            @JsonProperty("parentApartmentComplex") ApartmentComplex parentApartmentComplex,
            @JsonProperty("apartmentNumber") int apartmentNumber,
            @JsonProperty("floor") int floor,
            @JsonProperty("size") Size size,
            @JsonProperty("maxTenants") int maxTenants) {
        this.parentApartmentComplex = parentApartmentComplex;
        setApartmentNumber(apartmentNumber);
        this.floor = floor;
        setSize(size);
        setMaxTenants(maxTenants);
        this.id = new RentalId();
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        if (apartmentNumber <= 0)
            throw new IllegalArgumentException("Invalid apartment number");

        // Check if an apartment with the same number already exists
        for (RentalApartmentUnit rentalApartmentUnit : this.parentApartmentComplex.getRentalApartmentUnits()) {
            if (rentalApartmentUnit.getApartmentNumber() == apartmentNumber)
                throw new IllegalArgumentException("Apartment with this number already exists");
        }

        this.apartmentNumber = apartmentNumber;
    }

    public int getFloor() {
        return floor;
    }

    public ApartmentComplex getParentApartmentComplex() {
        return parentApartmentComplex;
    }

    @Override
    public RentalId getId() {
        return id;
    }

    @Override
    public LeaseAgreement getLeaseAgreement() {
        return leaseAgreement;
    }

    @Override
    public int getMaxTenants() {
        return maxTenants;
    }

    private void setMaxTenants(int maxTenants) {
        if (maxTenants <= 0)
            throw new IllegalArgumentException("Invalid max tenants");

        this.maxTenants = maxTenants;
    }

    @Override
    public Size getSize() {
        return size;
    }

    private void setSize(Size size) {
        this.size = size;
    }

    @Override
    public void remodel(Size size, int maxTenants) {
        // Validate the apartment is not rented
        if (leaseAgreement != null)
            throw new IllegalStateException("Cannot remodel while apartment is rented");

        setSize(size);
        setMaxTenants(maxTenants);
    }

    @Override
    public void rentToTenants(List<Tenant> tenants, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment) {
        // Validate that the number of tenants does not exceed the maximum number of tenants
        if (tenants.size() > maxTenants)
            throw new IllegalArgumentException("Too many tenants");

        leaseAgreement = new LeaseAgreement(tenants, inclusiveStartDate, rent, monthlyDayOfPayment, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalApartmentUnit that = (RentalApartmentUnit) o;
        return apartmentNumber == that.apartmentNumber && floor == that.floor && maxTenants == that.maxTenants && Objects.equals(parentApartmentComplex, that.parentApartmentComplex) && Objects.equals(id, that.id) && Objects.equals(leaseAgreement, that.leaseAgreement) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apartmentNumber, floor, parentApartmentComplex, id, leaseAgreement, maxTenants, size);
    }
}
