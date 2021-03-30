package com.ssingh.covid19.service;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.ssingh.covid19.annotation.ValidStateCode;
import com.ssingh.covid19.dto.CaseDTO;
@Validated
public interface CaseService {

	boolean addNewStateUpdate(@Valid CaseDTO caseDto);

	CaseDTO fetchStateCase(@ValidStateCode String code);

	CaseDTO updateStateCase(@Valid CaseDTO newStateCase);

}
