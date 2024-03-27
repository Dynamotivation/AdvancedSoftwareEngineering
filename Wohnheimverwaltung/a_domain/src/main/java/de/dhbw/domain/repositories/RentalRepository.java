package de.dhbw.domain.repositories;

import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.miscellaneous.Rental;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.RentalId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.util.List;
import java.util.UUID;

public interface RentalRepository {
    List<Rental> listAll();
    Rental findById(RentalId rentalId);
    List<Rental> findByTenantId(TenantId tenantId);
    List<Rental> findByRentalAgreementId(LeaseAgreementId leaseAgreementId);
    List<Rental> findByApartmentComplexId(ApartmentComplexId apartmentComplexId);
    void save(Rental rental);
    void load(Rental rental);
}
