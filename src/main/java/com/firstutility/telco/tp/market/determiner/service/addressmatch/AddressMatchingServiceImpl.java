package com.firstutility.telco.tp.market.determiner.service.addressmatch;

import com.firstutility.telco.addressmatching.api.TelcoAvailableAddress;
import com.firstutility.telco.tp.market.determiner.domain.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.firstutility.telco.tp.market.determiner.service.addressmatch.AddressMatchingUtils.hasSame;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.lang.StringUtils.isEmpty;

@Component
public class AddressMatchingServiceImpl implements AddressMatchingService {

    private final Logger LOG = LoggerFactory.getLogger(AddressMatchingServiceImpl.class);

    @Override
    public Optional<TelcoAvailableAddress> getMatchingTelcoAvailableAddress(
            final List<TelcoAvailableAddress> telcoAvailableAddressList, final Address address) {

        final Address cleanedAddress = AddressMatchingUtils.cleanAddressForMatching(address);

        Optional<TelcoAvailableAddress> maybeTelcoAvailableAddress = empty();

        if (!AddressMatchingUtils.isEmptyList(telcoAvailableAddressList)) {
            maybeTelcoAvailableAddress = findMatchingATelcoAvailableAddress(telcoAvailableAddressList, cleanedAddress);
        }

        return maybeTelcoAvailableAddress;
    }

    private Optional<TelcoAvailableAddress> findMatchingATelcoAvailableAddress(
            final List<TelcoAvailableAddress> telcoAvailableAddressList, final Address address) {

        Optional<TelcoAvailableAddress> bestMatchAddress = empty();
        int bestMatchScore = 0;

        for (final TelcoAvailableAddress telcoAvailableAddress : telcoAvailableAddressList) {
            final int matchScore = matchOnStreetAndAtLeastOneOther(address, AddressMatchingUtils.cleanAddressForMatching(telcoAvailableAddress));
            if (matchScore > bestMatchScore) {
                bestMatchScore = matchScore;
                bestMatchAddress = of(telcoAvailableAddress);
            }
        }

        return bestMatchAddress;
    }

    private int matchOnStreetAndAtLeastOneOther(final Address address, final TelcoAvailableAddress telcoAvailableAddress) {

        final List<String> parts = AddressMatchingUtils.buildParts(address);
        int i = 0;

        //@formatter:off
        if (matchesOnStreet(address, telcoAvailableAddress, parts)) {
            if (hasSame(address, telcoAvailableAddress.getBuildingNumber(),parts)) { i++; }
            if (hasSame(address, telcoAvailableAddress.getSubBuilding(),parts)) { i++; }
            if (hasSame(address, telcoAvailableAddress.getBuildingName(),parts)) { i++; }
        }
        //@formatter:on

        return i;
    }

    private boolean matchesOnStreet(final Address address, final TelcoAvailableAddress telcoAvailableAddress, final List<String> parts) {
        if (isEmpty(telcoAvailableAddress.getStreet())) { return false; }
        return hasSame(address, telcoAvailableAddress.getStreet(),parts);
    }
}
