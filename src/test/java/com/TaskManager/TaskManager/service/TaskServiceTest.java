package com.TaskManager.TaskManager.service;

import com.TaskManager.TaskManager.dao.TaskRepository;
import com.TaskManager.TaskManager.entity.Task;
import com.TaskManager.TaskManager.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImplementation taskServiceImplementation;

    @Test
    public void testSaveTask(){

        Task theTask = new Task();
        theTask.setTaskDescription("Test task");
        theTask.setTaskStatus("TO DO");

        when(taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(theTask);

        Task result=taskServiceImplementation.save(theTask);

        assertEquals("Test task", result.getTaskDescription());
        verify(taskRepository, times(1)).save(theTask);
    }

    @Test
    public void testFindById_Found(){

        int theId=20;

        Task theTask= new Task();
        theTask.setId(theId);

        when(taskRepository.findById(ArgumentMatchers.any(Integer.class))).thenReturn(Optional.of(theTask));

        Task result=taskServiceImplementation.findById(theId);

        assertEquals(20, result.getId());
        verify(taskRepository, times(1)).findById(ArgumentMatchers.eq(theId));
    }

    @Test
    public void testFindById_NotFound(){

        int theId=99;

        when(taskRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            taskServiceImplementation.findById(theId);
        });

        assertEquals("Did not find task id - 99", exception.getMessage());
        verify(taskRepository, times(1)).findById(any(Integer.class));
    }

    @Test
    public void testDeleteById_Exists(){

        int theId=12;

        taskServiceImplementation.deleteById(theId);

        verify(taskRepository, times(1)).deleteById(theId);

    }

    @Test
    public void testDeleteById_NotExists(){

        int theId=10;

        taskServiceImplementation.deleteById(theId);

        verify(taskRepository, times(1)).deleteById(theId);
        verify(taskRepository, times(0)).findById(theId);
    }

    @Test
    public void testFindAllByUser(){

        User theUser = new User();

        Task task1= new Task();
        task1.setTaskDescription("Go for a walk");
        Task task2= new Task();
        task2.setTaskDescription("Play guitar");

        theUser.add(task1);
        theUser.add(task2);

        List<Task> testList= new ArrayList<>();
        testList.add(task1);
        testList.add(task2);

        when(taskRepository.findAllByUser(ArgumentMatchers.any(User.class))).thenReturn(testList);

        List<Task> result=taskServiceImplementation.findAllByUser(theUser);

        assertEquals(testList, result);
        assertEquals(2, result.size()); // Check that exactly two tasks are returned
        assertEquals("Go for a walk", result.get(0).getTaskDescription());
        assertEquals("Play guitar", result.get(1).getTaskDescription());

        verify(taskRepository, times(1)).findAllByUser(theUser);
    }

    @Test
    public void testFindAllByUser_NoTasks(){
        User theUser = new User();

        when(taskRepository.findAllByUser(theUser)).thenReturn(Collections.emptyList());

        List<Task> result = taskServiceImplementation.findAllByUser(theUser);

        assertEquals(0, result.size());
        verify(taskRepository, times(1)).findAllByUser(theUser);

    }


}
