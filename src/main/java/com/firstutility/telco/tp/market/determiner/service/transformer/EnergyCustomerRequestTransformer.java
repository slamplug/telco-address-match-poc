package com.firstutility.telco.tp.market.determiner.service.transformer;

import com.firstutility.telco.tp.market.determiner.domain.EnergyCustomerRequestMsg;

public interface EnergyCustomerRequestTransformer {

    EnergyCustomerRequestMsg transform(final String fileName, final String line);
}
