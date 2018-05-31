package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaskMapperTest {
    @Test
    public void testMapToTask() {
        // Given
        TaskDto taskDto = new TaskDto(1L, "test_task", "testing");
        TaskMapper taskMapper = new TaskMapper();
        // When
        Task mappedTask = taskMapper.mapToTask(taskDto);
        // Then
        assertEquals(new Long(1), mappedTask.getId());
        assertEquals("test_task", mappedTask.getTitle());
        assertEquals("testing", mappedTask.getContent());
    }

    @Test
    public void testMapToTaskDto() {
        // Given
        Task task = new Task(1L, "test_task", "testing");
        TaskMapper taskMapper = new TaskMapper();
        // When
        TaskDto mappedTaskDto = taskMapper.mapToTaskDto(task);
        // Then
        assertEquals(new Long(1), mappedTaskDto.getId());
        assertEquals("test_task", mappedTaskDto.getTitle());
        assertEquals("testing", mappedTaskDto.getContent());
    }
}