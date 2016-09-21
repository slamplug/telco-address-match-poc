package com.firstutility.telco.tp.market.determiner.service.transformer;

import com.firstutility.telco.tp.market.determiner.domain.Address;
import com.firstutility.telco.tp.market.determiner.domain.EnergyCustomerRequestMsg;
import org.springframework.stereotype.Component;

@Component
public class EnergyCustomerRequestTransformerImpl implements EnergyCustomerRequestTransformer {

    @Override
    public EnergyCustomerRequestMsg transform(final String fileName, final String line) {

        final String[] parts = line.split(",");

        //@formatter:off
        final Address address = new Address(
                safePart(parts, 3),
                safePart(parts, 4),
                safePart(parts, 5),
                safePart(parts, 6),
                safePart(parts, 7),
                safePart(parts, 2));

        return new EnergyCustomerRequestMsg(
                fileName,
                parts[0], // customer number
                parts[1], // cli
                address); // address
        //@formatter:on
    }

    private String safePart(final String[] parts, final int index) {
        return (parts.length > index) ? parts[index] : null;
    }
}
