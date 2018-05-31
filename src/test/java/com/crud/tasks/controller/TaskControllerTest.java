package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskController taskController;

    @Test
    public void shouldGetTasks() throws Exception {
        // Given
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto(1L, "Test Task", "test content"));
        when(taskController.getTasks()).thenReturn(tasks);
        // When & Than
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test Task")))
                .andExpect(jsonPath("$[0].content", is("test content")));

    }

    @Test
    public void shouldGetTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(123L, "Test Task", "test content");
        when(taskController.getTask(any(Long.class))).thenReturn(taskDto);
        // When & Than
        mockMvc.perform(get("/v1/task/getTask?taskId=123").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(123)))
                .andExpect(jsonPath("$.title", is("Test Task")))
                .andExpect(jsonPath("$.content", is("test content")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        // Given
        doNothing().when(taskController).deleteTask(any(Long.class));
        // When & Than
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(123L, "Test Task", "test content");
        doNothing().when(taskController).createTask(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When & Than
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(1L, "Test Task", "test content");
        TaskDto createdTaskDto = new TaskDto(123L, "Test Task", "test content");
        when(taskController.updateTask(any(TaskDto.class))).thenReturn(createdTaskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When & Than
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(123)))
                .andExpect(jsonPath("$.title", is("Test Task")))
                .andExpect(jsonPath("$.content", is("test content")));
    }
}