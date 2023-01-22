package core.items;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public final class PriceFormat {

    private PriceFormat() {
        // not called
    }

    /**
     * constant for thousand.
     */
    private static final Double THOUSAND = 1000.0;
    /**
     * constant for million.
     */
    private static final Double MILLION = 1000000.0;
    /**
     * constant for billion.
     */
    private static final Double BILLION = 1000000000.0;

    /**
     * Formats large Double values to a more generic and
     * readable value for use in
     * GUI.
     *
     * @param value value that is to be converted
     * @return converted value as string
     */

    public static String numberFormat(final Double value) {
        StringBuilder builder = new StringBuilder();

        if (value < MILLION && value > -MILLION) {
            builder.append("$"
                    + Double.toString(round(value / THOUSAND, 2)) + "k");
        } else if (value < BILLION && value > -BILLION) {
            builder.append("$"
                    + Double.toString(round(value / MILLION, 2)) + "m");
        } else {
            builder.append("$"
                    + Double.toString(round(value / BILLION, 2)) + "b");
        }
        return builder.toString();
    }

    /**
     * Formats small to large double values into a price format.
     *
     * @param value value that is to be formatted
     * @return the formatted value as a string
     */
    public static String priceNumberFormat(final Double value) {
        StringBuilder builder = new StringBuilder();
        if (value < 1.0 && value > -1.0) {
            builder.append("$" + String.format("%.1f",
                    value).replace(",", "."));
        } else if (value < THOUSAND && value > -THOUSAND) {
            builder.append("$" + Double.toString(round(value, 2)));
        } else {
            NumberFormat nf = NumberFormat.getInstance(Locale.US);
            builder.append("$" + nf.format(round(value, 2)));
        }
        return builder.toString();
    }

    /**
     * Rounds a number to a given amount of decimal places.
     *
     * Taken from a forum post at stackoverflow:
     * https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     *
     * @param value  value that is to be rounded
     * @param places number of decimal places the value is to be rounded to
     * @return the double value that was rounded.
     */
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
