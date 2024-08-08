package com.github.fengye.starring.uranium.utils.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {
    public static double keepDecimalPoint(double num,int places) {
        BigDecimal bigDecimal = new BigDecimal(num);
        return bigDecimal.setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
}
