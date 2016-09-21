package com.firstutility.telco.tp.market.determiner.service;

import com.firstutility.telco.tp.market.determiner.domain.DeterminedAvailabilityResponseMsg;
import com.firstutility.telco.tp.market.determiner.domain.EnergyCustomerRequestMsg;

public interface AvailabilityDeterminingService {

    DeterminedAvailabilityResponseMsg determineAvailabilityForCustomer(final EnergyCustomerRequestMsg request);
}
