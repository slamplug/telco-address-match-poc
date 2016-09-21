package com.firstutility.telco.tp.market.determiner.actor;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.firstutility.telco.tp.market.determiner.domain.DeterminedAvailabilityResponseMsg;
import com.firstutility.telco.tp.market.determiner.domain.EnergyCustomerRequestMsg;
import com.firstutility.telco.tp.market.determiner.domain.ProcessCompleteMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope("prototype")
public class SupervisorActor extends UntypedActor {

    Logger LOG = LoggerFactory.getLogger(SupervisorActor.class);

    private final ActorRef supervisorRouter;

    private final ActorRef availabilityResponseHandlingActor;

    private final ConcurrentHashMap<String, AtomicInteger> messagesInFlightMap = new ConcurrentHashMap<>();

    @Autowired
    public SupervisorActor(final ActorRef supervisorRouter, final ActorRef availabilityResponseHandlingActor) {
        this.supervisorRouter = supervisorRouter;
        this.availabilityResponseHandlingActor = availabilityResponseHandlingActor;
    }

    @Override
    public void onReceive(final Object message) throws Throwable {

        if (message instanceof EnergyCustomerRequestMsg) {

            final String key = ((EnergyCustomerRequestMsg) message).getFileName();

            messagesInFlightMap.compute(key,
                    (k, v) -> (v == null) ? new AtomicInteger(1) : new AtomicInteger(v.incrementAndGet()));

            messagesInFlightMap.putIfAbsent(((EnergyCustomerRequestMsg) message).getFileName(), new AtomicInteger(1));

            supervisorRouter.tell(message, getSelf());

        } else if (message instanceof DeterminedAvailabilityResponseMsg) {

            final String key = ((DeterminedAvailabilityResponseMsg) message).getFileName();

            final int newVal = messagesInFlightMap.get(key).decrementAndGet();

            availabilityResponseHandlingActor.tell(message, getSelf());

            if (newVal == 0) {
                availabilityResponseHandlingActor.tell(new ProcessCompleteMsg(key), getSelf());
            }

        } else {
            LOG.error("Unable to handle message {}", message);
        }
    }
}