package com.farmexercise.Repository;

import com.farmexercise.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
