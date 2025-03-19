package com.TaskManager.TaskManager.service;

import com.TaskManager.TaskManager.entity.Task;
import com.TaskManager.TaskManager.entity.User;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    Task findById(int theId);

    Task save(Task theTask);

    void deleteById(int theId);

    List<Task> findAllByUser(User user);
}
