package de.dhbw.domain.utilities;

import de.dhbw.domain.entities.Tenant;
import de.dhbw.domain.valueObjects.Rent;

import java.util.List;

public interface Rentable {
    void rentTo(Tenant tenant);

    void remodel(double size, int maxTenants);

    void setMaxTenants(int maxTenants);

    void setSize(double size);

    Rent getRent();
    void setRent(Rent rent);
    double getSize();
    List<Tenant> getTenants();
}
