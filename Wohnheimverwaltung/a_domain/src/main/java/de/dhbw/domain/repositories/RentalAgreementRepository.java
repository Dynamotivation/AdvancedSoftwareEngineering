package de.dhbw.domain.repositories;

import de.dhbw.domain.aggregates.RentalAgreement;

public interface RentalAgreementRepository {
    RentalAgreement findById(Long id);
    void save(RentalAgreement rentalAgreement);
    void load(RentalAgreement rentalAgreement);
}
