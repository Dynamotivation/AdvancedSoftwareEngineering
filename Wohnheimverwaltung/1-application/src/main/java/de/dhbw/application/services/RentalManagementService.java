package de.dhbw.application.services;

import de.dhbw.application.transferObjects.RentalApartmentUnitDTO;
import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.aggregateRoots.RentalProperty;
import de.dhbw.domain.repositories.RentalRepository;
import de.dhbw.domain.valueObjects.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RentalManagementService {
    private final RentalRepository rentalRepository;

    public RentalManagementService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void createRentalProperty(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction, BigDecimal size, int maxTenants) {
        RentalProperty rentalProperty = new RentalProperty(streetName, houseNumber, postalCode, city, dateOfConstruction, Size.squareMeters(size), maxTenants);
        rentalRepository.add(rentalProperty);
    }

    public void createRentalApartmentUnit(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction, int apartmentNumber, int floor, BigDecimal size, int maxTenants) {
        RentalApartmentUnit rentalApartmentUnit = new RentalApartmentUnit(streetName, houseNumber, postalCode, city, dateOfConstruction, apartmentNumber, floor, Size.squareMeters(size), maxTenants);
        rentalRepository.add(rentalApartmentUnit);
    }

    public List<RentalApartmentUnitDTO> listAllRentalApartmentUnits() {
        return rentalRepository.listAllRentalApartmentUnits().stream()
                .map(RentalApartmentUnitDTO::new)
                .toList();
    }

    public List<RentalProperty> listAllRentalProperties() {
        return rentalRepository.listAllRentalProperties();
    }
}
