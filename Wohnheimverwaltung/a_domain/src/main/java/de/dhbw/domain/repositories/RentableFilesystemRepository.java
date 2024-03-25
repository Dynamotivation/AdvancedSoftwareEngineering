package de.dhbw.domain.repositories;

import de.dhbw.domain.entities.RentalAgreement;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.aggregates.ApartmentUnit;
import de.dhbw.domain.entities.Tenant;
import de.dhbw.domain.utilities.Rentable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RentableFilesystemRepository implements RentableRepository {
    private final List<Rentable> rentables = new ArrayList<>();

    @Override
    public List<Rentable> listAll() {
        //TODO test if this is enough to obfuscate the pointer
        return new ArrayList<>(rentables);
    }

    @Override
    public Rentable findById(UUID id) {
        return rentables.stream()
                .filter(rentable -> rentable.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Rentable> findByTenant(Tenant tenant) {
        return rentables.stream()
                .filter(rentable -> rentable.GetRentalAgreement().getTenants().contains(tenant))
                .toList();
    }

    @Override
    public List<Rentable> findByRentalAgreement(RentalAgreement rentalAgreement) {
        return rentables.stream()
                .filter(rentable -> rentable.GetRentalAgreement().equals(rentalAgreement))
                .toList();
    }

    @Override
    public List<Rentable> findByApartmentComplex(ApartmentComplex apartmentComplex) {
        return rentables.stream()
                .filter(rentable -> rentable.getClass().equals(ApartmentUnit.class))
                .filter(rentable -> ((ApartmentUnit) rentable).getParentComplex().equals(apartmentComplex))
                .toList();
    }

    @Override
    public void save(Rentable rentable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void load(Rentable rentable) {
        throw new UnsupportedOperationException();
    }
}
