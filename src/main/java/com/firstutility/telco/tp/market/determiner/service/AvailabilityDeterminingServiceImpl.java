package com.firstutility.telco.tp.market.determiner.service;

import com.firstutility.java.util.Either;
import com.firstutility.telco.adaptor.error.api.TelcoErrors;
import com.firstutility.telco.addressmatching.api.TelcoAvailableAddress;
import com.firstutility.telco.tp.market.determiner.client.AddressMatchingServiceClient;
import com.firstutility.telco.tp.market.determiner.domain.DeterminedAvailabilityResponseMsg;
import com.firstutility.telco.tp.market.determiner.domain.EnergyCustomerRequestMsg;
import com.firstutility.telco.tp.market.determiner.service.addressmatch.AddressMatchingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AvailabilityDeterminingServiceImpl implements AvailabilityDeterminingService {

    private final Logger LOG = LoggerFactory.getLogger(AvailabilityDeterminingServiceImpl.class);

    private final AddressMatchingServiceClient addressMatchingServiceClient;

    private final AddressMatchingService addressMatchingService;

    @Autowired
    public AvailabilityDeterminingServiceImpl(final AddressMatchingServiceClient addressMatchingServiceClient, final AddressMatchingService addressMatchingService) {
        this.addressMatchingServiceClient = addressMatchingServiceClient;
        this.addressMatchingService = addressMatchingService;
    }

    @Override
    public DeterminedAvailabilityResponseMsg determineAvailabilityForCustomer(final EnergyCustomerRequestMsg request) {

        final Either<List<TelcoAvailableAddress>, TelcoErrors> response = addressMatchingServiceClient.getAvailableAddresses(request.getAddress().getPostCode());

        Optional<TelcoAvailableAddress> telcoAvailableAddress = null;
        if (response.isLeft()) {
            telcoAvailableAddress = addressMatchingService.getMatchingTelcoAvailableAddress(response.getLeft().get(), request.getAddress());
        }

        //LOG.info("{}, {}, {}", request.getAddress().getPostCode(),telcoAvailableAddress.getAlk(), telcoAvailableAddress.getAddressQualifier());

        if (telcoAvailableAddress.isPresent()) {

            return new DeterminedAvailabilityResponseMsg(
                    request.getFileName(),
                    request.getCustomerNumber(),
                    telcoAvailableAddress.get().getAlk(),
                    telcoAvailableAddress.get().getAddressQualifier().value(),
                    "MIGRATE",
                    null,
                    null,
                    null, // current provider
                    false, // tt customer on fibre
                    false  // tt customer migrated to tt
            );
        } else {

            return new DeterminedAvailabilityResponseMsg(
                    request.getFileName(),
                    request.getCustomerNumber(),
                    null, null, null, null, null, null,
                    false, false);
        }
    }
}
