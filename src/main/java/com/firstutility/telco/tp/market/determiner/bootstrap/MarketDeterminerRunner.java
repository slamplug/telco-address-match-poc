package com.firstutility.telco.tp.market.determiner.bootstrap;

import static org.springframework.boot.SpringApplication.run;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.FromConfig;
import com.firstutility.telco.tp.market.determiner.extension.SpringExtension;
import com.firstutility.telco.tp.market.determiner.service.FileHandlingServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.file.FileSystems;
import java.nio.file.Path;

@EnableConfigurationProperties
//@formatter:off
@SpringBootApplication(scanBasePackages = { "com.firstutility.telco.tp.market.determiner"})
@EnableScheduling
//@formatter:on
public class MarketDeterminerRunner {
    public static void main(final String[] args) throws Exception {

        run(MarketDeterminerRunner.class, args);

        /*final ActorSystem system = applicationContext.getBean(ActorSystem.class);

        final SpringExtension springExtension = applicationContext.getBean(SpringExtension.class);

        final ActorRef availabilityResponseHandlingActor =
                system.actorOf(springExtension.props("availabilityResponseHandlingActor"));

        //@formatter:off
        final ActorRef supervisorRouter = system.actorOf(
                springExtension.props("energyCustomerRequestProcessorActor").withRouter(new FromConfig()), "supervisorRouter");
        //@formatter:on

        final ActorRef supervisorActor = system.actorOf(
                springExtension.props("supervisorActor", supervisorRouter, availabilityResponseHandlingActor));*/
    }
}