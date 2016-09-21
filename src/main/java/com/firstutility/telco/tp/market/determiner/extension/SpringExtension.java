package com.firstutility.telco.tp.market.determiner.extension;

import akka.actor.Extension;
import akka.actor.Props;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringExtension implements Extension {

    private ApplicationContext applicationContext;

    public void initialize(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Props props(final String actorBeanName, final Object... args) {
        //@formatter:off
        return (args != null && args.length > 0) ?
                Props.create(SpringActorProducer.class, applicationContext, actorBeanName, args) :
                Props.create(SpringActorProducer.class, applicationContext, actorBeanName);
        //@formatter:on
    }
}