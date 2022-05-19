package com.soultech.banking.cmd.domain;

import com.soultech.banking.cmd.api.command.OpenAccountCommand;
import com.soultech.banking.common.events.AccountClosedEvent;
import com.soultech.banking.common.events.AccountOpenedEvent;
import com.soultech.banking.common.events.FundsDepositedEvent;
import com.soultech.banking.common.events.FundsWithdrawnEvent;
import com.soultech.banking.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private Boolean active;

    private double balance;

    public double getBalance(){
        return this.balance;
    }

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder().
                id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount) {
        if (this.active) {
            throw new IllegalStateException("Funds cannot be deposited into this account");
        }
        if (amount <= 0) {
            throw new IllegalStateException("The money deposit cannot be zero or less than zero");
        }

        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount) {
        if (this.active) {
            throw new IllegalStateException("The bank account is closed");
        }
        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount(){
        if(!active) {
            throw new IllegalStateException("The bank account is closed");
        }

        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }
}
