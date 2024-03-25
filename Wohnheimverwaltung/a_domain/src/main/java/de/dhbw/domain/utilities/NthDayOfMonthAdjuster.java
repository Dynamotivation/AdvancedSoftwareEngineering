package de.dhbw.domain.utilities;

import java.time.LocalDate;
import java.time.temporal.*;

public class NthDayOfMonthAdjuster implements TemporalAdjuster {
    private int nthDay;

    public NthDayOfMonthAdjuster(int nthDay) {
        if (nthDay <= 0 || nthDay > 31) {
            throw new IllegalArgumentException("Invalid day of month");
        }
        this.nthDay = nthDay;
    }

    public static TemporalAdjuster nthDayOfMonth(int nthDay) {
        return new NthDayOfMonthAdjuster(nthDay);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        int month = temporal.get(ChronoField.MONTH_OF_YEAR);
        int year = temporal.get(ChronoField.YEAR);

        // Account for shorter months
        int lastDayOfMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        int dayOfMonth = Math.min(nthDay, lastDayOfMonth);

        return temporal.with(ChronoField.DAY_OF_MONTH, dayOfMonth);
    }
}