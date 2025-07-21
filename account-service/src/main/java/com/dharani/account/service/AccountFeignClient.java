package com.dharani.account.service;

import com.dharani.account.model.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ADDRESS-SERVICE", path = "/address")
public interface AccountFeignClient {

    @GetMapping("/{id}")
    Address getAddress(@PathVariable("id") String id);
}
