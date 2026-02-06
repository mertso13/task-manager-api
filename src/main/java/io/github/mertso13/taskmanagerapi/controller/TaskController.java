package io.github.mertso13.taskmanagerapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mertso13.taskmanagerapi.Task;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Task Management", description = "CRUD for Task Management")
public class TaskController {
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
    // TODO: Implement
    return new ArrayList<>();
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
    // TODO: Implement
    return null;
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
    // TODO: Implement
    return new ResponseEntity<>(task, HttpStatus.CREATED);
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
    // TODO:
    // find task in db
    // update task with updatedTask
    // save updated version
    return ResponseEntity.ok(null);
    
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
    // TODO: Implement
    return ResponseEntity.noContent().build();
    }
}
