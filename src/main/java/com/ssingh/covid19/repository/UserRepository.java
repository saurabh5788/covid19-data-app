package com.ssingh.covid19.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssingh.covid19.entity.UserBO;

public interface UserRepository extends JpaRepository<UserBO, Long> {
	Optional<UserBO> findByUsername(String username);
}