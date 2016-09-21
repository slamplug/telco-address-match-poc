package com.firstutility.telco.tp.market.determiner.actor;

import akka.actor.UntypedActor;
import com.firstutility.telco.tp.market.determiner.domain.DeterminedAvailabilityResponseMsg;
import com.firstutility.telco.tp.market.determiner.domain.EnergyCustomerRequestMsg;
import com.firstutility.telco.tp.market.determiner.service.AvailabilityDeterminingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class EnergyCustomerRequestProcessorActor extends UntypedActor {

    Logger LOG = LoggerFactory.getLogger(EnergyCustomerRequestProcessorActor.class);

    @Autowired
    private final AvailabilityDeterminingService availabilityDeterminingService;

    @Autowired
    public EnergyCustomerRequestProcessorActor(final AvailabilityDeterminingService availabilityDeterminingService) {
        this.availabilityDeterminingService = availabilityDeterminingService;
    }

    @Override
    public void onReceive(final Object message) throws Throwable {

        if (message instanceof EnergyCustomerRequestMsg) {
            final DeterminedAvailabilityResponseMsg response =
                    availabilityDeterminingService.determineAvailabilityForCustomer((EnergyCustomerRequestMsg) message);

            getSender().tell(response, getSelf());
        } else {
            LOG.error("Unable to handle message {}", message);
            unhandled(message);
        }
    }
}
