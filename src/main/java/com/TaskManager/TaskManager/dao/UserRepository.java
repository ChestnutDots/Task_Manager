package com.TaskManager.TaskManager.dao;

import com.TaskManager.TaskManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    /**
     * Find all users who have the 'ROLE_USER' role
     * but do not hav 'ROLE_ADMIN' role.
     */
    @Query(value="""
    SELECT u.*
    FROM users u
    JOIN roles r ON u.id = r.user_id
    WHERE r.role='ROLE_USER'
    AND NOT EXISTS(
        SELECT 1 FROM roles r2
                 WHERE r2.user_id=u.id AND r2.role='ROLE_ADMIN'
    )
    """, nativeQuery=true)
    List<User> findAllNonAdminUsers();
}
