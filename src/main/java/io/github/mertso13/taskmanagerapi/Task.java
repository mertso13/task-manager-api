package io.github.mertso13.taskmanagerapi;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Task {
    private Long id;
    private String name;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt = LocalDateTime.now();
}
