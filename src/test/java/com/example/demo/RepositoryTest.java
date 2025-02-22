package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryTest extends JpaRepository<User, Integer> {

    //User findByApplicationId(int applicationId);
}
