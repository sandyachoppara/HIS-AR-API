package com.his.service;

import java.util.List;

import com.his.dto.ApplicationRegDTO;

public interface ApplicationRegService {
	
	public boolean submitApplication(ApplicationRegDTO  app );
	public ApplicationRegDTO getApplication(Integer appnumber);
	public List<ApplicationRegDTO> getApplications();
	public ApplicationRegDTO getApplicationByCitizenId(Integer citizenId);
	

}
