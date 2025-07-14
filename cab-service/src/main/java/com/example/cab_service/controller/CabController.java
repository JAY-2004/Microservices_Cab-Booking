package com.example.cab_service.controller;

import com.example.cab_service.entity.Cab;
import com.example.cab_service.repository.CabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cabs")
public class CabController {

    @Autowired
    private CabRepository cabRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public Cab createCab(@RequestBody Cab cab) {
        return cabRepository.save(cab);
    }

    @GetMapping
    public List<Cab> getAllCabs() {
        return cabRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cab getCabById(@PathVariable Long id) {
        return cabRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Cab updateCab(@PathVariable Long id, @RequestBody Cab updatedCab) {
        Optional<Cab> cabOptional = cabRepository.findById(id);
        if (cabOptional.isPresent()) {
            Cab cab = cabOptional.get();
            cab.setRegistrationNumber(updatedCab.getRegistrationNumber());
            cab.setDriverName(updatedCab.getDriverName());
            cab.setAvailable(updatedCab.isAvailable());
            return cabRepository.save(cab);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteCab(@PathVariable Long id) {
        cabRepository.deleteById(id);
        return "Cab deleted with ID: " + id;
    }

    @GetMapping("/user-info/{userId}")
    public String getUserInfoFromUserService(@PathVariable String userId) {
        String url = "http://user-service/users/" + userId;
        return restTemplate.getForObject(url, String.class);
    }
}
