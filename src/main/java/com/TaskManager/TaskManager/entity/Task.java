package com.TaskManager.TaskManager.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name="task")
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="task_description")
    private String taskDescription;

    @Column(name="task_status")
    private String taskStatus;

    @Column(name="task_due")
    private LocalDate taskDue;

    @Column(name="task_priority")
    private String taskPriority;

    @ManyToOne(cascade={CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    public Task(){

    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getTaskDue() {
        return taskDue;
    }

    public void setTaskDue(LocalDate taskDue) {
        this.taskDue = taskDue;
    }
}
