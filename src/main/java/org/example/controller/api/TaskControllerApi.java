package org.example.controller.api;

import org.example.dto.Result;
import org.example.dto.task.TaskDTO;
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
public interface TaskControllerApi {

    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final TaskDTO taskDTO);

    @GetMapping
    ResponseEntity<Result<List<TaskDTO>>> getList();

    @GetMapping("/{id}")
    ResponseEntity<Result<TaskDTO>> get(@PathVariable("id") final Long id);

    @PutMapping("/{id}")
    ResponseEntity<Result<TaskDTO>> update(@PathVariable("id") final Long id, @RequestBody final TaskDTO taskDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id);

    @DeleteMapping("/delete")
    ResponseEntity<Result<Void>> deleteTasks(@RequestBody final List<Long> tasksIds);
}
