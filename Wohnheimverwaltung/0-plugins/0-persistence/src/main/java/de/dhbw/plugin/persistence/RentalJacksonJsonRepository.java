package de.dhbw.plugin.persistence;

import de.dhbw.domain.miscellaneous.Rental;
import de.dhbw.domain.repositories.RentalRepository;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.RentalId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.util.ArrayList;
import java.util.List;

public class RentalJacksonJsonRepository implements RentalRepository {
    private final List<Rental> rentals = new ArrayList<>();

    @Override
    public List<Rental> listAll() {
        return new ArrayList<>(rentals);
    }

    @Override
    public Rental findById(RentalId rentalId) {
        return rentals.stream()
                .filter(rental -> rental.getId().equals(rentalId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Rental> findByTenantId(TenantId tenantId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Rental> findByRentalAgreementId(LeaseAgreementId leaseAgreementId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Rental> findByApartmentComplexId(ApartmentComplexId apartmentComplexId) {
        throw new UnsupportedOperationException();
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
