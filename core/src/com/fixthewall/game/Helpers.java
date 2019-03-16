package com.fixthewall.game;

import java.util.Locale;

public class Helpers {

    public static String[] suffixes = {
            "",
            "K",
            "M",
            "G",
            "AA",
            "BB",
            "CC",
            "DD",
            "EE",
            "FF",
            "GG",
            "HH",
            "II",
            "JJ",
            "KK",
            "LL",
            "MM",
            "NN",
            "OO",
            "PP",
            "QQ",
            "RR",
            "SS",
            "TT",
            "UU",
            "VV",
            "WW",
            "XX",
            "YY",
            "ZZ"
    };

    public static String formatBigNumbers(double n) {
        if (n <= 0)
            return "0";
        int magnitude = (int) Math.floor(Math.log(n) / Math.log(1000));
        double highdigits = n / Math.pow(1000, magnitude);
        String r;
        if (magnitude < 1)
            r = String.format(Locale.getDefault(),"%d", (int) highdigits);
        else
            r = String.format(Locale.getDefault(),"%.3f", highdigits);
        if (magnitude < 0)//magnitude = -1 en fin de vie.
            return "0";
        return r += suffixes[magnitude];
    }

}
