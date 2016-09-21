package com.firstutility.telco.tp.market.determiner.service.addressmatch;

import com.firstutility.telco.addressmatching.api.TelcoAvailableAddress;
import com.firstutility.telco.tp.market.determiner.domain.Address;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.addAll;
import static org.apache.commons.lang.StringUtils.*;
import static org.apache.commons.lang3.StringUtils.getLevenshteinDistance;
import static org.apache.commons.lang3.StringUtils.isEmpty;


import static org.jooq.tools.StringUtils.replace;

public class AddressMatchingUtils {

    public static boolean matchStringsWithSpellingTollerance(final String str1, final String str2) {
        return matchStringsWithSpellingTollerance(str1, str2, 1);
    }

    public static boolean matchStringsWithSpellingTollerance(final String str1, final String str2, final int tolerance) {
        return getLevenshteinDistance(str1.toLowerCase(), str2.toLowerCase()) <= tolerance;
    }

    public static Address cleanAddressForMatching(final Address address) {
        return new Address(cleanAddressLine(address.getAddressLine1()),
                cleanAddressLine(address.getAddressLine2()),
                cleanAddressLine(address.getAddressLine3()),
                cleanAddressLine(address.getAddressLine4()),
                cleanAddressLine(address.getAddressLine5()),
                address.getPostCode());
    }

    public static TelcoAvailableAddress cleanAddressForMatching(final TelcoAvailableAddress address) {
        final TelcoAvailableAddress telcoAvailableAddress = new TelcoAvailableAddress();
        telcoAvailableAddress.setOrganisationName(address.getOrganisationName());
        telcoAvailableAddress.setSubBuilding(address.getSubBuilding());
        telcoAvailableAddress.setBuildingName(address.getBuildingName());
        telcoAvailableAddress.setBuildingNumber(address.getBuildingNumber());
        telcoAvailableAddress.setStreet(cleanAddressLine(address.getStreet()));
        telcoAvailableAddress.setDependantThoroughfare(address.getDependantThoroughfare());
        telcoAvailableAddress.setLocality(address.getLocality());
        telcoAvailableAddress.setPostTown(address.getPostTown());
        telcoAvailableAddress.setCounty(address.getCounty());
        telcoAvailableAddress.setPostCode(address.getPostCode());
        telcoAvailableAddress.setAddressQualifier(address.getAddressQualifier());
        telcoAvailableAddress.setAlk(address.getAlk());
        telcoAvailableAddress.setExchangeCode(address.getExchangeCode());
        telcoAvailableAddress.setCssDistrictCode(address.getCssDistrictCode());
        telcoAvailableAddress.setPostcodeValid(address.isPostcodeValid());
        return telcoAvailableAddress;
    }

    private static String cleanAddressLine(final String addressLine) {
        return correctCommonSpellingMistakes(
                replaceStreetVariantsInStringWithFullValue(
                        removeAllButAlaphaNumericAndWhitespace(addressLine)));
    }

    private static String correctCommonSpellingMistakes(final String addressLine) {
        return isEmpty(addressLine) ? addressLine : addressLine.replaceAll("ei", "ie");
    }


    public static String replaceStreetVariantsInStringWithFullValue(final String addressLine) {
        String tmp = addressLine;
        for (final String key : AddressMatchingConstants.STREET_VARIANTS.keySet()) {
            final String[] variants = AddressMatchingConstants.STREET_VARIANTS.get(key);
            for (final String variant: variants) {
                if (stringEndsWithUniqueWord(tmp, variant)) {
                    tmp = replaceLastInstanceOfWord(tmp, variant, key);
                }
            }
        }
        return tmp;
    }

    public static String replaceUniqueWordsInString(final String toSearch, final String toReplace, final String replaceWith) {
        String newString = toSearch;
        //@formatter:off
        if (stringStartsWithUniqueWord(newString, toReplace)) { newString = replaceWith + toSearch.substring(toReplace.length()); }
        if (stringContainsUniqueWord(newString, toReplace)) { newString = newString.replaceAll(" " + toReplace + " ", " " + replaceWith + " "); }
        if (stringEndsWithUniqueWord(newString, toReplace)) { newString = replaceLastInstanceOfWord(newString, toReplace, replaceWith); }
        //@formatter:off
        return newString;
    }

    private static String replaceLastInstanceOfWord(final String str, final String toReplace, final String replaceWith) {
        return str.substring(0, str.length() - toReplace.length()) + replaceWith;
    }


