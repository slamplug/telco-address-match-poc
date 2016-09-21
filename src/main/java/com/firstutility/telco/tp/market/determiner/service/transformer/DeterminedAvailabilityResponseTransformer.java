package com.firstutility.telco.tp.market.determiner.service.transformer;

import com.firstutility.telco.tp.market.determiner.domain.DeterminedAvailabilityResponseMsg;

public interface DeterminedAvailabilityResponseTransformer {

    String transform(final DeterminedAvailabilityResponseMsg determinedAvailabilityResponseMsg);
}
