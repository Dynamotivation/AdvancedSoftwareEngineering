package de.dhbw.domain.entities;

import de.dhbw.domain.miscellaneous.Transaction;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.TransactionId;

import java.time.LocalDate;

public class RentCharge implements Transaction {
    // Not to be confused with Rent. While Rent is cost associated with an apartment, RentCharge is debt associated with a tenant.
    // A Tenant can miss multiple rent payments. While his balance diminishes, the IDENTITY of RentCharges will be different.
    private final int amount;
    private final LocalDate chargeDate;
    private final LeaseAgreementId associatedLeaseAgreementId;
    private final TransactionId transactionId;

    public RentCharge(int amount, LocalDate chargeDate, LeaseAgreementId associatedLeaseAgreementId) {
        if (amount >= 0)
            throw new IllegalArgumentException("Invalid amount");

        this.amount = amount;
        this.chargeDate = chargeDate;
        this.associatedLeaseAgreementId = associatedLeaseAgreementId;
        this.transactionId = new TransactionId();
    }

    public LeaseAgreementId getAssociatedLeaseAgreementId() {
        return associatedLeaseAgreementId;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public LocalDate getDate() {
        return chargeDate;
    }

    @Override
    public TransactionId getTransactionId() {
        return transactionId;
    }
}
