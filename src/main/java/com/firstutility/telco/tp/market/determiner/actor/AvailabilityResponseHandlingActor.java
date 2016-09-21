package com.firstutility.telco.tp.market.determiner.actor;

import akka.actor.UntypedActor;
import com.firstutility.telco.tp.market.determiner.domain.DeterminedAvailabilityResponseMsg;
import com.firstutility.telco.tp.market.determiner.domain.ProcessCompleteMsg;
import com.firstutility.telco.tp.market.determiner.service.FileHandlingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class AvailabilityResponseHandlingActor extends UntypedActor {

    Logger LOG = LoggerFactory.getLogger(AvailabilityResponseHandlingActor.class);

    private final FileHandlingService fileHandlingService;

    @Autowired
    public AvailabilityResponseHandlingActor(final FileHandlingService fileHandlingService) {
        this.fileHandlingService = fileHandlingService;
    }

    @Override
    public void onReceive(final Object message) throws Throwable {

        if (message instanceof DeterminedAvailabilityResponseMsg) {
            fileHandlingService.handleResponse((DeterminedAvailabilityResponseMsg) message);
        } else if (message instanceof ProcessCompleteMsg) {
            fileHandlingService.finalise(((ProcessCompleteMsg) message).getFileName());
        } else {
            LOG.error("Unable to handle message {}", message);
            unhandled(message);
        }
    }
}
