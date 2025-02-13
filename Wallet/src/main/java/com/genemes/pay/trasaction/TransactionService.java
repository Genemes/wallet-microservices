package com.genemes.pay.trasaction;

import com.genemes.pay.authorizathion.AuthorizerService;
import com.genemes.pay.exception.InsufficientBalanceException;
import com.genemes.pay.exception.InvalidTransactionException;
import com.genemes.pay.exception.ResourceNotFoundException;
import com.genemes.pay.notification.NotificationService;
import com.genemes.pay.wallet.Wallet;
import com.genemes.pay.wallet.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizerService authorizerService;
    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository,
                              WalletRepository walletRepository,
                              AuthorizerService authorizerService,
                              NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizerService = authorizerService;
        this.notificationService = notificationService;
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        validate(transaction);

        var transactionCreated =  transactionRepository.save(transaction);

        var walletDebit = walletRepository.findById(transaction.payer())
                .orElseThrow(() -> new ResourceNotFoundException("Wallet %d not found!".formatted(transaction.payer())));
        walletRepository.save(walletDebit.debit(transaction.value()));
        var walletCredit = walletRepository.findById(transaction.payee())
                .orElseThrow(() -> new ResourceNotFoundException("Wallet %d not found!".formatted(transaction.payee())));
        walletRepository.save(walletCredit.credit(transaction.value()));

        // Authorization
        authorizerService.authorize(transactionCreated);
        // Notification
        notificationService.notify(transaction);

        return transactionCreated;
    }

    private void validate(Transaction transaction) {
        walletRepository.findById(transaction.payee())
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with id: %d".formatted(transaction.payee())));

        Wallet payer = walletRepository.findById(transaction.payer())
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with id: %d".formatted(transaction.payer())));

        validateTransaction(transaction, payer);
    }

    private void validateTransaction(Transaction transaction, Wallet payer) {
        if (payer.balance().compareTo(transaction.value()) < 0) {
            throw new InsufficientBalanceException("The payer has not insufficient balance in your wallet!");
        }
        if (payer.id().equals(transaction.payee())) {
            throw new InvalidTransactionException("Invalid transaction, payer cannot be equal to payee.");
        }
    }


    public List<Transaction> list() {
        return transactionRepository.findAll();
    }
}
