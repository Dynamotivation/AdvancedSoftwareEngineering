package de.dhbw.application.services;

import de.dhbw.application.snapshotObjects.TenantSnapshotDTO;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.miscellaneous.ContactAvenue;
import de.dhbw.domain.repositories.TenantRepository;
import de.dhbw.domain.valueObjects.ids.TenantId;

public class TenantManagementService {
    private final TenantRepository tenantRepository;

    public TenantManagementService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public TenantId createTenant(String name, String surname, ContactAvenue contactAvenue) {
        Tenant tenant = new Tenant(name, surname, contactAvenue);
        tenantRepository.add(tenant);

        return tenant.getId();
    }

    public void addContactAvenueToTenant(TenantId tenantId, ContactAvenue contactAvenue) {
        Tenant tenant = tenantRepository.findById(tenantId);
        tenant.addContactAvenue(contactAvenue);
    }

    public int getOutstandingBalanceOfTenant(TenantId tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId);
        return tenant.getBalance();
    }

    public TenantSnapshotDTO getTenantSnapshotById(TenantId tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId);

        return new TenantSnapshotDTO(tenant);
    }

    public void loadTenants() {
        tenantRepository.load();
    }

    public void clearAllTenants() {
        for (Tenant tenant : tenantRepository.listAll()) {
            tenantRepository.remove(tenant);
        }
    }
}
