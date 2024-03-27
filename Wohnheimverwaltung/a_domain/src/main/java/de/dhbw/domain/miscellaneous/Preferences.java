package de.dhbw.domain.miscellaneous;

public class Preferences {
    // The user / landlords preferences
    private static int acceptableDebt = 0;
    private static int paymentGracePeriod = 0;

    public static int getAcceptableDebt() {
        return acceptableDebt;
    }

    public static int getPaymentGracePeriod() {
        return paymentGracePeriod;
    }

    public static void setAcceptableDebt(int acceptableDebt) {
        if (acceptableDebt < 0)
            throw new IllegalArgumentException("Acceptable debt must be greater than or equal to 0");

        Preferences.acceptableDebt = acceptableDebt;
    }

    public static void setPaymentGracePeriod(int paymentGracePeriod) {
        if (paymentGracePeriod < 0)
            throw new IllegalArgumentException("Payment grace period must be greater than or equal to 0");

        Preferences.paymentGracePeriod = paymentGracePeriod;
    }
}
