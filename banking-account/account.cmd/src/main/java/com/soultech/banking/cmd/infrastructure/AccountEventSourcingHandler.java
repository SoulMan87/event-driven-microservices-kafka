package com.soultech.banking.cmd.infrastructure;

import com.soultech.banking.cmd.domain.AccountAggregate;
import com.soultech.banking.cqrs.core.domain.AggregateRoot;
import com.soultech.banking.cqrs.core.events.BaseEvent;
import com.soultech.banking.cqrs.core.handlers.EventSourcingHandler;
import com.soultech.banking.cqrs.core.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.saveEvents(aggregateRoot.getId(), aggregateRoot.getUncommittedChanges(), aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvent(id);
        if (events != null && !events.isEmpty()){
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }
}
