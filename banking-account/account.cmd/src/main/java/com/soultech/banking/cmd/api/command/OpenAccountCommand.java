package com.soultech.banking.cmd.api.command;

import com.soultech.banking.common.dto.AccountType;
import com.soultech.banking.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {

    private String accountHolder;

    private AccountType accountType;

    private double openingBalance;
}
