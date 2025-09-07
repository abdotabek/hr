package org.example.service;

import org.example.dto.task.TaskDTO;

import java.util.List;

public interface TaskService {

    Long create(final TaskDTO taskDTO);

    TaskDTO get(final Long id);

    List<TaskDTO> getList();

    TaskDTO update(final Long id, final TaskDTO taskDTO);

    void delete(final Long id);

    void sendTaskIdsToQueue(final List<Long> taskIds);

}
