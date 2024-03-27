package de.dhbw.domain.miscellaneous;

import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.ids.RentableId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.time.LocalDate;
import java.util.List;

public interface Rental {
    void remodel(double size, int maxTenants);
    void setMaxTenants(int maxTenants);
    LeaseAgreement getRentalAgreement();
    void rentToTenant(List<Tenant> tenants, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment);
    double getSize();
    RentableId getId();
    List<TenantId> getTenantIds();
    LeaseAgreement GetRentalAgreement();
}
