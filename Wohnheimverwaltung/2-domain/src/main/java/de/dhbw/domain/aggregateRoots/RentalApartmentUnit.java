package de.dhbw.domain.aggregateRoots;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.miscellaneous.Rental;
import de.dhbw.domain.valueObjects.DoorNumber;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.Size;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class RentalApartmentUnit implements Rental {
    // In case of remodelling the doorNumber and size should be mutable. Rent can naturally change.
    // Implementation specific variables
    private DoorNumber doorNumber;
    private final ApartmentComplex parentApartmentComplex;

    // Required variables
    private final RentalId id;
    private LeaseAgreement leaseAgreement;
    private int maxTenants;
    private Size size;

    @JsonCreator
    public RentalApartmentUnit(
            @JsonProperty("parentApartmentComplex") ApartmentComplex parentApartmentComplex,
            @JsonProperty("doorNumber") DoorNumber doorNumber,
            @JsonProperty("size") Size size,
            @JsonProperty("maxTenants") int maxTenants) {
        this.parentApartmentComplex = parentApartmentComplex;
        this.doorNumber = doorNumber;
        setSize(size);
        setMaxTenants(maxTenants);
        this.id = new RentalId();
    }

    public DoorNumber getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(DoorNumber doorNumber) {
        this.doorNumber = doorNumber;
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
    public void rentToTenants(List<Tenant> tenants, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment, int monthsOfNotice) {
        // Validate that the number of tenants does not exceed the maximum number of tenants
        if (tenants.size() > maxTenants)
            throw new IllegalArgumentException("Too many tenants");

        leaseAgreement = new LeaseAgreement(tenants, inclusiveStartDate, rent, monthlyDayOfPayment, monthsOfNotice, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RentalApartmentUnit that = (RentalApartmentUnit) o;
        return maxTenants == that.maxTenants && doorNumber.equals(that.doorNumber) && parentApartmentComplex.equals(that.parentApartmentComplex) && id.equals(that.id) && leaseAgreement.equals(that.leaseAgreement) && size.equals(that.size);
    }

    @Override
    public int hashCode() {
        int result = doorNumber.hashCode();
        result = 31 * result + parentApartmentComplex.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + leaseAgreement.hashCode();
        result = 31 * result + maxTenants;
        result = 31 * result + size.hashCode();
        return result;
    }
}
