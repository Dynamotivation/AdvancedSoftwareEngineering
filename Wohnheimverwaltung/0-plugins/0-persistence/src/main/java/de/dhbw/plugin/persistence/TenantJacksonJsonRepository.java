package de.dhbw.plugin.persistence;

import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.aggregateRoots.RentalProperty;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.miscellaneous.Rental;
import de.dhbw.domain.repositories.RentalRepository;
import de.dhbw.domain.repositories.TenantRepository;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.RentalId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.util.ArrayList;
import java.util.List;

public class TenantJacksonJsonRepository implements TenantRepository {
    private final List<Tenant> tenants = new ArrayList<>();


    @Override
    public List<Tenant> listAll() {
        return new ArrayList<>(tenants);
    }

    @Override
    public Tenant findById(TenantId tenantId) {
        return tenants.stream()
                .filter(tenant -> tenant.getId().equals(tenantId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Tenant> findByRentalId(RentalId rentalId) {
        return tenants.stream()
                .filter(tenant -> tenant.getAssociatedLeaseAgreements().stream()
                        .anyMatch(leaseAgreement -> leaseAgreement.getAssociatedRentalId().equals(rentalId)))
                .toList();
    }

    @Override
    public List<Tenant> findByLeaseAgreementId(LeaseAgreementId leaseAgreementId) {
        return tenants.stream()
                .filter(tenant -> tenant.getAssociatedLeaseAgreements().stream()
                        .anyMatch(leaseAgreement -> leaseAgreement.getId().equals(leaseAgreementId)))
                .toList();
    }

    @Override
    public void add(Tenant tenant) {
        tenants.add(tenant);
    }

    @Override
    public void save(Tenant tenant) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void load(Tenant tenant) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
