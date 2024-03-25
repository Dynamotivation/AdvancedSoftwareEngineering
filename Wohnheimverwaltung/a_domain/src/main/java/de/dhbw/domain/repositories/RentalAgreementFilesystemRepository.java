package de.dhbw.domain.repositories;

import de.dhbw.domain.aggregates.RentalAgreement;

public class RentalAgreementFilesystemRepository implements RentalAgreementRepository {
    @Override
    public RentalAgreement findById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(RentalAgreement rentalAgreement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void load(RentalAgreement rentalAgreement) {
        throw new UnsupportedOperationException();
    }
}
