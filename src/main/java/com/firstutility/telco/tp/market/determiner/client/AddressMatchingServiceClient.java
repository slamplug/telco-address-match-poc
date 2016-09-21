package com.firstutility.telco.tp.market.determiner.client;

import com.firstutility.java.util.Either;
import com.firstutility.telco.adaptor.error.api.TelcoErrors;
import com.firstutility.telco.addressmatching.api.TelcoAvailableAddress;

import java.util.List;

public interface AddressMatchingServiceClient {

    Either<List<TelcoAvailableAddress>, TelcoErrors> getAvailableAddresses(final String postCode);
}
