package com.ssingh.covid19.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssingh.covid19.entity.StateBO;

public interface StateRepository extends JpaRepository<StateBO, Long> {
	Optional<StateBO> findByStateCode(String stateCode);
}
