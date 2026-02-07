package io.github.mertso13.taskmanagerapi.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.mertso13.taskmanagerapi.model.Task;
import io.github.mertso13.taskmanagerapi.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void getAllTasks_ShouldReturnAllTasks_WhenTasksExist() {
        Task task1 = new Task();
        task1.setName("Random name 1");

        Task task2 = new Task();
        task2.setName("Random name 2");

        List<Task> mocTasks = List.of(task1, task2);

        when(taskRepository.findAll()).thenReturn(mocTasks);

        List<Task> result = taskService.getAllTasks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Random name 1", result.get(0).getName());
        assertEquals("Random name 2", result.get(1).getName());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getAllTasks_ShouldReturnEmptyList_WhenNoTasksExist() {
        when(taskRepository.findAll()).thenReturn(List.of());

        List<Task> result = taskService.getAllTasks();

        assertNotNull(result);
        assertEquals(0, result.size());

        verify(taskRepository, times(1)).findAll();
    }
    
    @Test
    void getTaskByID_ShouldReturnTask_WhenTaskExists() {
        Task mocTask = new Task();
        mocTask.setId(1L);
        mocTask.setName("Some random task name");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(mocTask));

        Task result = taskService.getTaskByID(1L);

        assertNotNull(result);
        assertEquals("Some random task name", result.getName());
    }

    @Test
    void getTaskByID_ShouldThrowException_WhenTaskNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.getTaskByID(99L));
    }

    @Test
    void createTask_ShouldCreateAndReturnTask_WhenValidTaskProvided() {
        Task taskToCreate = new Task();
        taskToCreate.setName("New Task");

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setName("New Task");

        when(taskRepository.save(taskToCreate)).thenReturn(savedTask);

        Task result = taskService.createTask(taskToCreate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Task", result.getName());

        verify(taskRepository, times(1)).save(taskToCreate);
    }

    @Test
    void createTask_ShouldThrowException_WhenTaskIsNull() {
        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(null));
    }

    @Test
    void createTask_ShouldThrowException_WhenTaskHasId() {
        Task taskWithId = new Task();
        taskWithId.setId(1L);
        taskWithId.setName("Task with ID");
        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(taskWithId));
    }

    @Test
    void createTask_ShouldThrowException_WhenTaskNameIsNull() {
        Task taskWithoutName = new Task();
        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(taskWithoutName));
    }

    @Test
    void createTask_ShouldThrowException_WhenTaskNameIsEmpty() {
        Task taskWithEmptyName = new Task();
        taskWithEmptyName.setName("   ");
        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(taskWithEmptyName));
    }

    @Test
    void updateTask_ShouldUpdateAndReturnTask_WhenValidTaskProvided() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setName("Existing Task");

        Task updatedTask = new Task();
        updatedTask.setName("Updated Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated Task", result.getName());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    void updateTask_ShouldThrowException_WhenTaskNotFound() {
        Task updatedTask = new Task();
        updatedTask.setName("Updated Task");

        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.updateTask(99L, updatedTask));
    }

    @Test
    void updateTask_ShouldThrowException_WhenUpdatedTaskIsNull() {
        assertThrows(IllegalArgumentException.class, () -> taskService.updateTask(1L, null));
    }

    @Test
    void updateTask_ShouldThrowException_WhenIdIsNull() {
        Task updatedTask = new Task();
        updatedTask.setName("Updated Task");
        assertThrows(IllegalArgumentException.class, () -> taskService.updateTask(null, updatedTask));
    }

    @Test
    void updateTask_ShouldThrowException_WhenIdIsNegative() {
        Task updatedTask = new Task();
        updatedTask.setName("Updated Task");
        assertThrows(IllegalArgumentException.class, () -> taskService.updateTask(-1L, updatedTask));
    }

    @Test
    void deleteTask_ShouldDeleteTask_WhenTaskExists() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setName("Existing Task");

        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTask_ShouldThrowException_WhenTaskNotFound() {
        when(taskRepository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> taskService.deleteTask(99L));
    }

    @Test
    void deleteTask_ShouldThrowException_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask(null));
    }

    @Test
    void deleteTask_ShouldThrowException_WhenIdIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask(-1L));
    }

    @Test
    void deleteTask_ShouldThrowException_WhenIdIsZero() {
        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask(0L));
    }

}
