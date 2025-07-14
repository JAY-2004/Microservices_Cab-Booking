package com.example.user_service.controller;

import com.example.user_service.entity.User;
import com.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return repo.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User u = repo.findById(id).orElse(null);
        if (u != null) {
            u.setName(user.getName());
            u.setEmail(user.getEmail());
            u.setRole(user.getRole());
            return repo.save(u);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
