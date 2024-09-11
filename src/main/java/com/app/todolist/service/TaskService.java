package com.app.todolist.service;

import com.app.todolist.model.entity.task.Task;
import com.app.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
    @Transactional(readOnly = true)
    public List<Task> listTaskByUser(String userId) {
        return taskRepository.findByUserId(userId);
    }
    @Transactional
    public void deleteTask(String taskId) {
        taskRepository.delete(Task.builder().id(taskId).build());
    }
}
