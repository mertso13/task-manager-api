package io.github.mertso13.taskmanagerapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.mertso13.taskmanagerapi.Task;
import io.github.mertso13.taskmanagerapi.service.TaskService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        // TODO
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTaskByID(Long id) {
        // TODO
        return null;
    }

    @Override
    @Transactional
    public Task createTask(Task task) {
        // TODO
        return task;
    }

    @Override
    @Transactional
    public Task updateTask(Long id, Task updatedTask) {
        // TODO
        return updatedTask;
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        // TODO
    }
}
