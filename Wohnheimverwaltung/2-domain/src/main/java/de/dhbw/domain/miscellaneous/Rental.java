package de.dhbw.domain.miscellaneous;

import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.time.LocalDate;
import java.util.List;

public interface Rental {
    RentalId getId();
    LeaseAgreement getLeaseAgreement();
    int getMaxTenants();
    double getSize();
    void remodel(double size, int maxTenants);
    void rentToTenants(List<Tenant> tenants, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment);
}
