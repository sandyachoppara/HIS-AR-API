package com.his.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.his.dto.ApplicationRegDTO;
import com.his.dto.SSARequest;
import com.his.dto.SSAResponse;
import com.his.entity.ApplicationReg;
import com.his.repository.ApplicationRegRepository;

@Service
public class ApplicationRegServiceImpl implements ApplicationRegService {
	
	@Autowired
	ApplicationRegRepository applicationRegRepo;
	Logger logger= LoggerFactory.getLogger(ApplicationRegServiceImpl.class);
	
	@Value("${apiUrl}")
	private String apiUrl;
	
	@Value("${plainCreds}")
	private String plainCreds;
	
	@Value("${eligibleState}")
	private String eligibleState;
	
	@Override
	public boolean submitApplication(ApplicationRegDTO appDto) {
		ApplicationReg applicationReg =new ApplicationReg();
		SSARequest ssaRequest=new  SSARequest();
		ssaRequest.setName(appDto.getName());
		ssaRequest.setSsn(appDto.getSsn());
		ssaRequest.setDob(appDto.getDob());
		logger.info("Calling third party API for the state............");
		String state = getState(ssaRequest);
		logger.info("Got the state from third Party API ............");
		//if(state.equalsIgnoreCase("Rohde Island")) {
		if(state.equalsIgnoreCase(eligibleState)) {
		BeanUtils.copyProperties(appDto, applicationReg);
		applicationRegRepo.save(applicationReg);
		return true;
		}
		return false;
	}

	private String getState(SSARequest ssaRequest) {
		
		String state = new String();
		//String plainCreds="root:root@123";
		byte[] plainCredsByte=plainCreds.getBytes();
		String encodeBase64String = Base64.encodeBase64String(plainCredsByte);
		HttpHeaders headers= new HttpHeaders();
		headers.add("Authorization", "Basic "+ encodeBase64String);
		
		HttpEntity<SSARequest> requestEntity = new HttpEntity<>(ssaRequest, headers);
		//String apiUrl="http://localhost:8080/getstate";	
		
		RestTemplate rt=new RestTemplate();
		ResponseEntity<SSAResponse> responseEntity = 
					rt.exchange(apiUrl,HttpMethod.POST,requestEntity,SSAResponse.class);
		
		state = responseEntity.getBody().getStateName();
		return state;
	}

	@Override
	public ApplicationRegDTO getApplication(Integer appnumber) {
		ApplicationReg entity= applicationRegRepo.findById(appnumber).orElseThrow();
		ApplicationRegDTO dto= new ApplicationRegDTO();
		if(entity!=null) {
			BeanUtils.copyProperties(entity, dto);
		 	return dto;
		}
		return null;
	}

	@Override
	public List<ApplicationRegDTO> getApplications() {
		List<ApplicationReg> entities= applicationRegRepo.findAll();
		List<ApplicationRegDTO> dtos = new ArrayList<ApplicationRegDTO>();
		entities.stream().forEach( entity ->{
			ApplicationRegDTO dto= new ApplicationRegDTO();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		});
		return dtos;
	}

	@Override
	public ApplicationRegDTO getApplicationByCitizenId(Integer citizenId) {
		ApplicationReg entity= applicationRegRepo.findByCitizenId(citizenId);
		ApplicationRegDTO dto= new ApplicationRegDTO();
		if(entity!=null) {
			BeanUtils.copyProperties(entity, dto);
		 	return dto;
		}
		return null;
	}
}
