package de.dhbw.domain.miscellaneous;

import de.dhbw.domain.entities.LeaseAgreement;

import java.time.LocalDate;

public interface RentCharger {
    LocalDate chargeRent(LeaseAgreement leaseAgreement);
}
