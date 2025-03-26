package com.TaskManager.TaskManager.service;

import com.TaskManager.TaskManager.dao.TaskRepository;
import com.TaskManager.TaskManager.entity.Task;
import com.TaskManager.TaskManager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for task-related operations.
 */
@Service
public class TaskServiceImplementation implements TaskService{

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImplementation(TaskRepository theTaskRepository){
        taskRepository= theTaskRepository;
    }

    /**
     * Retrieves the task by its id.
     * Throws a RuntimeException if the task is not found.
     */
    @Override
    public Task findById(int theId) {
        Optional<Task> result = taskRepository.findById(theId);

        Task theTask;

        if(result.isPresent()){
            theTask=result.get();
        }
        else{
            throw new RuntimeException("Did not find task id - "+theId);
        }

        return theTask;
    }

    @Override
    public Task save(Task theTask) {
        return taskRepository.save(theTask);
    }

    @Override
    public void deleteById(int theId) {
        taskRepository.deleteById(theId);
    }

    @Override
    public List<Task> findAllByUser(User user) {
        return taskRepository.findAllByUser(user);
    }

}
