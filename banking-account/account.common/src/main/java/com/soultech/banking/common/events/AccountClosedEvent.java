package com.soultech.banking.common.events;

import com.soultech.banking.cqrs.core.events.BaseEvent;
import lombok.Data;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
}
