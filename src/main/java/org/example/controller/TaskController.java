package org.example.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.task.TaskDTO;
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

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.create(taskDTO));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

   /* @DeleteMapping
    public ResponseEntity<Void> deleteBatch(@RequestBody List<Long> ids) {
        taskService.deleteBatch(ids);
        return ResponseEntity.ok().build();
    }*/
}
