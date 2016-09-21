package com.firstutility.telco.tp.market.determiner.service;

import com.firstutility.telco.tp.market.determiner.domain.DeterminedAvailabilityResponseMsg;
import com.firstutility.telco.tp.market.determiner.domain.EnergyCustomerRequestMsg;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface FileHandlingService {

    Optional<Path> getFirstFileToProcess() throws IOException;

    List<EnergyCustomerRequestMsg> loadFile(final Path path) throws IOException;

    void handleResponse(final DeterminedAvailabilityResponseMsg determinedAvailabilityResponseMsg);

    void finalise(final String fileName) throws IOException;
}
