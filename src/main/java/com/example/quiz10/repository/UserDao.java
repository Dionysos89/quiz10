package com.example.quiz10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz10.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, String> {

}
