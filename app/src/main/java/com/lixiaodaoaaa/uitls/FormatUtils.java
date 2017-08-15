package com.lixiaodaoaaa.uitls;

import java.text.DecimalFormat;

/**
 * Created by lixiaodaoaaa on 2017/8/15.
 */

public class FormatUtils {

    public static String formatPrice(float price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(price);
    }
}
