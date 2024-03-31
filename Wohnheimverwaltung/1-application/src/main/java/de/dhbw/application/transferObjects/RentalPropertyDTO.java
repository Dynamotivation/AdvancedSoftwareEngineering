package de.dhbw.application.transferObjects;

import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.aggregateRoots.RentalProperty;
import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.valueObjects.Address;
import de.dhbw.domain.valueObjects.Size;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.time.LocalDate;
import java.util.UUID;

public class RentalPropertyDTO {
    private final String streetName;
    private final String houseNumber;
    private final String postalCode;
    private final String city;
    private final LocalDate dateOfConstruction;
    private final RentalId UUID;
    private LeaseAgreement leaseAgreement;
    private int maxTenants;
    private Size size;

    public RentalPropertyDTO(RentalProperty rentalProperty) {
        this.streetName = rentalProperty.getAddress().getStreetName();
        this.houseNumber = rentalProperty.getAddress().getHouseNumber();
        this.postalCode = rentalProperty.getAddress().getPostalCode();
        this.city = rentalProperty.getAddress().getCity();
        this.dateOfConstruction = rentalProperty.getDateOfConstruction();
        this.UUID = rentalProperty.getId();
        this.size = rentalProperty.getSize();
        this.maxTenants = rentalProperty.getMaxTenants();
        this.leaseAgreement = rentalProperty.getLeaseAgreement();
    }

    public String getStreetName() {
        return streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getDateOfConstruction() {
        return dateOfConstruction;
    }

    public RentalId getUUID() {
        return UUID;
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
