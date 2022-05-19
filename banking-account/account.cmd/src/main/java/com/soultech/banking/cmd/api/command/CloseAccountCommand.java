package com.soultech.banking.cmd.api.command;

import com.soultech.banking.cqrs.core.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {

    public CloseAccountCommand(String id){
        super(id);
    }
}
