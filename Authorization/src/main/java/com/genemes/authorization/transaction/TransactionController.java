package com.genemes.authorization.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
public class TransactionController {

    private final TransactionService authorizationService;

    public TransactionController(TransactionService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/check")
    public ResponseEntity<AuthorizationDTO> checkAuthorization(@RequestBody Transaction request) {
        return ResponseEntity.ok(authorizationService.isAuthorized(request));
    }
}
