package de.dhbw.domain.utilities;

import de.dhbw.domain.entities.RentalAgreement;
import de.dhbw.domain.entities.Tenant;
import de.dhbw.domain.valueObjects.Rent;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface Rentable {
    void remodel(double size, int maxTenants);
    void setMaxTenants(int maxTenants);
    RentalAgreement getRentalAgreement();
    void rentToTenant(List<Tenant> tenants, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment);
    double getSize();
    UUID getId();
    List<Tenant> getTenants();
    RentalAgreement GetRentalAgreement();
}
