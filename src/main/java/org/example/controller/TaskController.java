package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.Result;
import org.example.dto.task.TaskDTO;
import org.example.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Result<Long>> create(@RequestBody final TaskDTO taskDTO) {
        return ResponseEntity.ok(Result.success(taskService.create(taskDTO)));
    }

    @GetMapping
    public ResponseEntity<Result<List<TaskDTO>>> getList() {
        return ResponseEntity.ok(Result.success(taskService.getList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<TaskDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(taskService.get(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<TaskDTO>> update(@PathVariable("id") final Long id, @RequestBody final TaskDTO taskDTO) {
        return ResponseEntity.ok(Result.success(taskService.update(id, taskDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        taskService.delete(id);
        return ResponseEntity.ok(Result.success());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Result<Void>> deleteTasks(@RequestBody final List<Long> tasksIds) {
        taskService.sendTaskIdsToQueue(tasksIds);
        return ResponseEntity.ok(Result.success());
    }
}
