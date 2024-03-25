package de.dhbw.domain.repositories;

import de.dhbw.domain.entities.RentalAgreement;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.entities.Tenant;
import de.dhbw.domain.utilities.Rentable;

import java.util.List;
import java.util.UUID;

public interface RentableRepository {
    List<Rentable> listAll();
    Rentable findById(UUID id);
    List<Rentable> findByTenant(Tenant tenant);
    List<Rentable> findByRentalAgreement(RentalAgreement rentalAgreement);
    List<Rentable> findByApartmentComplex(ApartmentComplex apartmentComplex);
    void save(Rentable rentable);
    void load(Rentable rentable);
}
