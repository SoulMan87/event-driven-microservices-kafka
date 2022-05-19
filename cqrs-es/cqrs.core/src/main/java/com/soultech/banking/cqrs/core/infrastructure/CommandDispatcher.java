package com.soultech.banking.cqrs.core.infrastructure;

import com.soultech.banking.cqrs.core.commands.BaseCommand;
import com.soultech.banking.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {

    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);

    void send(BaseCommand command);
}
