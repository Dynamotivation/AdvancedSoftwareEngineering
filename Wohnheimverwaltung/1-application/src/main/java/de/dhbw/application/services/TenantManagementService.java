package de.dhbw.application.services;

import de.dhbw.application.snapshotObjects.TenantSnapshotDTO;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.entities.Deposit;
import de.dhbw.domain.miscellaneous.ContactAvenue;
import de.dhbw.domain.repositories.TenantRepository;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.time.LocalDate;
import java.util.List;

public class TenantManagementService {
    private final TenantRepository tenantRepository;

    public TenantManagementService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public TenantSnapshotDTO getTenantById(TenantId tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId);
        return new TenantSnapshotDTO(tenant);
    }

    public List<TenantSnapshotDTO> getAllTenants() {
        return tenantRepository.listAll().stream().map(TenantSnapshotDTO::new).toList();
    }

    public TenantId createTenant(String name, String surname, ContactAvenue contactAvenue) {
        Tenant tenant = new Tenant(name, surname, contactAvenue);
        tenantRepository.add(tenant);

        return tenant.getId();
    }

    public void creditTenant(TenantId tenantId, int amount, LocalDate creditDate) {
        Tenant tenant = tenantRepository.findById(tenantId);
        tenant.getCredited(new Deposit(amount, creditDate));
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

    public void deleteTenant(TenantId tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId);
        tenantRepository.remove(tenant);
    }

    public void saveAllOrphanTenants() {
        for (Tenant tenant : tenantRepository.listAll()) {
            if (tenant.getAssociatedLeaseAgreementIds().isEmpty()) {
                tenantRepository.save(tenant);
            }
        }
    }

    public void exportTenantById(TenantId tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId);
        tenantRepository.save(tenant);
    }

    public List<TenantId> importTenants() {
        return tenantRepository.load().stream()
                .map(Tenant::getId)
                .toList();
    }
}
