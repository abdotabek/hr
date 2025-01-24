package org.example.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.task.TaskDTO;
import org.example.service.RabbitMQService;
import org.example.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskController {

    TaskService taskService;
    RabbitMQService rabbitMQService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody TaskDTO dto) {
        return ResponseEntity.ok(taskService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getList() {
        return ResponseEntity.ok(taskService.getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable("id") Long id, @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.update(id, taskDTO));
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteBatch(@RequestBody List<Long> ids) {
        rabbitMQService.deleteBatchWithDelay(ids, 10000);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        rabbitMQService.deleteBatchWithDelay(List.of(id), 10000);
        return ResponseEntity.ok().build();
    }
}
