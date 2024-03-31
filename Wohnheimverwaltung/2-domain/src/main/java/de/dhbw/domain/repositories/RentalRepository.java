package de.dhbw.domain.repositories;

import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.aggregateRoots.RentalProperty;
import de.dhbw.domain.miscellaneous.Rental;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.RentalId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.util.List;

public interface RentalRepository {
    List<Rental> listAll();
    Rental findById(RentalId rentalId);
    List<Rental> findByTenantId(TenantId tenantId);
    List<Rental> findByLeaseAgreementId(LeaseAgreementId leaseAgreementId);
    List<Rental> findByApartmentComplexId(ApartmentComplexId apartmentComplexId);
    List<RentalApartmentUnit> listAllRentalApartmentUnits();
    List<RentalProperty> listAllRentalProperties();
    void add(Rental rental);
    void save(Rental rental);
    void load(Rental rental);
}
