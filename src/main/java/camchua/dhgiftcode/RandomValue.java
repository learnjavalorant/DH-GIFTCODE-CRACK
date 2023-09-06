/*
 * Decompiled with CFR 0.150.
 */
package camchua.dhgiftcode;

import camchua.dhgiftcode.Utils;
import java.util.concurrent.ThreadLocalRandom;

public class RandomValue {
    public double min;
    public double max;
    public double value;

    public RandomValue(String factor) {
        if (factor.contains("-")) {
            String[] factors = factor.split("-");
            this.min = Double.parseDouble(factors[0]);
            this.max = Double.parseDouble(factors[1]);
            if (this.min == this.max) {
                this.value = this.min;
            } else if (this.min > this.max) {
                double temp = this.min;
                this.min = this.max;
                this.max = temp;
            }
            this.value = Utils.fixDouble(ThreadLocalRandom.current().nextDouble(this.min, this.max));
        } else {
            this.min = this.value = Double.parseDouble(factor);
            this.max = this.value;
        }
    }

    public boolean isRandom() {
        return this.min != this.max;
    }

    public String toString() {
        if (this.isRandom()) {
            return this.min + "-" + this.max;
        }
        return String.valueOf(this.value);
    }
}

