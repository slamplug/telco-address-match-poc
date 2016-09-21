package com.firstutility.telco.tp.market.determiner.task;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.FromConfig;
import com.firstutility.telco.tp.market.determiner.domain.EnergyCustomerRequestMsg;
import com.firstutility.telco.tp.market.determiner.extension.SpringExtension;
import com.firstutility.telco.tp.market.determiner.service.FileHandlingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Component
public class PeriodicFileCheckerTask {

    private final Logger LOG = LoggerFactory.getLogger(PeriodicFileCheckerTask.class.getName());

    private final FileHandlingService fileHandlingService;

    private final ActorSystem actorSystem;

    private final SpringExtension springExtension;

    private final ActorRef availabilityResponseHandlingActor;

    private final ActorRef supervisorRouter;

    private final ActorRef supervisorActor;

    @Autowired
    public PeriodicFileCheckerTask(final ActorSystem actorSystem, final SpringExtension springExtension,
            final FileHandlingService fileHandlingService) {

        this.actorSystem = actorSystem;
        this.springExtension = springExtension;
        this.fileHandlingService = fileHandlingService;

        availabilityResponseHandlingActor = actorSystem
                .actorOf(springExtension.props("availabilityResponseHandlingActor", fileHandlingService),
                        "availabilityResponseHandlingActor");

        supervisorRouter = actorSystem
                .actorOf(springExtension.props("energyCustomerRequestProcessorActor").withRouter(new FromConfig()),
                        "supervisorRouter");

        supervisorActor = actorSystem
                .actorOf(springExtension.props("supervisorActor", supervisorRouter, availabilityResponseHandlingActor),
                        "supervisorActor");
    }

    @Scheduled(fixedRate = 5000)
    public void postToInflux() throws Exception {

        final Optional<Path> maybePath = fileHandlingService.getFirstFileToProcess();

        if (maybePath.isPresent()) {
            final List<EnergyCustomerRequestMsg> requests = fileHandlingService.loadFile(maybePath.get());

            requests.forEach(r -> supervisorActor.tell(r, null));
        }
    }
}
