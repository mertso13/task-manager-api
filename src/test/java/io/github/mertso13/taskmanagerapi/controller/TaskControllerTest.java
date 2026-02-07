package io.github.mertso13.taskmanagerapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import io.github.mertso13.taskmanagerapi.model.Task;
import io.github.mertso13.taskmanagerapi.service.impl.TaskServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
    
    @Mock
    private TaskServiceImpl taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void getAllTasks_ShouldReturnAllTasks_WhenTasksExist() {
        Task task1 = new Task();
        task1.setName("Random name 1");
        Task task2 = new Task();
        task2.setName("Random name 2");
        List<Task> mocTasks = List.of(task1, task2);

        when(taskService.getAllTasks()).thenReturn(mocTasks);

        List<Task> result = taskController.getAllTasks();
        assertEquals(2, result.size());
        assertEquals("Random name 1", result.get(0).getName());
        assertEquals("Random name 2", result.get(1).getName());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void getAllTasks_ShouldReturnEmptyList_WhenNoTasksExist() {
        when(taskService.getAllTasks()).thenReturn(List.of());

        List<Task> result = taskController.getAllTasks();
        assertEquals(0, result.size());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void getTaskByID_ShouldReturnTask_WhenTaskExists() {
        Task task = new Task();
        task.setId(1L);
        task.setName("Random name");

        when(taskService.getTaskByID(1L)).thenReturn(task);

        Task result = taskController.getTaskByID(1L);
        assertEquals(1L, result.getId());
        assertEquals("Random name", result.getName());
        verify(taskService, times(1)).getTaskByID(1L);
    }

    @Test
    void getTaskByID_ShouldReturnNull_WhenTaskDoesNotExist() {
        when(taskService.getTaskByID(99L)).thenReturn(null);

        Task result = taskController.getTaskByID(99L);
        assertEquals(null, result);
        verify(taskService, times(1)).getTaskByID(99L);
    }

    @Test
    void getTaskByID_ShouldThrowException_WhenIdIsNegative() {
        when(taskService.getTaskByID(-1L)).thenThrow(new IllegalArgumentException("ID cannot be negative"));
        assertThrows(IllegalArgumentException.class, () -> taskController.getTaskByID(-1L));
        verify(taskService, times(1)).getTaskByID(-1L);
    }
    
    @Test
    void getTaskByID_ShouldThrowException_WhenIdIsZero() {
        when(taskService.getTaskByID(0L)).thenThrow(new IllegalArgumentException("ID cannot be zero"));
        assertThrows(IllegalArgumentException.class, () -> taskController.getTaskByID(0L));
        verify(taskService, times(1)).getTaskByID(0L);
    }

    @Test
    void getTaskByID_ShouldThrowException_WhenTaskNotFound() {
        when(taskService.getTaskByID(99L)).thenThrow(new RuntimeException("Task not found"));
        assertThrows(RuntimeException.class, () -> taskController.getTaskByID(99L));
        verify(taskService, times(1)).getTaskByID(99L);
    }

    @Test
    void getTaskByID_ShouldThrowException_WhenIdIsNull() {
        when(taskService.getTaskByID(null)).thenThrow(new IllegalArgumentException("ID cannot be null"));
        assertThrows(IllegalArgumentException.class, () -> taskController.getTaskByID(null));
        verify(taskService, times(1)).getTaskByID(null);
    }

    @Test
    void createTask_ShouldCreateAndReturnTask_WhenValidTaskProvided() {
        Task taskToCreate = new Task();
        taskToCreate.setName("New Task");

        Task createdTask = new Task();
        createdTask.setId(1L);
        createdTask.setName("New Task");

        when(taskService.createTask(taskToCreate)).thenReturn(createdTask);

        var response = taskController.createTask(taskToCreate);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdTask, response.getBody());
    }

    @Test
    void createTask_ShouldThrowException_WhenTaskIsNull() {
        when(taskService.createTask(null)).thenThrow(new IllegalArgumentException("Task cannot be null"));
        assertThrows(IllegalArgumentException.class, () -> taskController.createTask(null));
        verify(taskService, times(1)).createTask(null);
    }

    @Test
    void createTask_ShouldThrowException_WhenTaskHasId() {
        Task taskWithId = new Task();
        taskWithId.setId(1L);
        taskWithId.setName("Task with ID");

        when(taskService.createTask(taskWithId)).thenThrow(new IllegalArgumentException("New task cannot have an ID"));
        assertThrows(IllegalArgumentException.class, () -> taskController.createTask(taskWithId));
        verify(taskService, times(1)).createTask(taskWithId);
    }

    @Test
    void createTask_ShouldThrowException_WhenTaskNameIsNull() {
        Task taskWithoutName = new Task();

        when(taskService.createTask(taskWithoutName)).thenThrow(new IllegalArgumentException("Task name cannot be null or empty"));
        assertThrows(IllegalArgumentException.class, () -> taskController.createTask(taskWithoutName));
        verify(taskService, times(1)).createTask(taskWithoutName);
    }

    @Test
    void createTask_ShouldThrowException_WhenTaskNameIsEmpty() {
        Task taskWithEmptyName = new Task();
        taskWithEmptyName.setName("");

        when(taskService.createTask(taskWithEmptyName)).thenThrow(new IllegalArgumentException("Task name cannot be null or empty"));
        assertThrows(IllegalArgumentException.class, () -> taskController.createTask(taskWithEmptyName));
        verify(taskService, times(1)).createTask(taskWithEmptyName);
    }

    @Test
    void updateTask_ShouldUpdateAndReturnTask_WhenValidTaskProvided() {
        Task updatedTask = new Task();
        updatedTask.setName("Updated Task");

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setName("Updated Task");

        when(taskService.updateTask(1L, updatedTask)).thenReturn(savedTask);

        var response = taskController.updateTask(1L, updatedTask);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedTask, response.getBody());
        verify(taskService, times(1)).updateTask(1L, updatedTask);
    }

    @Test
    void updateTask_ShouldThrowException_WhenTaskNotFound() {
        Task updatedTask = new Task();
        updatedTask.setName("Updated Task");

        when(taskService.updateTask(99L, updatedTask)).thenThrow(new RuntimeException("Task not found"));
        assertThrows(RuntimeException.class, () -> taskController.updateTask(99L, updatedTask));
        verify(taskService, times(1)).updateTask(99L, updatedTask);
    }

    @Test
    void deleteTask_ShouldDeleteTask_WhenTaskExists() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setName("Existing Task");

        var response = taskController.deleteTask(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void deleteTask_ShouldThrowException_WhenTaskNotFound() {
        doThrow(new RuntimeException("Task not found")).when(taskService).deleteTask(99L);
        assertThrows(RuntimeException.class, () -> taskController.deleteTask(99L));
        verify(taskService, times(1)).deleteTask(99L);
    }

    @Test
    void deleteTask_ShouldThrowException_WhenIdIsNegative() {
        doThrow(new IllegalArgumentException("ID cannot be negative")).when(taskService).deleteTask(-1L);
        assertThrows(IllegalArgumentException.class, () -> taskController.deleteTask(-1L));
        verify(taskService, times(1)).deleteTask(-1L);
    }

    @Test
    void deleteTask_ShouldThrowException_WhenIdIsZero() {
        doThrow(new IllegalArgumentException("ID cannot be zero")).when(taskService).deleteTask(0L);
        assertThrows(IllegalArgumentException.class, () -> taskController.deleteTask(0L));
        verify(taskService, times(1)).deleteTask(0L);
    }

    @Test
    void deleteTask_ShouldThrowException_WhenIdIsNull() {
        doThrow(new IllegalArgumentException("ID cannot be null")).when(taskService).deleteTask(null);
        assertThrows(IllegalArgumentException.class, () -> taskController.deleteTask(null));
        verify(taskService, times(1)).deleteTask(null);
    }
}
