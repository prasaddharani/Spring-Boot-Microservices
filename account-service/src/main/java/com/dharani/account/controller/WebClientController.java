package com.dharani.account.controller;

import com.dharani.account.config.WebClientConfig;
import com.dharani.account.model.Account;
import com.dharani.account.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
public class WebClientController {

    @Autowired
    private WebClient.Builder builder;

    @GetMapping("/webclient/{id}")
    private Mono<Account> getAccount(@PathVariable int id) {
        return builder.build()
                .get()
                .uri("http://ADDRESS-SERVICE/address/" + id)
                .retrieve()
                .bodyToMono(Address.class)
                .map(address -> new Account(id, "Dharani", address));
    }
}
