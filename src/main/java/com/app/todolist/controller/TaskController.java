package com.app.todolist.controller;

import com.app.todolist.model.dto.TaskDto;
import com.app.todolist.model.entity.task.State;
import com.app.todolist.model.entity.task.Task;
import com.app.todolist.service.TaskService;
import com.app.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    private Map<String, String> response;

    @PutMapping(value = "task")
    public ResponseEntity<?> updateTask(@RequestBody TaskDto request) {
        try {
            Task newTask = Task.builder()
                    .id(request.getId())
                    .name(request.getName())
                    .description(request.getDescription())
                    .state(State.findById(request.getState()))
                    .user(userService.getUserByUsername(request.getUser()).orElseThrow())
                    .build();

            taskService.saveTask(newTask);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception exception) {
            response = new HashMap<>();
            response.put("message", "Error al actualizar estado!");
            response.put("token", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "task")
    public ResponseEntity<?> newTask(@RequestBody TaskDto request) {
        try {
            Task newTask = Task.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .state(State.findById(request.getState()))
                    .user(userService.getUserByUsername(request.getUser()).orElseThrow())
                    .build();

            taskService.saveTask(newTask);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception exception) {
            response = new HashMap<>();
            response.put("message", "Error al crear un nueva tarea!");
            response.put("token", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "task/{userid}")
    public ResponseEntity<?> getTaskByUserId(@PathVariable String userid) {
        try {
            List<Task> tasksByUser = taskService.listTaskByUser(userid);
            return new ResponseEntity<>(tasksByUser, HttpStatus.OK);
        } catch (Exception exception) {
            response = new HashMap<>();
            response.put("message", "Error interno del servidor");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "task/{taskid}")
    public ResponseEntity<?> deleteTask(@PathVariable String taskid) {
        try {
            taskService.deleteTask(taskid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            response = new HashMap<>();
            response.put("message", "Error interno del servidor");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
