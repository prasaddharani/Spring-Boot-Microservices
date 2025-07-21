package com.dharani.account.controller;

import com.dharani.account.model.Account;
import com.dharani.account.model.Address;
import com.dharani.account.service.AccountFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class FeignClientController {

    @Autowired
    private AccountFeignClient accountFeignClient;

    @GetMapping("/feign/{id}")
    public Account getAccounts(@PathVariable int id) {
        Address address = accountFeignClient.getAddress(String.valueOf(id));
        return new Account(id, "Dharani", address);
    }
}
