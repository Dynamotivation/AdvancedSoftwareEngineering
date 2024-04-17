package de.dhbw.domain.services;

import de.dhbw.domain.entities.LeaseAgreement;
import de.dhbw.domain.entities.RentCharge;
import de.dhbw.domain.miscellaneous.NthDayOfMonthAdjuster;
import de.dhbw.domain.miscellaneous.RentCharger;

import java.time.LocalDate;

public class DefaultRentCharger implements RentCharger {
    @Override
    public LocalDate chargeRent(LeaseAgreement leaseAgreement) {
        NthDayOfMonthAdjuster nthDayOfMonthAdjuster = leaseAgreement.getMonthlyDayOfPayment();
        LocalDate nextPaymentDate = leaseAgreement.getNextPaymentDate();

        while (nextPaymentDate.isBefore(LocalDate.now())) {
            final LocalDate finalNextPaymentDate = nextPaymentDate;
            leaseAgreement.getTenants().forEach(tenant ->
                    tenant.getCharged(leaseAgreement, new RentCharge(-leaseAgreement.getRent().getAmount(),
                            finalNextPaymentDate, leaseAgreement.getId())));

            nextPaymentDate = finalNextPaymentDate.plusMonths(1).with(nthDayOfMonthAdjuster);
        }

        return nextPaymentDate;
    }
}
