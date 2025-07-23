package com.dharani.account.controller;

import com.dharani.account.model.Account;
import com.dharani.account.model.Address;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/accounts")
public class RestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/rest/{id}")
    @CircuitBreaker(name = "addressServiceCB", fallbackMethod = "fallbackAddress")
    public Account getAccountUsingRest(@PathVariable int id) {
        // REMOVE try-catch block
        Address address = restTemplate.getForObject("http://ADDRESS-SERVICE/address/" + id, Address.class);
        return new Account(id, "Dharani", address);
    }

    // Fallback must match the method signature + Throwable
    public Account fallbackAddress(int id, Throwable t) {
        System.out.println("⚠️ Fallback called due to: " + t.getMessage());
        Address address = new Address(id, "Fallback Address", "0000000");
        return new Account(id, "Dharani", address);
    }

}
