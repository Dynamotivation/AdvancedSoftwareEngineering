package de.dhbw.domain.aggregates;

import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.entities.RentalAgreement;
import de.dhbw.domain.entities.Tenant;
import de.dhbw.domain.utilities.Rentable;
import de.dhbw.domain.valueObjects.Rent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ApartmentUnit implements Rentable {
    // In case of remodelling the apartmentNumber and size should be mutable. Rent can change.
    private int apartmentNumber;
    private double size;
    private final int floor;
    private final List<Tenant> tenants = new ArrayList<>();
    private final ApartmentComplex parentApartmentComplex;
    private int maxTenants;
    private RentalAgreement rentalAgreement;
    private final UUID id = UUID.randomUUID();

    public ApartmentUnit(ApartmentComplex parentApartmentComplex, int apartmentNumber, int floor, double size, int maxTenants, Rent rent) {
        setApartmentNumber(apartmentNumber);
        this.floor = floor;
        setSize(size);
        setMaxTenants(maxTenants);
        this.parentApartmentComplex = parentApartmentComplex;
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
    public UUID getId() {
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
    public List<Tenant> getTenants() {
        return tenants;
    }

    @Override
    public RentalAgreement GetRentalAgreement() {
        return rentalAgreement;
    }

    public ApartmentComplex getParentComplex() {
        return parentApartmentComplex;
    }
}
