package com.fixthewall.game;

import java.util.Locale;

public class Helpers {

    public static int steps = 0;

    public static String formatBigNumbers(double n) {
        if (n <= 0)
            return "0";
        int magnitude = (int) Math.floor(Math.log(n) / Math.log(1000));
        if (magnitude < 0) // magnitude = -1 en fin de vie.
            return "0";
        double highdigits = n / Math.pow(1000, magnitude);
        String r;
        if (magnitude < 1)
            r = String.format(Locale.getDefault(),"%d", (int) highdigits);
        else
            r = String.format(Locale.getDefault(),"%.3f", highdigits);
        return r + getSuffix(magnitude);
    }

    private static String getSuffix(int magnitude) {
        int nChars = magnitude / 26 + 1;
        String r = "";
        for (int i = 0; i < nChars; i++)
            r += (char) (magnitude % 26 + 65);
        return r;
    }

    /**
     * Retourne un double aléatoire entre 0 et max-1
     */
    public static double getRandom(int max){
        return Math.random() * max;
    }

    /**
     * Retourne un double aléatoire entre min et max-1
     */
    public static double getRandom(int min, int max){
        return Math.random() * (max - min) + min;
    }

}
