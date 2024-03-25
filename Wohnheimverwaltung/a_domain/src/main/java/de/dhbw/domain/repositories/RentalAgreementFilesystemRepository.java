package de.dhbw.domain.repositories;

import de.dhbw.domain.aggregates.RentalAgreement;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.entities.ApartmentUnit;
import de.dhbw.domain.entities.Tenant;
import de.dhbw.domain.utilities.Rentable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RentalAgreementFilesystemRepository implements RentalAgreementRepository {
    private final List<RentalAgreement> rentalAgreements = new ArrayList<>();

    @Override
    public List<RentalAgreement> listAll() {
        //TODO test if this is enough to obfuscate the pointer
        return new ArrayList<>(rentalAgreements);
    }

    @Override
    public RentalAgreement findById(UUID id) {
        return rentalAgreements.stream()
                .filter(rentalAgreement -> rentalAgreement.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<RentalAgreement> findByTenant(Tenant tenant) {
        return rentalAgreements.stream()
                .filter(rentalAgreement -> rentalAgreement.getTenants().contains(tenant))
                .toList();
    }

    @Override
    public List<RentalAgreement> findByRentable(Rentable rentable) {
        return rentalAgreements.stream()
                .filter(rentalAgreement -> rentable.equals(rentalAgreement.getRentable()))
                .toList();
    }

    @Override
    public List<RentalAgreement> findByApartmentComplex(ApartmentComplex apartmentComplex) {
        return rentalAgreements.stream()
                .filter(rentalAgreement -> rentalAgreement.getRentable().getClass().equals(ApartmentUnit.class))
                .filter(rentalAgreement -> ((ApartmentUnit) rentalAgreement.getRentable()).getParentComplex().equals(apartmentComplex))
                .toList();
    }

    @Override
    public void save(RentalAgreement rentalAgreement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void load(RentalAgreement rentalAgreement) {
        throw new UnsupportedOperationException();
    }
}
