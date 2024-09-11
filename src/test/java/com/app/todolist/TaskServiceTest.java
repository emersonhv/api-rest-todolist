package com.app.todolist;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.app.todolist.model.entity.task.Task;
import com.app.todolist.repository.TaskRepository;
import com.app.todolist.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private List<Task> taskList;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .id("1")
                .description("Test Task")
                .build();

        taskList = new ArrayList<>();
        taskList.add(task);
    }

    @Test
    void testSaveTask() {
        when(taskRepository.save(task)).thenReturn(task);

        Task savedTask = taskService.saveTask(task);

        assertNotNull(savedTask);
        assertEquals("1", savedTask.getId());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testListTaskByUser() {
        when(taskRepository.findByUserId("user1")).thenReturn(taskList);

        List<Task> tasks = taskService.listTaskByUser("user1");

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("1", tasks.get(0).getId());
        verify(taskRepository, times(1)).findByUserId("user1");
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).delete(any(Task.class));

        taskService.deleteTask("1");

        verify(taskRepository, times(1)).delete(any(Task.class));
    }
}
