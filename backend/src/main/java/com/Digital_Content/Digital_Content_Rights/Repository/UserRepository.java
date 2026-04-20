package com.Digital_Content.Digital_Content_Rights.Repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Digital_Content.Digital_Content_Rights.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
