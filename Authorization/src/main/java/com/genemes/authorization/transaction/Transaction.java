package com.genemes.authorization.transaction;

public record Transaction(
        Long payer,
        Long payee) {
}
