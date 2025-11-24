package com.example.heyWorld.controller;

import com.example.heyWorld.model.Task;
import com.example.heyWorld.repository.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/tasks")
@CrossOrigin // allow UI to access this API

public class PageController {
    private final TaskRepository repository;

    public PageController(TaskRepository repository){
        this.repository = repository;
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id){
        repository.deleteById(id);
        return "redirect:/tasks-view";
    }
    @GetMapping("/complete/{id}")
    public String completeTask(@PathVariable Long id){
        Task task = repository.findById(id).orElse(null);
        if(task != null){
            task.setCompleted(true);
            repository.save(task);
        }
        return "redirect:/tasks-view";
    }
    @PostMapping("/add")
    public String addTask(@RequestParam String description) {
        Task task = new Task();
        task.setDescription(description);
        task.setCompleted(false);
        repository.save(task);
        return "redirect:/tasks-view";
    }


}
