package de.dhbw.domain.aggregateRoots;

import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.miscellaneous.Rental;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.ids.RentalId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.time.LocalDate;
import java.util.List;

public class RentalApartmentUnit implements Rental {
    // In case of remodelling the apartmentNumber and size should be mutable. Rent can change.
    // Implementation specific variables
    private int apartmentNumber; // TODO make into a value object or annotate verifications
    private final int floor; // TODO make into a value object or annotate verifications
    private final ApartmentComplex parentApartmentComplex;

    // Required variables
    private final RentalId id;
    private LeaseAgreement leaseAgreement;
    private int maxTenants; // TODO move into RentalInformation
    private double size; // TODO move into RentalInformation

    public RentalApartmentUnit(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction, int apartmentNumber, int floor, double size, int maxTenants) {
        this(new ApartmentComplex(streetName, houseNumber, postalCode, city, dateOfConstruction), apartmentNumber, floor, size, maxTenants);
    }

    public RentalApartmentUnit(ApartmentComplex parentApartmentComplex, int apartmentNumber, int floor, double size, int maxTenants) {
        setApartmentNumber(apartmentNumber);
        this.floor = floor;
        setSize(size);
        setMaxTenants(maxTenants);
        this.parentApartmentComplex = parentApartmentComplex;
        this.id = new RentalId();
    }

    private void setSize(double size) {
        if (size <= 0)
            throw new IllegalArgumentException("Invalid size");

        this.size = size;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        if (apartmentNumber <= 0)
            throw new IllegalArgumentException("Invalid apartment number");

        // Check if an apartment with the same number already exists
        for (RentalApartmentUnit rentalApartmentUnit : this.parentApartmentComplex.getApartments()) {
            if (rentalApartmentUnit.getApartmentNumber() == apartmentNumber)
                throw new IllegalArgumentException("Apartment with this number already exists");
        }

        this.apartmentNumber = apartmentNumber;
    }

    public int getFloor() {
        return floor;
    }

    public ApartmentComplex getParentComplex() {
        return parentApartmentComplex;
    }

    @Override
    public RentalId getId() {
        return id;
    }

    @Override
    public LeaseAgreement GetLeaseAgreement() {
        return leaseAgreement;
    }

    @Override
    public int getMaxTenants() {
        return 0;
    }

    @Override
    //TODO Should this really be public?
    public void setMaxTenants(int maxTenants) {
        if (maxTenants <= 0)
            throw new IllegalArgumentException("Invalid max tenants");

        this.maxTenants = maxTenants;
    }

    @Override
    public double getSize() {
        return size;
    }

    @Override
    public List<TenantId> getTenantIds() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void remodel(double size, int maxTenants) {
        // Validate the apartment is not rented
        if (leaseAgreement != null)
            throw new IllegalStateException("Cannot remodel while apartment is rented");

        setSize(size);
        setMaxTenants(maxTenants);
    }

    @Override
    public void rentToTenant(List<Tenant> tenants, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment) {
        // Validate that the number of tenants does not exceed the maximum number of tenants
        if (tenants.size() > maxTenants)
            throw new IllegalArgumentException("Too many tenants");

        leaseAgreement = new LeaseAgreement(tenants, inclusiveStartDate, rent, monthlyDayOfPayment);
    }
}
