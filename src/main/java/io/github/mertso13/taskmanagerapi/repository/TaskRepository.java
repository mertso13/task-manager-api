package io.github.mertso13.taskmanagerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.mertso13.taskmanagerapi.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
