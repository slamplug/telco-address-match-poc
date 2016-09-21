package com.firstutility.telco.tp.market.determiner.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstutility.telco.addressmatching.api.TelcoAddressQualifier;
import com.firstutility.telco.addressmatching.api.TelcoAvailableAddress;
import com.firstutility.telco.tp.market.determiner.domain.Address;
import com.firstutility.telco.tp.market.determiner.service.addressmatch.AddressMatchingService;
import com.firstutility.telco.tp.market.determiner.service.addressmatch.AddressMatchingServiceImpl;
import com.firstutility.util.ResourceLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static com.firstutility.telco.addressmatching.api.builder.TelcoAvailableAddressBuilder.aAvailableAddress;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddressMatchingServiceTest {

    private final ResourceLoader resourceLoader = new ResourceLoader();

    private AddressMatchingService addressMatchingService;

    private ObjectMapper mapper;

    @Before
    public void beforeEachTest() {
        addressMatchingService = new AddressMatchingServiceImpl();
        mapper = new ObjectMapper();
    }

    @Test
    public void testGetMatchingTelcoAvailableAddressNullAvailableLists() {

        final Address address = new Address("39","St. Gowan Ave.","","Cardiff","","CF144JX");

        final Optional<TelcoAvailableAddress> optional = addressMatchingService.getMatchingTelcoAvailableAddress(null, address);

        assertFalse(optional.isPresent());
    }

    @Test
    public void matchingTelcoAgainstAvailableAddressListOfAvailableAddressesForCF144JXShouldFindMatch() throws Exception {

        final Address address = new Address("39","St. Gowan Ave.","","Cardiff","","CF144JX");

        final TelcoAvailableAddress expectedMatch = aAvailableAddress()
                .withBuildingNumber("39")
                .withStreet("St. Gowan Avenue")
                .withPostTown("Cardiff")
                .withCounty("South Glamorgan")
                .withPostCode("CF14 4JX")
                .withAddressQualifier(TelcoAddressQualifier.GOLD)
                .withAlk("A00016555194")
                .withCssDistrictCode("SW")
                .withExchangeCode("WXC")
                .withPostcodeValid(true)
                .build();

        final String json = resourceLoader.getResourceAsString("/telco-available-addresses-CF144JX.json");

        final TelcoAvailableAddress[] telcoAvailableAddresses = mapper.readValue(json, TelcoAvailableAddress[].class);

        final Optional<TelcoAvailableAddress> optional = addressMatchingService.getMatchingTelcoAvailableAddress(
                asList(telcoAvailableAddresses), address);

        assertTrue(optional.isPresent());
        assertEquals(expectedMatch, optional.get());
    }

    @Test
    public void matchingTelcoAgainstAvailableAddressListOfAvailableAddressesForBS217XRShouldNotFindMatch() throws Exception {

        final Address address = new Address("144","Old Church Lane","","Clevedon","Avon","BS217XR");

        final String json = resourceLoader.getResourceAsString("/telco-available-addresses-BS217XR.json");

        final TelcoAvailableAddress[] telcoAvailableAddresses = mapper.readValue(json, TelcoAvailableAddress[].class);

        final Optional<TelcoAvailableAddress> optional = addressMatchingService.getMatchingTelcoAvailableAddress(
                asList(telcoAvailableAddresses), address);

        assertFalse(optional.isPresent());
    }


    @Test
    public void matchingTelcoAgainstAvailableAddressListOfAvailableAddressesForCV227NNShouldFindMatch() throws Exception {

        final Address address = new Address("8","Dalkieth Avenue","","Bilton","Rugby","CV227NN");

        final TelcoAvailableAddress expectedMatch = aAvailableAddress()
                .withBuildingNumber("8")
                .withStreet("Dalkeith Avenue")
                .withPostTown("Rugby")
                .withCounty("Warwickshire")
                .withPostCode("CV22 7NN")
                .withAddressQualifier(TelcoAddressQualifier.GOLD)
                .withAlk("A00019438213")
                .withCssDistrictCode("CM")
                .withExchangeCode("DUN")
                .withPostcodeValid(true)
                .build();

        final String json = resourceLoader.getResourceAsString("/telco-available-addresses-CV227NN.json");

        final TelcoAvailableAddress[] telcoAvailableAddresses = mapper.readValue(json, TelcoAvailableAddress[].class);

        final Optional<TelcoAvailableAddress> optional = addressMatchingService.getMatchingTelcoAvailableAddress(
                asList(telcoAvailableAddresses), address);

        assertTrue(optional.isPresent());
        assertEquals(expectedMatch, optional.get());
    }
}