package org.example.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.entity.redis.User;
import org.example.service.redis.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestParam String id, @RequestParam String username, @RequestParam String status) {
        return ResponseEntity.ok(userService.create(id, username, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> get(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getList() {
        return ResponseEntity.ok(userService.getList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") String id, @RequestParam String username, @RequestParam String status) {
        return ResponseEntity.ok(userService.update(id, username, status));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") String userId) {
        userService.deactivateAndDeleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
