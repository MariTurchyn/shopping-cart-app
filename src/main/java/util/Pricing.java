package util;

public final class Pricing {
    private Pricing() {}

    public static final double TAX_RATE = 0.17;

    public static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    public static double tax(double subtotal) {
        return round2(subtotal * TAX_RATE);
    }

    public static double total(double subtotal) {
        return round2(subtotal + tax(subtotal));
    }
}
