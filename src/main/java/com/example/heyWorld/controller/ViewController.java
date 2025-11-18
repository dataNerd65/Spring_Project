package com.example.heyWorld.controller;
import com.example.heyWorld.model.Task;
import com.example.heyWorld.repository.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    private final TaskRepository taskRepository;

    public ViewController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks-view")
    public String getTasksPage(Model model){
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasks"; // maps to tasks.html
    }
}
