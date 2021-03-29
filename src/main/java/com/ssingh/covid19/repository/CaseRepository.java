package com.ssingh.covid19.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssingh.covid19.dto.CaseDTO;
import com.ssingh.covid19.entity.CaseBO;

public interface CaseRepository extends JpaRepository<CaseBO, Long> {
	
	/*@Query("select sc from StateCaseBO sc"
			+ " JOIN sc.state st"
			+ " where st.stateCode = ?1")*/
	Optional<CaseBO> findByStateStateCode(String stateCode);
	
	/*@Query("select new com.ssingh.covid19.dto.CaseDTO"
			+ " (sc.id, st.stateCode, sc.caseCount.activeCases, sc.caseCount.deathCases, sc.caseCount.recoveredCases)"
			+ " from CaseBO sc JOIN sc.state st where st.stateCode = ?1")
	Optional<CaseDTO> findByStateCode(String stateCode);*/
}
