package de.dhbw.domain.repositories;

import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.miscellaneous.Rental;

import java.util.List;
import java.util.UUID;

public interface RentalRepository {
    List<Rental> listAll();
    Rental findById(UUID id);
    List<Rental> findByTenant(Tenant tenant);
    List<Rental> findByRentalAgreement(LeaseAgreement leaseAgreement);
    List<Rental> findByApartmentComplex(ApartmentComplex apartmentComplex);
    void save(Rental rental);
    void load(Rental rental);
}
