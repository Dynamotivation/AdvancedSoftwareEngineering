package de.dhbw.domain.aggregateRoots;

import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.entities.RentalAgreement;
import de.dhbw.domain.utilities.Rentable;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.ids.RentableId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ApartmentUnit implements Rentable {
    // In case of remodelling the apartmentNumber and size should be mutable. Rent can change.
    // Implementation specific variables
    private int apartmentNumber; // TODO make into a value object or annotate verifications
    private final int floor; // TODO make into a value object or annotate verifications
    private final ApartmentComplex parentApartmentComplex;

    // Required variables
    private final RentableId id;
    private int maxTenants; // TODO make into a value object or annotate verifications
    private RentalAgreement rentalAgreement;
    private double size; // TODO make into a value object or annotate verifications

    public ApartmentUnit(ApartmentComplex parentApartmentComplex, int apartmentNumber, int floor, double size, int maxTenants, Rent rent) {
        setApartmentNumber(apartmentNumber);
        this.floor = floor;
        setSize(size);
        setMaxTenants(maxTenants);
        this.parentApartmentComplex = parentApartmentComplex;
        this.id = new RentableId();
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
        for (ApartmentUnit apartmentUnit : this.parentApartmentComplex.getApartments()) {
            if (apartmentUnit.getApartmentNumber() == apartmentNumber)
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
    public void remodel(double size, int maxTenants) {
        // Validate the apartment is not rented
        if (rentalAgreement != null)
            throw new IllegalStateException("Cannot remodel while apartment is rented");

        setSize(size);
        setMaxTenants(maxTenants);
    }

    @Override
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
    public RentableId getId() {
        return id;
    }

    @Override
    public RentalAgreement getRentalAgreement() {
        return rentalAgreement;
    }

    @Override
    public void rentToTenant(List<Tenant> tenants, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment) {
        // Validate that the number of tenants does not exceed the maximum number of tenants
        if (tenants.size() > maxTenants)
            throw new IllegalArgumentException("Too many tenants");

        rentalAgreement = new RentalAgreement(tenants, inclusiveStartDate, rent, monthlyDayOfPayment);
    }

    @Override
    public List<TenantId> getTenantIds() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public RentalAgreement GetRentalAgreement() {
        return rentalAgreement;
    }
}
