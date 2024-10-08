package com.juvarya.kovela.customer.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.juvarya.kovela.customer.model.JTDesignationModel;
import com.juvarya.kovela.customer.repository.JTDesignationRepository;
import com.juvarya.kovela.customer.service.JTDesignationService;

@Service("jtDesignationService")
public class JTDesignationServiceImpl implements JTDesignationService{
	
	@Resource(name = "jtDesignationRepository")
	private JTDesignationRepository designationRepository;
	
	@Override
	@Transactional
	public JTDesignationModel saveDesignation(JTDesignationModel designation) {
		return designationRepository.save(designation);
	}
	
	@Override
	public List<JTDesignationModel> getDesignationList(){
		return  designationRepository.findAll();
	}

	@Override
	@Transactional
	public void removeDesignation(Long designationId) {
		designationRepository.delete(designationRepository.findById(designationId).get());
	}
}