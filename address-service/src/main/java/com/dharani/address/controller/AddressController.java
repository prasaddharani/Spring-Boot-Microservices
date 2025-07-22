package com.dharani.address.controller;

import com.dharani.address.model.Address;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    List<Address> addresses = new ArrayList<>(
            List.of(new Address(1, "Tirupati", "517501"),
                    new Address(2, "Tirupati", "517502"),
                    new Address(3, "Tirupati", "517503"))
    );

    @GetMapping("/{id}")
    public Address getAddress(@PathVariable String id) {
        int addressId = Integer.parseInt(id);
        return addresses.stream()
                .filter(address -> addressId == address.getId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Record Not Found Exception"));
    }
}
