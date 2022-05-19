package com.soultech.banking.cmd.infrastructure;

import com.soultech.banking.cqrs.core.commands.BaseCommand;
import com.soultech.banking.cqrs.core.commands.CommandHandlerMethod;
import com.soultech.banking.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();


    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if (handlers == null || handlers.size() == 0){
           throw new RuntimeException("The command handler wasn't registered");
        }
        if (handlers.size()> 1){
            throw new RuntimeException("Send more than one command is not allowed");
        }

        handlers.get(0).handle(command);
    }
}