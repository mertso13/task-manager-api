package io.github.mertso13.taskmanagerapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mertso13.taskmanagerapi.model.Task;
import io.github.mertso13.taskmanagerapi.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management", description = "CRUD for Task Management")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @Operation(summary = "Get all tasks", description = "Returns all the tasks in the DB.")
    @ApiResponse(
        responseCode = "200", 
        description = "Successful",
        content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = Task.class))
    )
)
public List<Task> getAllTasks() {
    return taskService.getAllTasks();
    }

@GetMapping("/{id}")
@Operation(summary = "Fetch task by ID")
@ApiResponse(
    responseCode = "200",
    description = "Task found"
)
@ApiResponse(
    responseCode = "404",
    description = "Task couldn't found"
)
public Task getTaskByID(@PathVariable Long id){
    return taskService.getTaskByID(id);
    }

@PostMapping
@Operation(summary = "Create new task")
@ApiResponse(
    responseCode = "201",
    description = "Task created"
)
@ApiResponse(
    responseCode = "400",
    description = "Invalid input"
)
public ResponseEntity<Task> createTask(@RequestBody Task task) {
    Task createdTask = taskService.createTask(task);
    return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

@PutMapping("/{id}")
@Operation(summary = "Update the task")
@ApiResponse(
    responseCode = "200",
    description = "Task updated"
)
@ApiResponse(
    responseCode = "404",
    description = "Task couldn't found"
)
public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
    Task savedTask = taskService.updateTask(id, updatedTask);
    return ResponseEntity.ok(savedTask);
    
    }

@DeleteMapping("/{id}")
@Operation(summary = "Delete the task")
@ApiResponse(
    responseCode = "204",
    description = "Task deleted"
)
@ApiResponse(
    responseCode = "404",
    description = "Task couldn't found"
)
public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();
    }
}
