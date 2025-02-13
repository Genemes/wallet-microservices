package com.genemes.authorization.transaction;

import com.genemes.authorization.blocklist.BlocklistRepository;
import com.genemes.authorization.exception.BlockListException;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final BlocklistRepository blocklistRepository;

    public TransactionService(BlocklistRepository blocklistRepository) {
        this.blocklistRepository = blocklistRepository;
    }

    public AuthorizationDTO isAuthorized(Transaction request) {
        boolean isPayerBlacklisted = blocklistRepository.existsByUserId(request.payer());
        boolean isPayeeBlacklisted = blocklistRepository.existsByUserId(request.payee());

        if(isPayerBlacklisted || isPayeeBlacklisted) {
            return new AuthorizationDTO("UNAUTHORIZATION");
        }

        return new AuthorizationDTO("AUTHORIZATION");
    }
}