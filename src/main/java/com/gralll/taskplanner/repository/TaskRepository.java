package com.gralll.taskplanner.repository;

import com.gralll.taskplanner.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long>{

    @Query("select task from Task task where task.user.login = ?1")
    Page<Task> findByUserLogin(String login, Pageable pageable);

    Page<Task> findByUserId(Long userId, Pageable pageable);
}