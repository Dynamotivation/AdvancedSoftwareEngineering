package de.dhbw.domain.repositories;

import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.miscellaneous.Rental;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RentalFilesystemRepository implements RentalRepository {
    private final List<Rental> rentals = new ArrayList<>();

    @Override
    public List<Rental> listAll() {
        //TODO test if this is enough to obfuscate the pointer
        return new ArrayList<>(rentals);
    }

    @Override
    public Rental findById(UUID id) {
        return rentals.stream()
                .filter(rental -> rental.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Rental> findByTenant(Tenant tenant) {
        return rentals.stream()
                .filter(rental -> rental.GetRentalAgreement().getTenants().contains(tenant))
                .toList();
    }

    @Override
    public List<Rental> findByRentalAgreement(LeaseAgreement leaseAgreement) {
        return rentals.stream()
                .filter(rental -> rental.GetRentalAgreement().equals(leaseAgreement))
                .toList();
    }

    @Override
    public List<Rental> findByApartmentComplex(ApartmentComplex apartmentComplex) {
        return rentals.stream()
                .filter(rental -> rental.getClass().equals(RentalApartmentUnit.class))
                .filter(rental -> ((RentalApartmentUnit) rental).getParentComplex().equals(apartmentComplex))
                .toList();
    }

    @Override
    public void save(Rental rental) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void load(Rental rental) {
        throw new UnsupportedOperationException();
    }
}
