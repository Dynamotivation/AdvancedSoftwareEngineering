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
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

public class RentalApartmentUnit implements Rental {
    private final ApartmentComplex parentApartmentComplex;
    // Required variables
    private final RentalId id;
    // In case of remodelling the doorNumber and size should be mutable. Rent can naturally change.
    // Implementation specific variables
    private DoorNumber doorNumber;
    private LeaseAgreement leaseAgreement;
    private int maxTenants;
    private Size size;

    public RentalApartmentUnit(ApartmentComplex parentApartmentComplex, DoorNumber doorNumber, Size size, int maxTenants) {
        this(doorNumber, parentApartmentComplex, new RentalId(), null, maxTenants, size);
    }

    @JsonCreator
    private RentalApartmentUnit(
            @JsonProperty("doorNumber") @NonNull DoorNumber doorNumber,
            @JsonProperty("parentApartmentComplex") @NonNull ApartmentComplex parentApartmentComplex,
            @JsonProperty("id") @NonNull RentalId id,
            @JsonProperty("leaseAgreement") LeaseAgreement leaseAgreement,
            @JsonProperty("maxTenants") int maxTenants,
            @JsonProperty("size") @NonNull Size size) {
        this.doorNumber = doorNumber;
        this.parentApartmentComplex = parentApartmentComplex;
        this.id = id;
        this.leaseAgreement = leaseAgreement;
        this.size = size;
        setMaxTenants(maxTenants);
    }

    public DoorNumber getDoorNumber() {
        return doorNumber;
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

    @Override
    public void remodel(Size size, int maxTenants) {
        // Validate the apartment is not rented
        if (leaseAgreement != null)
            throw new IllegalStateException("Cannot remodel while apartment is rented");

        this.size = size;
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
    public void endLeaseAgreement(LocalDate submissionDate, LocalDate endDate) {
        if (leaseAgreement == null)
            throw new IllegalStateException("No lease agreement to end");

        leaseAgreement.setInclusiveEndDate(submissionDate, endDate);
        update();
    }

    @Override
    public void update() {
        if (leaseAgreement != null)
            leaseAgreement.update();

        if (leaseAgreement != null && leaseAgreement.getInclusiveEndDate().isBefore(LocalDate.now())) {
            leaseAgreement = null;
        }
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
