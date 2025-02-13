package com.genemes.pay.wallet;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("WALLETS")
public record Wallet(
        @Id Long id,
        String fullName,
        String cpf,
        String email,
        BigDecimal balance) {

    public Wallet credit(BigDecimal value) {
        return new Wallet(id, fullName, cpf, email, balance.add(value));
    }

    public Wallet debit(BigDecimal value) {
        return new Wallet(id, fullName, cpf, email, balance.subtract(value));
    }
}
