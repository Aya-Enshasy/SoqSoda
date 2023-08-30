package com.lira_turkish.dollarstocks.utils.formatter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class DecimalFormatterManager {

    public static NumberFormat formatter;

    public static NumberFormat getFormatterBalanceWithRound() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        formatter = new DecimalFormat("0", symbols);
        return formatter;
    }

    public static NumberFormat getFormatterBalance() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        formatter = new DecimalFormat("#0.00", symbols);
        return formatter;
    }

    public static NumberFormat getFormatterPercentage() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        formatter = new DecimalFormat("#0.0000", symbols);
        return formatter;
    }

    public static NumberFormat getFormatterTime() {
        if (formatter == null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
            formatter = new DecimalFormat("#", symbols);
        }
        return formatter;
    }
}