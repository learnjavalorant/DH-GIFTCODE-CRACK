/*
 * Decompiled with CFR 0.150.
 */
package camchua.dhgiftcode;

import java.text.DecimalFormat;

public final class Utils {
    private static DecimalFormat decimalFormat = new DecimalFormat("#.#");

    public static double fixDouble(double d) {
        return Double.parseDouble(decimalFormat.format(d));
    }
}

