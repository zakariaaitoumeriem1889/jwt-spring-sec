package org.sid.jwtspringsec.controller;

import org.sid.jwtspringsec.entity.Task;
import org.sid.jwtspringsec.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestRestController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    private List<Task> listTask() {
        return taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public Task save(@RequestBody Task task) {
        return taskRepository.save(task);
    }


}
