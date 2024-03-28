package de.dhbw.domain.entities;

import de.dhbw.domain.miscellaneous.Transaction;

import java.time.LocalDate;

public class RentCharge implements Transaction {
    // Not to be confused with Rent. While Rent is cost associated with an apartment, RentCharge is debt associated with a tenant.
    // A Tenant can miss multiple rent payments. While his balance diminishes, the IDENTITY of RentCharges will be different.
    private final int amount;
    private final LocalDate dueDate;

    public RentCharge(int amount, LocalDate dueDate) {
        if (amount >= 0)
            throw new IllegalArgumentException("Invalid amount");

        this.amount = amount;
        this.dueDate = dueDate;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public int getDate() {
        return 0;
    }
}
