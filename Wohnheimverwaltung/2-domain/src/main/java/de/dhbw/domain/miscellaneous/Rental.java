package de.dhbw.domain.miscellaneous;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.aggregateRoots.RentalProperty;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.Size;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.time.LocalDate;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RentalProperty.class, name = "rentalProperty"),
        @JsonSubTypes.Type(value = RentalApartmentUnit.class, name = "rentalApartmentUnit")
})
public interface Rental {
    RentalId getId();

    LeaseAgreement getLeaseAgreement();

    int getMaxTenants();

    Size getSize();

    void remodel(Size size, int maxTenants);

    void rentToTenants(List<Tenant> tenants, LocalDate inclusiveStartDate, Rent rent, int monthlyDayOfPayment, int monthsOfNotice);

    void endLeaseAgreement(LocalDate submissionDate, LocalDate endDate);

    void update();
}
