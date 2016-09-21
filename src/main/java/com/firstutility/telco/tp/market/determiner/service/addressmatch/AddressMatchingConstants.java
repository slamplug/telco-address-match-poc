package com.firstutility.telco.tp.market.determiner.service.addressmatch;

import java.util.HashMap;
import java.util.Map;

public class AddressMatchingConstants {

    public static final String[] STREET_DESCRIPTORS =
            new String[] { "alley", "approach", "avenue", "boulevard", "close", "court", "crescent", "drive",
                    "garden", "gardens", "grove", "lane", "place", "road", "street", "terrace", "way", "walk"};

    public static final String ANY_WHITESPACE = "\\s+";

    public static final String[] NUMBERS = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    public static final String[] ALHPABETIC =
            new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                    "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                    "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
    public static final String STRIP_NUMBERS = "0123456789";
    public static final String STRIP_ALPHABETIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    // all variants of street types
    public static final Map<String, String[]> STREET_VARIANTS = new HashMap<String, String[]>() {{
        put("alley", new String[] { "aly" });
        put("avenue", new String[] { "av", "ave" });
        put("boulevard", new String[] { "blvd" });
        put("close", new String[] { "cl" });
        put("court", new String[] { "crt", "ct" });
        put("crescent", new String[] { "cres" });
        put("drive", new String[] { "dr" });
        put("garden", new String[] { "gdn" });
        put("gardens", new String[] { "gdns" });
        put("grove", new String[] { "grov" });
        put("lane", new String[] { "ln" });
        put("road", new String[] { "rd" });
        put("square", new String[] { "sq" });
        put("street", new String[] { "st" });
    }};
}
