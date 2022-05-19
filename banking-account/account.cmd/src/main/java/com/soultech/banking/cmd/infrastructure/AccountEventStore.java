package com.soultech.banking.cmd.infrastructure;

import com.soultech.banking.cmd.domain.AccountAggregate;
import com.soultech.banking.cmd.domain.EventStoreRepository;
import com.soultech.banking.cqrs.core.events.BaseEvent;
import com.soultech.banking.cqrs.core.events.EventModel;
import com.soultech.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.soultech.banking.cqrs.core.exceptions.ConcurrencyException;
import com.soultech.banking.cqrs.core.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {

        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }

        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            //if(persistedEvent != null){

        }
        // }
    }

    @Override
    public List<BaseEvent> getEvent(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("The bank account is not correct");
        }
        return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
    }
}
