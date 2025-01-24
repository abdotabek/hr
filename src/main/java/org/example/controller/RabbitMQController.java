package org.example.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.service.RabbitMQService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/rabbit")
public class RabbitMQController {

    RabbitMQService rabbitMQService;

    @DeleteMapping("employee/{id}")
    public ResponseEntity<Void> sendEmployeeIdQueue(@PathVariable("id") Long id) {
        rabbitMQService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("task/{id}")
    public ResponseEntity<Void> sendTask(@PathVariable("id") Long id) {
        rabbitMQService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
