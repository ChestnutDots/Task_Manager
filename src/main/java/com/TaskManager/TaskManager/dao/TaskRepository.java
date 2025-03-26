package com.TaskManager.TaskManager.dao;

import com.TaskManager.TaskManager.entity.Task;
import com.TaskManager.TaskManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task>findAllByUser(User user);

    void deleteByUserId(int userId);
}
