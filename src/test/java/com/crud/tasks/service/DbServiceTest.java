package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTest {
    @Autowired
    private DbService dbService;

    @Test
    public void shouldSaveTask() {
        // Given
        Task task = new Task(100L,"dbService_test", "testing db service");
        // When
        Task returnedTask = dbService.saveTask(task);
        // Then
        long id = returnedTask.getId();
        Optional<Task> savedTask = dbService.getTask(id);
        assertEquals(id, savedTask.get().getId().longValue());

        // Cleanup
        dbService.deleteTask(id);
    }

    @Test
    public void shouldReturnNull() {
        // When
        List<Task> tasks = dbService.getAllTasks();
        long id = tasks.get(tasks.size()-1).getId();
        Optional<Task> readTask = dbService.getTask(id+1);
        // Then
        assertFalse(readTask.isPresent());
    }

}