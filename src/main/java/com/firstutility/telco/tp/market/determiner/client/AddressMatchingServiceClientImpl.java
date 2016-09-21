package com.firstutility.telco.tp.market.determiner.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstutility.java.util.Either;
import com.firstutility.telco.adaptor.error.api.TelcoErrors;
import com.firstutility.telco.addressmatching.api.TelcoAvailableAddress;
import com.firstutility.telco.tp.market.determiner.exception.DownstreamServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.firstutility.java.util.Either.left;
import static com.firstutility.java.util.Either.right;

@Component
public class AddressMatchingServiceClientImpl implements AddressMatchingServiceClient {

    private final Logger LOG = LoggerFactory.getLogger(AddressMatchingServiceClientImpl.class);

    private final RestOperations availableAddressessTemplate;
    private final String getAvailableAddressesUri;
    private final ObjectMapper mapper;

    @Autowired
    public AddressMatchingServiceClientImpl(
            @Qualifier("restTemplate") final RestOperations addressMatchingRestTemplate,
            @Value("${address.match.service.baseurl}") final String addressMatchingServiceBaseUrl,
            final ObjectMapper mapper) {

        this.availableAddressessTemplate = addressMatchingRestTemplate;
        this.getAvailableAddressesUri = addressMatchingServiceBaseUrl + "/v1/available-addresses?postcode=%s";
        this.mapper = mapper;
    }

    public Either<List<TelcoAvailableAddress>, TelcoErrors> getAvailableAddresses(final String postCode) {

        final String uri = String.format(getAvailableAddressesUri, postCode);

        try {
            final TelcoAvailableAddress[] adaptorAvailableAddresses = availableAddressessTemplate
                    .getForObject(uri, TelcoAvailableAddress[].class);

            return left(Arrays.asList(adaptorAvailableAddresses));

        } catch (final HttpStatusCodeException ex) {
            LOG.error("failed getting available addresses for postcode {}", postCode, ex);
            final TelcoErrors telcoErrors = getTelcoErrorsFromException(ex);
            return right(telcoErrors);
        }
    }

    private TelcoErrors getTelcoErrorsFromException(final HttpStatusCodeException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), TelcoErrors.class);
        } catch (final IOException e) {
            LOG.error("failed transforming to telco error", e);
            throw new DownstreamServiceException(e.getMessage(), e);
        }
    }
}


