package com.juvarya.kovela.customer.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.juvarya.kovela.customer.dto.JTDesignationDTO;
import com.juvarya.kovela.customer.dto.JTDesignationListDTO;
import com.juvarya.kovela.customer.model.JTDesignationModel;
import com.juvarya.kovela.customer.populator.JTDesignationPopulator;
import com.juvarya.kovela.customer.service.impl.JTDesignationServiceImpl;
import com.juvarya.kovela.utils.converter.AbstractConverter;
import com.juvarya.kovela.utils.converter.JTBaseEndpoint;

@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/jtdesignation")
public class JTDesignationEndpoint extends JTBaseEndpoint {

	@Resource(name = "jtDesignationService")
	private JTDesignationServiceImpl designationService;

	@Resource(name = "jtDesignationPopulator")
	private JTDesignationPopulator designationPopulator;

	@SuppressWarnings("unchecked")
	@PostMapping("/save")
	public JTDesignationDTO save(@RequestBody JTDesignationDTO jtDesignationDTO)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTDesignationModel designation = new JTDesignationModel();
		designation.setName(jtDesignationDTO.getName());
		designation.setCode(jtDesignationDTO.getCode());
		designation = designationService.saveDesignation(designation);
		return (JTDesignationDTO) getConverterInstance().convert(designation);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/list")
	public JTDesignationListDTO getAllDesginations() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		List<JTDesignationModel> designations = designationService.getDesignationList();
		
		List<JTDesignationDTO> desigantionDto=new ArrayList<>();
		for (JTDesignationModel designation : designations) {
			JTDesignationDTO designationDTO =(JTDesignationDTO) getConverterInstance().convert(designation);
			desigantionDto.add(designationDTO);
		}
		JTDesignationListDTO designationListDTO = new JTDesignationListDTO();
		designationListDTO.setDesignations(desigantionDto);
		return designationListDTO;
	}
	
	@DeleteMapping("/{designationId}")
	public void removeDesignation(@PathVariable("designationId") Long designationId) {
		designationService.removeDesignation(designationId);
	}

	@SuppressWarnings({ "unchecked" })
	private AbstractConverter getConverterInstance() {
		return getConverter(designationPopulator, JTDesignationDTO.class.getName());
	}
}