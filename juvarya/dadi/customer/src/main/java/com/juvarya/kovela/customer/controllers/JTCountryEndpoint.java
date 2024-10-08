package com.juvarya.kovela.customer.controllers;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juvarya.kovela.customer.dto.JTCountryDTO;
import com.juvarya.kovela.customer.model.JTCountryModel;
import com.juvarya.kovela.customer.populator.JTCountryPopulator;
import com.juvarya.kovela.customer.response.MessageResponse;
import com.juvarya.kovela.customer.service.JTCountryService;
import com.juvarya.kovela.utils.converter.AbstractConverter;
import com.juvarya.kovela.utils.converter.JTBaseEndpoint;

@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/jtcountry")
public class JTCountryEndpoint extends JTBaseEndpoint {

	@Resource(name = "jtCountryService")
	private JTCountryService countryService;

	@Resource(name = "jtCountryPopulator")
	private JTCountryPopulator countryPopulator;

	@SuppressWarnings("unchecked")
	@PostMapping("/save")
	public ResponseEntity saveCountry(@RequestBody JTCountryDTO countryDTO)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTCountryModel countryModel = new JTCountryModel();
		countryModel.setId(countryDTO.getId());
		countryModel.setIsoCode(countryDTO.getIsoCode());
		countryModel.setName(countryDTO.getName());
		countryModel = countryService.saveCountry(countryModel);

		JTCountryDTO jtCountryDTO = (JTCountryDTO) getConverterInstance().convert(countryModel);
		return ResponseEntity.ok().body(jtCountryDTO);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable("id") Long id)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Optional<JTCountryModel> jtCountryModel = countryService.findById(id);
		if (jtCountryModel.isPresent()) {
			JTCountryModel countryModel = jtCountryModel.get();
			JTCountryDTO jtCountryDTO = (JTCountryDTO) getConverterInstance().convert(countryModel);
			return ResponseEntity.ok().body(jtCountryDTO);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Country Not Found With Given Id"));
	}

	@PutMapping("/update")
	public ResponseEntity updateCountry(@RequestBody JTCountryDTO countryDTO) {
		Optional<JTCountryModel> jtCountryModel = countryService.findById(countryDTO.getId());

		if (jtCountryModel.isPresent()) {
			JTCountryModel countryModel = jtCountryModel.get();
			if (StringUtils.hasText(countryDTO.getIsoCode())) {
				countryModel.setIsoCode(countryDTO.getIsoCode());
			}
			if (StringUtils.hasText(countryDTO.getName())) {
				countryModel.setName(countryDTO.getName());
			}

			countryModel = countryService.saveCountry(countryModel);
			return ResponseEntity.badRequest().body(new MessageResponse("Country Updated"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Country Not Found With Given Id"));
	}

	@GetMapping("/isocode")
	public ResponseEntity getByIsoCode(@RequestParam String isoCode) {
		JTCountryModel countryModel = countryService.findByIsoCodeContainsIgnoreCase(isoCode);
		if (null != countryModel) {
			return ResponseEntity.ok().body(countryModel);
		}
		return ResponseEntity.ok().body(new MessageResponse("Country Not Found With Givent IsoCode"));
	}

	@DeleteMapping("/delete")
	public ResponseEntity deleteCountry(@RequestParam Long countryId) {
		Optional<JTCountryModel> jtCountryModel = countryService.findById(countryId);
		if (jtCountryModel.isPresent()) {
			JTCountryModel countryModel = jtCountryModel.get();
			countryService.remove(countryModel);
			return ResponseEntity.ok().body(new MessageResponse("Country Deleted"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Country Not Found With Given CountryId"));
	}

	@SuppressWarnings("unchecked")
	public AbstractConverter getConverterInstance() {
		return getConverter(countryPopulator, JTCountryDTO.class.getName());
	}
}
