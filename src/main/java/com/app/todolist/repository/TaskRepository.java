package com.app.todolist.repository;

import com.app.todolist.model.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByUserId(String userId);
}
