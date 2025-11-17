package com.example.heyWorld.repository;

import com.example.heyWorld.model.Task;

import org.springframework.data.jpa.repository.JpaRepository;
public interface TaskRepository extends JpaRepository<Task, Long>{

}