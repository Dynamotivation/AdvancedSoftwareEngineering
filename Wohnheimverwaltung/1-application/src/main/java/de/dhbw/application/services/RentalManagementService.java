package de.dhbw.application.services;

import de.dhbw.application.snapshotObjects.RentalApartmentUnitSnapshotDTO;
import de.dhbw.application.snapshotObjects.RentalPropertySnapshotDTO;
import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.aggregateRoots.RentalProperty;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.miscellaneous.Rental;
import de.dhbw.domain.repositories.ApartmentComplexRepository;
import de.dhbw.domain.repositories.RentalRepository;
import de.dhbw.domain.repositories.TenantRepository;
import de.dhbw.domain.valueObjects.DoorNumber;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.Size;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;
import de.dhbw.domain.valueObjects.ids.RentalId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RentalManagementService {
    private final RentalRepository rentalRepository;
    private final TenantRepository tenantRepository;
    private final ApartmentComplexRepository apartmentComplexRepository;

    public RentalManagementService(RentalRepository rentalRepository, TenantRepository tenantRepository, ApartmentComplexRepository apartmentComplexRepository) {
        this.rentalRepository = rentalRepository;
        this.tenantRepository = tenantRepository;
        this.apartmentComplexRepository = apartmentComplexRepository;
    }

    public RentalId createRentalProperty(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction, BigDecimal size, int maxTenants) {
        RentalProperty rentalProperty = new RentalProperty(streetName, houseNumber, postalCode, city, dateOfConstruction, Size.squareMeters(size), maxTenants);
        rentalRepository.add(rentalProperty);

        return rentalProperty.getId();
    }

    public RentalId createRentalApartmentUnit(ApartmentComplexId apartmentComplexId, int apartmentNumber, int floor, BigDecimal size, int maxTenants) {
        ApartmentComplex apartmentComplex = apartmentComplexRepository.findByApartmentComplexId(apartmentComplexId);
        RentalApartmentUnit rentalApartmentUnit = new RentalApartmentUnit(apartmentComplex, new DoorNumber(floor, apartmentNumber), Size.squareMeters(size), maxTenants);
        rentalRepository.add(rentalApartmentUnit);
        apartmentComplexRepository.findByApartmentComplexId(apartmentComplexId).addApartment(rentalApartmentUnit);

        return rentalApartmentUnit.getId();
    }

    public void rentRentalToTenants(RentalId rentalId, List<TenantId> tenantIds, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment, int monthsOfNotice) {
        Rental rental = rentalRepository.findById(rentalId);

        List<Tenant> tenants = tenantIds.stream()
                .map(tenantRepository::findById)
                .toList();

        rental.rentToTenants(tenants, inclusiveStartDate, rent, monthlyDayOfPayment, monthsOfNotice);
    }

    public void endLeaseAgreement(RentalId rentalId, LocalDate submissionDate, LocalDate endDate) {
        Rental rental = rentalRepository.findById(rentalId);

        rental.endLeaseAgreement(submissionDate, endDate);
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

    public RentalApartmentUnitSnapshotDTO getRentalApartmentUnitSnapshotById(RentalId rentalId) {
        Rental rental = rentalRepository.findById(rentalId);

        if (rental instanceof RentalApartmentUnit rentalApartmentUnit)
            return new RentalApartmentUnitSnapshotDTO(rentalApartmentUnit);

        return null;
    }

    public List<RentalApartmentUnitSnapshotDTO> getRentalApartmentUnitSnapshotsByApartmentComplexId(ApartmentComplexId apartmentComplexId) {
        return rentalRepository.listAllRentalApartmentUnits()
                .stream()
                .filter(rental -> rental.getParentApartmentComplex().getId().equals(apartmentComplexId))
                .map(RentalApartmentUnitSnapshotDTO::new)
                .toList();
    }

    public void deleteRental(RentalId rentalId) {
        Rental rental = rentalRepository.findById(rentalId);
        rentalRepository.remove(rental);
    }

    public void updateRental(RentalId rentalId) {
        Rental rental = rentalRepository.findById(rentalId);
        rental.update();
    }

    public void saveAllRentals() {
        for (Rental rental : rentalRepository.listAll()) {
            rentalRepository.save(rental);
        }
    }

    public void loadRentals() {
        List<Rental> newRentals = rentalRepository.load();

        for (Rental rental : newRentals) {
            if (rental instanceof RentalApartmentUnit rentalApartmentUnit) {
                apartmentComplexRepository.add(rentalApartmentUnit.getParentApartmentComplex());
            }
        }
    }

    public void exportRentalById(RentalId rentalId) {
        Rental rental = rentalRepository.findById(rentalId);
        rentalRepository.save(rental);
    }

    public List<RentalId> importRentals() {
        return rentalRepository.load().stream()
                .map(Rental::getId)
                .toList();
    }
}
