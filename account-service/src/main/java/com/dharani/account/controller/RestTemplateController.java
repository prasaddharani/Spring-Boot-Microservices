package com.dharani.account.controller;

import com.dharani.account.exceptions.RecordNotFoundException;
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

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class RestTemplateController {

    @Autowired
    private RestTemplate restTemplate;
    List<Account> accounts = List.of(
            new Account(1, "Dharani", null),
            new Account(2, "Prasad", null)
    );

    @GetMapping("/rest/{id}")
    public Account getAccountUsingRest(@PathVariable int id) {
        Account account = getAccount(id); // throws RecordNotFoundException
        Address address = getAddressFromService(id); // has CircuitBreaker
        account.setAddress(address);
        return account;
    }

    public Account getAccount(int id) {
        return accounts.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException("Not found"));
    }

    @CircuitBreaker(name = "addressServiceCB", fallbackMethod = "fallbackAddress")
    public Address getAddressFromService(int id) {
        return restTemplate.getForObject("http://ADDRESS-SERVICE/address/" + id, Address.class);
    }


    // Fallback must match the method signature + Throwable
    public Account fallbackAddress(int id, Throwable t) {
        System.out.println("⚠️ Fallback called due to: " + t.getMessage());
        Address address = new Address(id, "Fallback Address", "0000000");
        return new Account(id, "Dharani", address);
    }

}
