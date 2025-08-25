package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.api.TaskControllerApi;
import org.example.dto.Result;
import org.example.dto.task.TaskDTO;
import org.example.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController implements TaskControllerApi {

    private final TaskService taskService;

    @Override
    public ResponseEntity<Result<Long>> create(@RequestBody final TaskDTO taskDTO) {
        return ResponseEntity.ok(Result.success(taskService.create(taskDTO)));
    }

    @Override
    public ResponseEntity<Result<List<TaskDTO>>> getList() {
        return ResponseEntity.ok(Result.success(taskService.getList()));
    }

    @Override
    public ResponseEntity<Result<TaskDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(taskService.get(id)));
    }

    @Override
    public ResponseEntity<Result<TaskDTO>> update(@PathVariable("id") final Long id, @RequestBody final TaskDTO taskDTO) {
        return ResponseEntity.ok(Result.success(taskService.update(id, taskDTO)));
    }

    @Override
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        taskService.delete(id);
        return ResponseEntity.ok(Result.success());
    }

    @Override
    public ResponseEntity<Result<Void>> deleteTasks(@RequestBody final List<Long> tasksIds) {
        taskService.sendTaskIdsToQueue(tasksIds);
        return ResponseEntity.ok(Result.success());
    }
}
