package com.TaskManager.TaskManager.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    // define fields

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="username", unique=true)
    private String username;

    @Column(name="password")
    private String password;

    @OneToMany(mappedBy="user",
            fetch=FetchType.LAZY,
            cascade={CascadeType.REFRESH, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.PERSIST})
    private List<Task> tasks;

    // define constructors
    public User(){

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    // add convenience methods for bi-directional relationship:

    public void add(Task tempTask){
        if(tasks==null){
            tasks=new ArrayList();
        }

        tasks.add(tempTask);

        tempTask.setUser(this);
    }

    // add toString()

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                '}';
    }
}
