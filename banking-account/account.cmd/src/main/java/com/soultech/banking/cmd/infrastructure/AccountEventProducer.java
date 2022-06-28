package com.soultech.banking.cmd.infrastructure;

import com.soultech.banking.cqrs.core.events.BaseEvent;
import com.soultech.banking.cqrs.core.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountEventProducer implements EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public void produce(String topic, BaseEvent event) {
        this.kafkaTemplate.send(topic, event);
    }
}
