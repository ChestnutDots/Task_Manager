package com.TaskManager.TaskManager.dao;

import com.TaskManager.TaskManager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    void deleteByUserId(int userId);
}
