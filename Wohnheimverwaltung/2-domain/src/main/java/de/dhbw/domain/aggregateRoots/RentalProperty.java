package de.dhbw.domain.aggregateRoots;

import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.miscellaneous.Rental;
import de.dhbw.domain.valueObjects.Address;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.Size;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class RentalProperty implements Rental {
    // Implementation specific variables
    private final Address address;
    private final LocalDate dateOfConstruction;

    // Required variables
    private final RentalId id;
    private LeaseAgreement leaseAgreement;
    private int maxTenants;
    private Size size;

    public RentalProperty(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction, Size size, int maxTenants) {
        // Validate date of construction (implicitly checked for null)
        if (dateOfConstruction.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date of construction may not be in the future");

        this.address = new Address(streetName, houseNumber, postalCode, city);
        this.dateOfConstruction = dateOfConstruction;
        this.id = new RentalId();
        this.size = size;
        setMaxTenants(maxTenants);
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getDateOfConstruction() {
        return dateOfConstruction;
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
        setSize(size);
        setMaxTenants(maxTenants);
    }

    @Override
    public void rentToTenants(List<Tenant> tenants, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment) {
        // Validate that the rental property is not already booked
        if (leaseAgreement != null)
            throw new IllegalArgumentException("Rental property is already booked");

        // Validate that the number of tenants does not exceed the maximum number of tenants
        if (tenants.size() > maxTenants)
            throw new IllegalArgumentException("Too many tenants");


        leaseAgreement = new LeaseAgreement(tenants, inclusiveStartDate, rent, monthlyDayOfPayment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalProperty that = (RentalProperty) o;
        return maxTenants == that.maxTenants && Objects.equals(address, that.address) && Objects.equals(dateOfConstruction, that.dateOfConstruction) && Objects.equals(id, that.id) && Objects.equals(leaseAgreement, that.leaseAgreement) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, dateOfConstruction, id, leaseAgreement, maxTenants, size);
    }
}
