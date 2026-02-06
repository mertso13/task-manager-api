package io.github.mertso13.taskmanagerapi.service;

import java.util.List;

import io.github.mertso13.taskmanagerapi.Task;

public interface TaskService {
    List<Task> getAllTasks();
    Task getTaskByID(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task updatedTask);
    void deleteTask(Long id);
}
