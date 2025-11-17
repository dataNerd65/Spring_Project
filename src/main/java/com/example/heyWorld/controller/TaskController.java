package com.example.heyWorld.controller;

import com.example.heyWorld.model.Task;
import com.example.heyWorld.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin // allow UI to access this API

public class TaskController {
    private final TaskRepository repository;

    public TaskController(TaskRepository repository){
        this.repository = repository;
    }
    @GetMapping
    public List<Task> getAllTasks(){
        return repository.findAll();
    }
    @PostMapping
    public Task addTask(@RequestBody Task task){
        return repository.save(task);
    }
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id){
        Task task = repository.findById(id).orElseThrow();
        task.setCompleted(true);
        return repository.save(task);
    }
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        repository.deleteById(id);
    }
}