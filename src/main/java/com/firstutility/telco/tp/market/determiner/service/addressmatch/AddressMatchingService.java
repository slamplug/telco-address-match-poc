package com.firstutility.telco.tp.market.determiner.service.addressmatch;

import com.firstutility.telco.addressmatching.api.TelcoAvailableAddress;
import com.firstutility.telco.tp.market.determiner.domain.Address;

import java.util.List;
import java.util.Optional;

public interface AddressMatchingService {

    Optional<TelcoAvailableAddress> getMatchingTelcoAvailableAddress(List<TelcoAvailableAddress> telcoAvailableAddressList, final Address address);
}
