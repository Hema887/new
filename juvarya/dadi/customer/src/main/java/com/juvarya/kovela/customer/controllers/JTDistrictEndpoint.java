package com.juvarya.kovela.customer.controllers;

import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juvarya.kovela.customer.dto.JTDistrictDTO;
import com.juvarya.kovela.customer.dto.MessageDTO;
import com.juvarya.kovela.customer.model.JTDistrictModel;
import com.juvarya.kovela.customer.model.JTRegionModel;
import com.juvarya.kovela.customer.populator.JTDistrictPopulator;
import com.juvarya.kovela.customer.service.JTDistrictService;
import com.juvarya.kovela.customer.service.JTRegionService;
import com.juvarya.kovela.security.services.CustomerState;
import com.juvarya.kovela.utils.converter.AbstractConverter;
import com.juvarya.kovela.utils.converter.JTBaseEndpoint;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/jtDitrict")
public class JTDistrictEndpoint extends JTBaseEndpoint {

	@Resource(name = "jtDistrictService")
	private JTDistrictService districtService;

	@Resource(name = "jtDistrictPopulator")
	private JTDistrictPopulator districtPopulator;

	@Resource(name = "customerState")
	private CustomerState customerState;

	@Resource(name = "jtRegionService")
	private JTRegionService regionService;

	@SuppressWarnings("unchecked")
	@PostMapping("/save")
	public ResponseEntity saveDistrict(@RequestBody JTDistrictDTO districtDTO)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTRegionModel regionModel = regionService.getById(districtDTO.getRegionId());

		JTDistrictModel districtModel = new JTDistrictModel();
		districtModel.setName(districtDTO.getName());
		districtModel.setIsoCode(districtDTO.getIsoCode());
		if (null != regionModel) {
			districtModel.setRegion(regionModel);
		}
		districtModel = districtService.saveDistrict(districtModel);
		JTDistrictDTO jtDistrictDTO = (JTDistrictDTO) getConverterInstance().convert(districtModel);
		return ResponseEntity.ok().body(jtDistrictDTO);
	}

	@SuppressWarnings("unchecked")
	public AbstractConverter getConverterInstance() {
		return getConverter(districtPopulator, JTDistrictDTO.class.getName());
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/{id}")
	public ResponseEntity findById(@PathVariable("id") Long id)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTDistrictModel districtModel = districtService.findById(id);
		if (Objects.nonNull(districtModel)) {
			JTDistrictDTO jtDistrictDTO = (JTDistrictDTO) getConverterInstance().convert(districtModel);
			return ResponseEntity.ok().body(jtDistrictDTO);
		}
		return ResponseEntity.ok().body(new MessageDTO ("Invalid details"));
	}

	@DeleteMapping("/delete")
	public ResponseEntity delete( Long id) {
		JTDistrictModel districtModel = districtService.findById(id);
		if (Objects.nonNull(districtModel)) {
			districtService.deleteById(districtModel.getId());
			return ResponseEntity.ok().body(new MessageDTO("details deleted successfully"));
		}
		return ResponseEntity.ok().body(new MessageDTO("Invalid details"));
	}

	@SuppressWarnings("unchecked")
	@PutMapping("/update")
	public ResponseEntity update(@RequestBody JTDistrictDTO districtDTO)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTDistrictModel districtModel = districtService.findById(districtDTO.getId());
		if (Objects.nonNull(districtModel)) {
			districtModel.setIsoCode(districtDTO.getIsoCode());
			districtModel.setName(districtDTO.getName());
			districtModel = districtService.saveDistrict(districtModel);
			JTDistrictDTO jtDistrictDTO = (JTDistrictDTO) getConverterInstance().convert(districtModel);
			return ResponseEntity.ok().body(jtDistrictDTO);
		}
		return ResponseEntity.ok().body(new MessageDTO("Invalid details"));
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/isoCode")
	public ResponseEntity findByIsoCode(String isoCode)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTDistrictModel districtModel = districtService.findByIsoCode(isoCode);
		if (Objects.nonNull(districtModel)) {
			JTDistrictDTO jtDistrictDTO = (JTDistrictDTO) getConverterInstance().convert(districtModel);
			return ResponseEntity.ok().body(jtDistrictDTO);
		}
		return ResponseEntity.ok().body(new MessageDTO("Invalid details"));
	}

}
