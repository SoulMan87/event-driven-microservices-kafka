package com.soultech.banking.cqrs.core.producers;

import com.soultech.banking.cqrs.core.events.BaseEvent;

public interface EventProducer {

    void produce(String topic, BaseEvent event);
}
