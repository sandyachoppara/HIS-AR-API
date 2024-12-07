package com.his.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.his.entity.ApplicationReg;

public interface ApplicationRegRepository extends JpaRepository<ApplicationReg, Integer> {
	
	public ApplicationReg findByCitizenId(Integer citizenId);

}
