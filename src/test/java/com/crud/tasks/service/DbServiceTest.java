package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTest {
    @Autowired
    private DbService dbService;

    @MockBean
    TaskRepository taskRepository;

    @Test
    public void shouldSaveTask() {
        // Given
        Task task = new Task(1L,"dbService_test", "testing db service");
        Task savedTask = new Task(123L,"dbService_test", "testing db service");
        when(taskRepository.save(task)).thenReturn(savedTask);
        when(taskRepository.findOne(anyLong())).thenReturn(savedTask);
        // When
        Task returnedTask = dbService.saveTask(task);
        // Then
        long id = returnedTask.getId();
        Optional<Task> readTask = dbService.getTask(id);
        assertEquals(id, readTask.get().getId().longValue());
    }

    @Test
    public void shouldReturnNull() {
        // Given
        when(taskRepository.findOne(anyLong())).thenReturn(null);
        // When
        Optional<Task> readTask = dbService.getTask(100L);
        System.out.println(readTask.orElse(null));
        // Then
        assertFalse(readTask.isPresent());
    }

}