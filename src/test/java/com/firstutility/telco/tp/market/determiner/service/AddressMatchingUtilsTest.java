package com.firstutility.telco.tp.market.determiner.service;

import com.firstutility.telco.tp.market.determiner.domain.Address;
import org.junit.Test;

import java.util.List;

import static com.firstutility.telco.tp.market.determiner.service.addressmatch.AddressMatchingUtils.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddressMatchingUtilsTest {

    @Test
    public void testTwoStringsWithSingleDifferenceMatch() {
        assertTrue(matchStringsWithSpellingTollerance("1 Bob Street", "1 Bod Street"));
    }

    @Test
    public void testTwoStringsWithTwoDifferencesDontMatch() {
        assertFalse(matchStringsWithSpellingTollerance("1 Bob Street", "1 Bad Street"));
    }

    @Test
    public void testMatchIfStringStartsWithWord() {
        assertTrue(stringStartsWithUniqueWord("ave road", "ave"));
    }

    @Test
    public void testStringStartWithWordMatchIfStringStartsWithWordButNoFollowingSpace() {
        assertFalse(stringStartsWithUniqueWord("averoad", "ave"));
    }

    @Test
    public void testStringStartWithWordNoMatchIfStringDoesntStartsWithWord() {
        assertFalse(stringStartsWithUniqueWord("big ave road", "ave"));
    }

    @Test
    public void testStringStartWithWordNoMatchIfEndsWithWord() {
        assertFalse(stringStartsWithUniqueWord("big ave", "ave"));
    }

    @Test
    public void testMatchIfStringEndsWithWord() {
        assertTrue(stringEndsWithUniqueWord("big ave", "ave"));
    }

    @Test
    public void testStringEndsWithWordMatchIfStringStartsWithWordButNoFollowingSpace() {
        assertFalse(stringEndsWithUniqueWord("bigave", "ave"));
    }

    @Test
    public void testStringEndsWithWordNoMatchIfStringDoesntStartsWithWord() {
        assertFalse(stringEndsWithUniqueWord("big ave road", "ave"));
    }

    @Test
    public void testStringEndsWithWordNoMatchIfEndsWithWord() {
        assertFalse(stringEndsWithUniqueWord("ave big", "ave"));
    }

    @Test
    public void testMatchIfStringContainsWord() {
        assertTrue(stringContainsUniqueWord("big ave big", "ave"));
    }

    @Test
    public void testStringContainsWordMatchIfStringContainsWordButNoFollowingSpace() {
        assertFalse(stringContainsUniqueWord("bigavebig", "ave"));
    }

    @Test
    public void testStringConatinsWordNoMatchIfStringStartsWithWord() {
        assertFalse(stringContainsUniqueWord("ave road", "ave"));
    }

    @Test
    public void testStringConatinsWordNoMatchIfStringEndsWithWord() {
        assertFalse(stringContainsUniqueWord("big ave", "ave"));
    }

    @Test
    public void testReplaceUniqueWordsInString() {
        assertEquals("avenue aaveb bave avec avenue bob dan avenue",
                replaceUniqueWordsInString("ave aaveb bave avec ave bob dan ave", "ave", "avenue"));
    }

    @Test
    public void testReplaceStreetVariantsInStringWithFullValue() {
        assertEquals("Acacia avenue", replaceStreetVariantsInStringWithFullValue("Acacia ave"));
    }

    @Test
    public void testleanAddressForMatchingInludingSteetVariantsAndPunctuation() {
        assertEquals(new Address("12", "acacia avenue", "peter street", "st pauls square", "sometown", "B123H"),
                cleanAddressForMatching(new Address("12", "acacia ave", "peter st", "st. paul's sq", "sometown", "B123H")));
    }

    @Test
    public void testRemoveAllButAlphaNumericAndWhiteSpaceFromString() {
        assertEquals("st peters sq", removeAllButAlaphaNumericAndWhitespace("st. peter's sq"));
    }

    @Test
    public void testBuildParts() {
        final String[] expected = new String[] { "CF144JX", "39" ,"ST.", "GOWAN", "AVE.","CARDIFF" };

        final Address address = new Address("39","St. Gowan Ave.","","Cardiff","","CF144JX");

        final List<String> actual = buildParts(address);

        assertThat(asList(expected), containsInAnyOrder(actual.toArray()));
    }

}
