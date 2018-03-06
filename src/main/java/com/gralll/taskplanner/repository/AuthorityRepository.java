package com.gralll.taskplanner.repository;

import com.gralll.taskplanner.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String>{
}