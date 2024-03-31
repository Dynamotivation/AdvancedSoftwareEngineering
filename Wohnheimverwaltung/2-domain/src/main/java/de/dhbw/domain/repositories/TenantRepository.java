package de.dhbw.domain.repositories;

import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.RentalId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.util.List;

public interface TenantRepository {
    List<Tenant> listAll();
    Tenant findById(TenantId tenantId);
    List<Tenant> findByRentalId(RentalId rentalId);
    List<Tenant> findByLeaseAgreementId(LeaseAgreementId leaseAgreementId);
    void add(Tenant tenant);
    void save(Tenant tenant);
    void load(Tenant tenant);
}
