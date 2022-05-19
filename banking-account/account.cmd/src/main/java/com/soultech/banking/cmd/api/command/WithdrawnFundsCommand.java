package com.soultech.banking.cmd.api.command;

import com.soultech.banking.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class WithdrawnFundsCommand extends BaseCommand {

    private double amount;
}
