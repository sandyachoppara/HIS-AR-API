package com.his.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.his.dto.ApplicationRegDTO;
import com.his.service.ApplicationRegService;

@RestController
@RefreshScope
public class ApplicationRegController {

	@Autowired
	ApplicationRegService applicationRegService;
	Logger logger= LoggerFactory.getLogger(ApplicationRegController.class);
	
	@PostMapping("/application")
	public ResponseEntity<String> submitApplication(@RequestBody ApplicationRegDTO applicationRegDTO){
		logger.info("Application Registration Started");
		boolean status = applicationRegService.submitApplication(applicationRegDTO);
		if(status) {
			logger.info("Application Registration Completed");
			return new ResponseEntity<>("Your application is submitted successfullly", HttpStatus.OK);
		}
		return new ResponseEntity<>("Your are not eligible for this plan", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/applications")
	public ResponseEntity<List<ApplicationRegDTO>> getApplications(){
		List<ApplicationRegDTO> applications = applicationRegService.getApplications();
		if(!applications.isEmpty())
		return new ResponseEntity<List<ApplicationRegDTO>>(applications, HttpStatus.OK);
		return new ResponseEntity<List<ApplicationRegDTO>>(applications, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/application/{appNumber}")
	public ResponseEntity<ApplicationRegDTO> getApplication(@PathVariable Integer appNumber){
		ApplicationRegDTO application= applicationRegService.getApplication(appNumber);
		if(application!=null)
		return new ResponseEntity<ApplicationRegDTO>(application, HttpStatus.OK);
		return new ResponseEntity<ApplicationRegDTO>(application, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/application-citizen/{citizenId}")
	public ResponseEntity<ApplicationRegDTO> getApplicationByCitizenId(@PathVariable Integer citizenId){
		ApplicationRegDTO application= applicationRegService.getApplicationByCitizenId(citizenId);
		if(application!=null)
			return new ResponseEntity<ApplicationRegDTO>(application, HttpStatus.OK);
			return new ResponseEntity<ApplicationRegDTO>(application, HttpStatus.NOT_FOUND);

	}
	
}
