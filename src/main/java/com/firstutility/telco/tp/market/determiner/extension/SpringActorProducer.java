package com.firstutility.telco.tp.market.determiner.extension;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;

public class SpringActorProducer implements IndirectActorProducer {

    private final ApplicationContext applicationContext;
    private final String actorBeanName;
    private final Object[] args;

    public SpringActorProducer(final ApplicationContext applicationContext, final String actorBeanName) {
        this.applicationContext = applicationContext;
        this.actorBeanName = actorBeanName;
        this.args = null;
    }

    public SpringActorProducer(final ApplicationContext applicationContext, final String actorBeanName, final Object... args) {
        this.applicationContext = applicationContext;
        this.actorBeanName = actorBeanName;
        this.args = args;
    }

    @Override
    public Actor produce() {
        return args == null ?
                (Actor) applicationContext.getBean(actorBeanName):
                (Actor) applicationContext.getBean(actorBeanName, args);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return (Class<? extends Actor>) applicationContext.getType(actorBeanName);
    }
}