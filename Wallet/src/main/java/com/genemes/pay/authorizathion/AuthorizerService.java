package com.genemes.pay.authorizathion;

import com.genemes.pay.exception.UnathorizedException;
import com.genemes.pay.trasaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class AuthorizerService {

    private static final Logger log = LoggerFactory.getLogger(AuthorizerService.class);
    private RestClient restClient;

    public AuthorizerService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.genemes.com")
                .build();
    }

    public void authorize(Transaction transaction) {
        log.info("Authorizing transaction {}", transaction);
        var response = restClient.get()
                .retrieve()
                .toEntity(Authorization.class);

        if(response.getStatusCode().isError()
                || Boolean.FALSE.equals(Objects.requireNonNull(response.getBody()).isAuthorized())) {
            throw new UnathorizedException("Authorization failed");
        }
        log.info("transaction authorized {}", transaction);
    }
}
