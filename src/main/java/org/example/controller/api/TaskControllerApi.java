package org.example.controller.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.config.doc.DocMethodAuth;
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

import java.util.List;

@RequestMapping("/api/tasks")
public interface TaskControllerApi {

    @DocMethodAuth(
        summary = "Create task",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final TaskDTO taskDTO);

    @DocMethodAuth(
        summary = "Get all task list",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = TaskDTO.class))
    )
    @GetMapping
    ResponseEntity<Result<List<TaskDTO>>> getList();

    @DocMethodAuth(
        summary = "Get task by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = TaskDTO.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<Result<TaskDTO>> get(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Update task by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = TaskDTO.class))
    )
    @PutMapping("/{id}")
    ResponseEntity<Result<TaskDTO>> update(@PathVariable("id") final Long id, @RequestBody final TaskDTO taskDTO);

    @DocMethodAuth(
        summary = "Delete task by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema())
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Delete tasks by ids",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema())
    )
    @DeleteMapping("/delete")
    ResponseEntity<Result<Void>> deleteTasks(@RequestBody final List<Long> tasksIds);
}
