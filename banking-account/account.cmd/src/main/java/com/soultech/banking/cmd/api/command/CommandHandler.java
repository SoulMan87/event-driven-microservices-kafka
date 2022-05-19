package com.soultech.banking.cmd.api.command;

public interface CommandHandler {

    void handle(OpenAccountCommand command);

    void handle(DepositFundsCommand command);

    void handle(WithdrawnFundsCommand command);

    void handle(CloseAccountCommand command);
}
