package de.dhbw.domain.repositories;

import de.dhbw.domain.aggregates.RentalAgreement;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.entities.Tenant;
import de.dhbw.domain.utilities.Rentable;

import java.util.List;
import java.util.UUID;

public interface RentalAgreementRepository {
    List<RentalAgreement> listAll();
    RentalAgreement findById(UUID id);
    List<RentalAgreement> findByTenant(Tenant tenant);
    List<RentalAgreement> findByRentable(Rentable rentable);
    List<RentalAgreement> findByApartmentComplex(ApartmentComplex apartmentComplex);
    void save(RentalAgreement rentalAgreement);
    void load(RentalAgreement rentalAgreement);
}
