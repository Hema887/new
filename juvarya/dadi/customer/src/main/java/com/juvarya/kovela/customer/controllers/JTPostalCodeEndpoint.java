package com.juvarya.kovela.customer.controllers;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juvarya.kovela.customer.dto.JTPostalCodeDTO;
import com.juvarya.kovela.customer.model.JTCityModel;
import com.juvarya.kovela.customer.model.JTPostalCodeModel;
import com.juvarya.kovela.customer.populator.JTPostalCodePopulator;
import com.juvarya.kovela.customer.response.MessageResponse;
import com.juvarya.kovela.customer.service.JTCityService;
import com.juvarya.kovela.customer.service.JTPostalCodeService;
import com.juvarya.kovela.utils.converter.AbstractConverter;
import com.juvarya.kovela.utils.converter.JTBaseEndpoint;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/jtpostalcode")
public class JTPostalCodeEndpoint extends JTBaseEndpoint {

	@Resource(name = "jtPostalCodeService")
	private JTPostalCodeService jtPostalCodeService;

	@Resource(name = "jtPostalCodePopulator")
	private JTPostalCodePopulator codePopulator;

	@Resource(name = "jtCityService")
	private JTCityService cityService;

	@SuppressWarnings("unchecked")
	@PostMapping("/save")
	public ResponseEntity savePostalCode(@RequestBody JTPostalCodeDTO codeDTO)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTPostalCodeModel jtPostalCodeModel = new JTPostalCodeModel();
		jtPostalCodeModel.setId(codeDTO.getId());
		jtPostalCodeModel.setCode(codeDTO.getCode());

		JTCityModel cityModel = cityService.findById(codeDTO.getCityId());
		if (null != cityModel) {
			jtPostalCodeModel.setCity(cityModel);
		}

		jtPostalCodeModel = jtPostalCodeService.save(jtPostalCodeModel);
		JTPostalCodeDTO jtPostalCodeDTO = (JTPostalCodeDTO) getConverterInstance().convert(jtPostalCodeModel);
		return ResponseEntity.ok().body(jtPostalCodeDTO);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/postalCode")
	public ResponseEntity getPostalCode(@RequestParam Long code)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTPostalCodeModel codeModel = jtPostalCodeService.getPostalCode(code);
		if (null != codeModel) {
			JTPostalCodeDTO codeDTO = (JTPostalCodeDTO) getConverterInstance().convert(codeModel);
			return ResponseEntity.ok().body(codeDTO);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("PostalCode Not Found With Given Code"));
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/{id}")
	public ResponseEntity findById(@PathVariable("id") Long id)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTPostalCodeModel codeModel = jtPostalCodeService.findById(id);
		if (null != codeModel) {
			JTPostalCodeDTO jtPostalCodeDTO = (JTPostalCodeDTO) getConverterInstance().convert(codeModel);
			return ResponseEntity.ok().body(jtPostalCodeDTO);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("PostalCode Not Found With Given Code"));
	}

	@DeleteMapping("/delete")
	public ResponseEntity deletePostalCode(@RequestParam Long postalCodeId) {
		JTPostalCodeModel codeModel = jtPostalCodeService.findById(postalCodeId);
		if (null != codeModel) {
			jtPostalCodeService.deletePostalCode(codeModel);
			return ResponseEntity.ok().body(new MessageResponse("PostalCode Deleted"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("PostalCode Not Found With Given Id"));
	}

	@PutMapping("/update")
	public ResponseEntity updatePostalCode(@RequestBody JTPostalCodeDTO codeDTO) {
		JTPostalCodeModel codeModel = jtPostalCodeService.findById(codeDTO.getId());
		if (null != codeModel) {
			if (null != codeDTO.getCode()) {
				codeModel.setCode(codeDTO.getCode());
			}

			if (null != codeDTO.getCityId()) {
				JTCityModel cityModel = cityService.findById(codeDTO.getCityId());
				if (null != cityModel) {
					codeModel.setCity(cityModel);
				}
			}
			jtPostalCodeService.save(codeModel);
			return ResponseEntity.ok().body(new MessageResponse("PostalCode Updated"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("PostalCode Not Found With Given Id"));
	}

	@SuppressWarnings("unchecked")
	public AbstractConverter getConverterInstance() {
		return getConverter(codePopulator, JTPostalCodeDTO.class.getName());
	}
}
