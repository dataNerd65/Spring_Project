package com.example.heyWorld.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Task{
    public enum Priority{
        LOW, MEDIUM, HIGH;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String title;

    private String description;
    //private LocalDate dueDate;
    private boolean completed = false;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;

    public Task(){}

    public Task(String title,String description, Priority priority){
        this.title = title;
        this.description = description;
        this.priority = priority;
    }
    // Getters and setters
    public long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public Priority getPriority(){
        return priority;
    }
    public boolean isCompleted(){
        return completed;
    }

    // setters
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setCompleted(boolean completed){
        this.completed = completed;
    }
    public void setPriority(Priority priority){
        this.priority = priority;
    }
}



