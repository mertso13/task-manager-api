package io.github.mertso13.taskmanagerapi.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import io.github.mertso13.taskmanagerapi.model.Task;
import io.github.mertso13.taskmanagerapi.model.TaskStatus;
import io.github.mertso13.taskmanagerapi.repository.TaskRepository;
import io.github.mertso13.taskmanagerapi.service.TaskService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTaskByID(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Task id must be a positive number.");
        }

        return taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Task not found: " + id));
    }

    @Override
    @Transactional
    public Task createTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task payload is required.");
        }
        if (task.getId() != null) {
            throw new IllegalArgumentException("New task must not have an id.");
        }
        if (task.getName() == null || task.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Task name is required.");
        }
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.TODO);
        }
        if (task.getCreatedAt() == null) { // not likely but just in case
            task.setCreatedAt(LocalDateTime.now());
        }

        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task updateTask(Long id, Task updatedTask) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Task id must be a positive number.");
        }
        if (updatedTask == null) {
            throw new IllegalArgumentException("Task payload is required.");
        }

        Task existingTask = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Task not found: " + id));

        if (updatedTask.getName() != null) {
            String name = updatedTask.getName().trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Task name must not be blank.");
            }
            existingTask.setName(name);
        }
        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getStatus() != null) {
            existingTask.setStatus(updatedTask.getStatus());
        }

        return taskRepository.save(existingTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Task id must be a positive number.");
        }
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found: " + id);
        }
        taskRepository.deleteById(id);
    }
}
