package de.dhbw.plugin.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.repositories.TenantRepository;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.RentalId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Tenant> findByLeaseAgreementId(LeaseAgreementId leaseAgreementId) {
        return tenants.stream()
                .filter(tenant -> tenant.getAssociatedLeaseAgreementIds().stream()
                        .anyMatch(leaseAgreement -> leaseAgreement.equals(leaseAgreementId)))
                .toList();
    }

    @Override
    public void add(Tenant tenant) {
        tenants.add(tenant);
    }

    @Override
    public void remove(Tenant tenant) {
        if (!tenant.getAssociatedLeaseAgreementIds().isEmpty())
            throw new IllegalArgumentException("Tenant still has active lease agreements");

        tenants.remove(tenant);
    }

    @Override
    public void save(Tenant tenant) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (var writer = new FileWriter("tenantOrphaned.save", true)) {
            String jsonString = mapper.writeValueAsString(tenant) + "\n";

            writer.write(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tenant> load() {
        List<Tenant> newTenants = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (var reader = new BufferedReader(new FileReader("tenantOrphaned.save"))) {
            while (reader.ready()) {
                String jsonString = reader.readLine();
                Tenant tenant = mapper.readValue(jsonString, Tenant.class);
                newTenants.add(tenant);
            }

        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

        tenants.addAll(newTenants);
        return newTenants;
    }
}
