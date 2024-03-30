package de.dhbw.domain.miscellaneous;

import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;

import java.time.LocalDate;

public interface Transaction {
    int getAmount();
    LocalDate getDate();
}
