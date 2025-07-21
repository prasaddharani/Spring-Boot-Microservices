package com.dharani.account.controller;

import com.dharani.account.model.Account;
import com.dharani.account.model.Address;
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

    @GetMapping("/{id}")
    private Account getAccountsUsingRest(@PathVariable int id) {
        try {
            Address address = restTemplate.getForObject("http://ADDRESS-SERVICE/address/{id}", Address.class, id);
            return new Account(id, "Dharani", address);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error calling address service: " + e.getMessage());
        }
    }
}
