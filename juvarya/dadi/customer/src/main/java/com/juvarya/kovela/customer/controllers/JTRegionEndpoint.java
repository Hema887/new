package com.juvarya.kovela.customer.controllers;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juvarya.kovela.customer.dto.JTRegionDTO;
import com.juvarya.kovela.customer.model.JTCountryModel;
import com.juvarya.kovela.customer.model.JTRegionModel;
import com.juvarya.kovela.customer.populator.JTRegionPopulator;
import com.juvarya.kovela.customer.response.MessageResponse;
import com.juvarya.kovela.customer.service.JTCountryService;
import com.juvarya.kovela.customer.service.JTRegionService;
import com.juvarya.kovela.utils.converter.AbstractConverter;
import com.juvarya.kovela.utils.converter.JTBaseEndpoint;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(value = "/jtregion")
public class JTRegionEndpoint extends JTBaseEndpoint {

	@Resource(name = "jtRegionService")
	private JTRegionService jtRegionService;

	@Resource(name = "jtCountryService")
	private JTCountryService countryService;

	@Resource(name = "jtRegionPopulator")
	private JTRegionPopulator jtRegionPopulator;

	@SuppressWarnings("unchecked")
	@PostMapping("/save")
	public ResponseEntity saveRegion(@RequestBody JTRegionDTO jtRegionDTO)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTRegionModel jtRegionModel = new JTRegionModel();
		jtRegionModel.setId(jtRegionDTO.getId());
		jtRegionModel.setIsoCode(jtRegionDTO.getIsoCode());
		jtRegionModel.setName(jtRegionDTO.getName());
		Optional<JTCountryModel> countryModel = countryService.findById(jtRegionDTO.getCountryId());
		if (countryModel.isPresent()) {
			JTCountryModel jtCountryModel = countryModel.get();
			jtRegionModel.setCountry(jtCountryModel);
		}

		jtRegionModel = jtRegionService.saveRegion(jtRegionModel);
		JTRegionDTO regionDTO = (JTRegionDTO) getConverterInstance().convert(jtRegionModel);
		return ResponseEntity.ok().body(regionDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity findById(@PathVariable("id") Long id) {
		Optional<JTRegionModel> jtRegionModel = jtRegionService.findById(id);
		if (jtRegionModel.isPresent()) {
			JTRegionModel regionModel = jtRegionModel.get();
			return ResponseEntity.ok().body(regionModel);
		}
		return ResponseEntity.ok().body(new MessageResponse("Region Not Found With Given Id"));
	}

	@GetMapping("/isocode")
	public ResponseEntity findByIsoCode(@RequestParam String isoCode) {
		JTRegionModel jtRegionModel = jtRegionService.findByIsoCodeContainsIgnoreCase(isoCode);
		if (null != jtRegionModel) {
			return ResponseEntity.ok().body(jtRegionModel);
		}
		return ResponseEntity.ok().body(new MessageResponse("Region Not Found With Given isocode"));
	}

	@PutMapping("/update")
	public ResponseEntity updateRegion(@RequestBody JTRegionDTO jtRegionDTO) {
		Optional<JTRegionModel> jtRegionModel = jtRegionService.findById(jtRegionDTO.getId());
		if (jtRegionModel.isPresent()) {
			JTRegionModel regionModel = jtRegionModel.get();

			if (StringUtils.hasText(jtRegionDTO.getName())) {
				regionModel.setName(jtRegionDTO.getName());
			}

			if (StringUtils.hasText(jtRegionDTO.getIsoCode())) {
				regionModel.setIsoCode(jtRegionDTO.getIsoCode());
			}

			if (null != jtRegionDTO.getCountryId()) {
				Optional<JTCountryModel> countryModel = countryService.findById(jtRegionDTO.getCountryId());
				if (countryModel.isPresent()) {
					JTCountryModel jtCountryModel = countryModel.get();
					regionModel.setCountry(jtCountryModel);
				}
			}
			regionModel = jtRegionService.saveRegion(regionModel);

			return ResponseEntity.ok().body(new MessageResponse("Region Updated"));

		}
		return ResponseEntity.badRequest().body(new MessageResponse("Region Not Foound With Given Id"));
	}

	@DeleteMapping("/delete")
	public ResponseEntity deleteRegion(@RequestParam Long regionId) {
		Optional<JTRegionModel> jtRegionModel = jtRegionService.findById(regionId);
		if (jtRegionModel.isPresent()) {
			JTRegionModel regionModel = jtRegionModel.get();
			jtRegionService.deleteRegion(regionModel);
			return ResponseEntity.ok().body(new MessageResponse("Deleted Region"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Region Not Found With Given regionId"));
	}

	@SuppressWarnings("unchecked")
	public AbstractConverter getConverterInstance() {
		return getConverter(jtRegionPopulator, JTRegionDTO.class.getName());
	}
}