    public static boolean stringStartsWithUniqueWord(final String toSearch, final String toMatch) {
        if (isEmpty(toMatch) || isEmpty(toSearch)) return false;
        return toSearch.toLowerCase().matches("^" + toMatch.toLowerCase() + "\\s.*");
    }

    public static boolean stringEndsWithUniqueWord(final String toSearch, final String toMatch) {
        if (isEmpty(toMatch) || isEmpty(toSearch)) return false;
        return toSearch.toLowerCase().matches(".*\\s" + toMatch.toLowerCase() + "$");
    }

    public static boolean stringContainsUniqueWord(final String toSearch, final String toMatch) {
        if (isEmpty(toMatch) || isEmpty(toSearch)) return false;
        return toSearch.toLowerCase().matches(".*\\s" + toMatch.toLowerCase() + "\\s.*");
    }

    public static String removeAllButAlaphaNumericAndWhitespace(final String in) {
        return isEmpty(in) ? in : in.replaceAll("[^A-Za-z0-9\\s]", "");
    }

    public static List<String> buildParts(final Address address) {
        final List<String> parts = new ArrayList<>();
        addParts(address.getAddressLine1(), parts);
        addParts(address.getAddressLine2(), parts);
        addParts(address.getAddressLine3(), parts);
        addParts(address.getAddressLine4(), parts);
        addParts(address.getAddressLine5(), parts);
        addParts(replace(address.getPostCode(), " ", ""), parts);
        return parts;
    }

    private static void addParts(final String toadd, final List<String> parts) {
        if (toadd != null && !toadd.equals("-")) {
            for (final String part : splitStringBySpace(toadd)) {
                addPart(part, parts);
            }
        }
    }

    private static void addPart(final String toadd, final List<String> parts) {
        final String[] elements = splitOutNumbers(toadd);

        String[] elements2 = new String[2];

        if (elements[1] != null) { elements2 = splitOutDescriptors(elements[1]); }

        final List<String> list = new ArrayList<>();
        addAll(list, elements);
        addAll(list, elements2);

        for (final String element : list) {
            if (isNotBlank(element))
                parts.add(element.toUpperCase());
        }
    }

    private static String[] splitStringBySpace(final String in) { return in.split(AddressMatchingConstants.ANY_WHITESPACE); }

    private static String[] splitOutNumbers(final String s) {

        final String[] result = new String[2];
        result[0] = s;

        if (startsWithAny(s, AddressMatchingConstants.NUMBERS)) {
            result[0] = stripEnd(s, AddressMatchingConstants.STRIP_ALPHABETIC);
            if (endsWithAny(s, AddressMatchingConstants.ALHPABETIC)) {
                result[1] = stripStart(s, AddressMatchingConstants.STRIP_NUMBERS);
            }
        }
        return result;
    }

    private static String[] splitOutDescriptors(final String s) {
        final String[] result = new String[2];
        result[0] = s;

        if (s == null) { return result; }
        if (endsWithAny(s.toLowerCase(), AddressMatchingConstants.STREET_DESCRIPTORS)) {
            result[0] = stripEnd(s.toLowerCase(), resolveDescriptor(s));
            result[1] = resolveDescriptor(s);
        }
        return result;
    }

    private static String resolveDescriptor(final String part) {
        for (final String descriptor : AddressMatchingConstants.STREET_DESCRIPTORS) {
            if (part.toLowerCase().contains(descriptor))
                return descriptor;
        }
        return "";
    }

    public static boolean isEmptyList(final Collection<?> objects) { return objects == null || objects.isEmpty(); }

    public static boolean hasSame(final Address address, final String toMatch, final List<String> parts) {
        if (toMatch == null) { return false; }
        if (toMatch.split(AddressMatchingConstants.ANY_WHITESPACE).length > 1) { return hasSame(address, toMatch.split(AddressMatchingConstants.ANY_WHITESPACE), parts); }
        return hasPart(toMatch, parts);
    }

    private static boolean hasSame(final Address address, final String[] nameParts, final List<String> parts) {
        int i = nameParts.length;
        for (final String part : nameParts) {
            if (hasSame(address, part, parts)) { i--; }
        }
        return i == 0;
    }

    private static boolean hasPart(final String toMatch, final List<String> parts) {
        if (toMatch == null) { return false; }
        for (final String part : parts) {
            if (isNumeric(toMatch)) {
                if (isNumeric(part) && Integer.valueOf(toMatch).equals(Integer.valueOf(part))) { return true; }
            } else {
                if (matchStringsWithSpellingTollerance(toMatch, part)) { return true; }
            }
        }
        return false;
    }
}
