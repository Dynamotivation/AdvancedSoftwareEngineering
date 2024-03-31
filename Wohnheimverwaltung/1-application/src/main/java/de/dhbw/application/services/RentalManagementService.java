package de.dhbw.application.services;

import de.dhbw.application.snapshotObjects.RentalApartmentUnitSnapshotDTO;
import de.dhbw.application.snapshotObjects.RentalPropertySnapshotDTO;
import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.aggregateRoots.RentalProperty;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.miscellaneous.Rental;
import de.dhbw.domain.repositories.RentalRepository;
import de.dhbw.domain.repositories.TenantRepository;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.Size;
import de.dhbw.domain.valueObjects.ids.RentalId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RentalManagementService {
    private final RentalRepository rentalRepository;
    private final TenantRepository tenantRepository;

    public RentalManagementService(RentalRepository rentalRepository, TenantRepository tenantRepository) {
        this.rentalRepository = rentalRepository;
        this.tenantRepository = tenantRepository;
    }

    public RentalId createRentalProperty(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction, BigDecimal size, int maxTenants) {
        RentalProperty rentalProperty = new RentalProperty(streetName, houseNumber, postalCode, city, dateOfConstruction, Size.squareMeters(size), maxTenants);
        rentalRepository.add(rentalProperty);

        return rentalProperty.getId();
    }

    public RentalId createRentalApartmentUnit(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction, int apartmentNumber, int floor, BigDecimal size, int maxTenants) {
        RentalApartmentUnit rentalApartmentUnit = new RentalApartmentUnit(streetName, houseNumber, postalCode, city, dateOfConstruction, apartmentNumber, floor, Size.squareMeters(size), maxTenants);
        rentalRepository.add(rentalApartmentUnit);

        return rentalApartmentUnit.getId();
    }

    public void rentRentalPropertyToTenants(RentalId rentalId, List<TenantId> tenantIds, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment) {
        Rental rental = rentalRepository.findById(rentalId);

        List<Tenant> tenants = tenantIds.stream()
                .map(tenantRepository::findById)
                .toList();

        rental.rentToTenants(tenants, inclusiveStartDate, rent, monthlyDayOfPayment);
    }

    public List<RentalApartmentUnitSnapshotDTO> listAllRentalApartmentUnitSnapshots() {
        return rentalRepository.listAllRentalApartmentUnits()
                .stream()
                .map(RentalApartmentUnitSnapshotDTO::new)
                .toList();
    }

    public List<RentalPropertySnapshotDTO> listAllRentalPropertySnapshots() {
        return rentalRepository.listAllRentalProperties()
                .stream()
                .map(RentalPropertySnapshotDTO::new)
                .toList();
    }

    public RentalPropertySnapshotDTO getRentalPropertySnapshotById(RentalId rentalId) {
        Rental rental = rentalRepository.findById(rentalId);

        if (rental instanceof RentalProperty rentalProperty)
            return new RentalPropertySnapshotDTO(rentalProperty);

        return null;
    }
}
