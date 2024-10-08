package com.juvarya.kovela.customer.controllers;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
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

import com.juvarya.kovela.customer.dto.JTCityDTO;
import com.juvarya.kovela.customer.model.JTCityModel;
import com.juvarya.kovela.customer.model.JTDistrictModel;
import com.juvarya.kovela.customer.populator.JTCityPopulator;
import com.juvarya.kovela.customer.response.MessageResponse;
import com.juvarya.kovela.customer.service.JTCityService;
import com.juvarya.kovela.customer.service.JTDistrictService;
import com.juvarya.kovela.utils.converter.AbstractConverter;
import com.juvarya.kovela.utils.converter.JTBaseEndpoint;

@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/jtCity")
public class JTCityEndpoint extends JTBaseEndpoint {

	@Resource(name = "jtCityService")
	private JTCityService cityService;

	@Resource(name = "jtCityPopulator")
	private JTCityPopulator cityPopulator;

	@Resource(name = "jtDistrictService")
	private JTDistrictService districtService;

	@SuppressWarnings("unchecked")
	@PostMapping("/save")
	public ResponseEntity saveCity(@RequestBody JTCityDTO cityDTO)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTCityModel city = new JTCityModel();
		city.setName(cityDTO.getName());
		city.setIsoCode(cityDTO.getIsoCode());
		city.setHasAirTransport(cityDTO.getHasAirTransport());
		city.setHasBusTransport(cityDTO.getHasBusTransport());
		city.setHasRailTransport(cityDTO.getHasRailTransport());

		if (Boolean.FALSE.equals(cityDTO.getHasAirTransport()) && null != cityDTO.getNearestAirTransport()) {
			JTCityModel cityModel = cityService.findById(cityDTO.getNearestAirTransport().getId());
			if (null != cityModel) {
				city.setNearestAirTransportCity(cityModel);
			}
		} else if (Boolean.FALSE.equals(cityDTO.getHasAirTransport())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Nearest Air Transport Is Required"));
		}

		if (Boolean.FALSE.equals(cityDTO.getHasBusTransport()) && null != cityDTO.getNearestBusTransport()) {
			JTCityModel cityModel = cityService.findById(cityDTO.getNearestBusTransport().getId());
			if (null != cityModel) {
				city.setNearestBusTransportCity(cityModel);
			}
		} else if (Boolean.FALSE.equals(cityDTO.getHasBusTransport())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Nearest Bus Transport Is Required"));
		}

		if (Boolean.FALSE.equals(cityDTO.getHasRailTransport()) && null != cityDTO.getNearestRailTransport()) {
			JTCityModel cityModel = cityService.findById(cityDTO.getNearestRailTransport().getId());
			if (null != cityModel) {
				city.setNearestRailTransportCity(cityModel);
			}
		} else if (Boolean.FALSE.equals(cityDTO.getHasRailTransport())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Nearest Rail Transport Is Required"));
		}

		JTDistrictModel districtModel = districtService.findById(cityDTO.getDistrictId());
		if (null != districtModel) {
			city.setDistrict(districtModel);
		}
		city = cityService.saveCity(city);
		JTCityDTO jtCityDTO = (JTCityDTO) getConverterInstance().convert(city);
		return ResponseEntity.ok().body(jtCityDTO);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable("id") Long id)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTCityModel cityModel = cityService.findById(id);
		if (null != cityModel) {
			JTCityDTO cityDTO = (JTCityDTO) getConverterInstance().convert(cityModel);
			return ResponseEntity.ok().body(cityDTO);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("City Not Found With Given Id"));
	}

	@DeleteMapping("/delete")
	public ResponseEntity deleteCity(@RequestParam Long cityId) {
		JTCityModel cityModel = cityService.findById(cityId);
		if (null != cityModel) {
			cityService.deleteCity(cityModel);
			return ResponseEntity.ok().body(new MessageResponse("City Deleted"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("City Not Found With Given Id"));
	}

	@PutMapping("/update")
	public ResponseEntity updateCity(@RequestBody JTCityDTO cityDTO) {
		JTCityModel cityModel = cityService.findById(cityDTO.getId());
		if (null != cityModel) {
			if (null != cityDTO.getName()) {
				cityModel.setName(cityDTO.getName());
			}

			if (null != cityDTO.getIsoCode()) {
				cityModel.setIsoCode(cityDTO.getIsoCode());
			}

			if (Boolean.TRUE.equals(cityDTO.getHasAirTransport())) {
				if (null != cityModel.getNearestAirTransportCity()) {
					cityModel.setNearestAirTransportCity(null);
				}
				cityModel.setHasAirTransport(cityDTO.getHasAirTransport());
			}

			if (Boolean.TRUE.equals(cityDTO.getHasBusTransport())) {
				if (null != cityModel.getNearestBusTransportCity()) {
					cityModel.setNearestBusTransportCity(null);
				}
				cityModel.setHasBusTransport(cityDTO.getHasBusTransport());
			}

			if (Boolean.TRUE.equals(cityDTO.getHasRailTransport())) {
				if (null != cityModel.getNearestRailTransportCity()) {
					cityModel.setNearestRailTransportCity(null);
				}
				cityModel.setHasRailTransport(cityDTO.getHasRailTransport());
			}

			if (Boolean.FALSE.equals(cityDTO.getHasAirTransport()) && null != cityDTO.getNearestAirTransport()) {
				JTCityModel city = cityService.findById(cityDTO.getNearestAirTransport().getId());
				if (null != cityModel) {
					cityModel.setNearestAirTransportCity(city);
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Nearest Air Transport Is Required"));
			}

			if (Boolean.FALSE.equals(cityDTO.getHasBusTransport() && null != cityDTO.getNearestBusTransport())) {
				JTCityModel city = cityService.findById(cityDTO.getNearestBusTransport().getId());
				if (null != cityModel) {
					cityModel.setNearestBusTransportCity(city);
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Nearest Bus Transport Is Required"));
			}

			if (Boolean.FALSE.equals(cityDTO.getHasRailTransport() && null != cityDTO.getNearestRailTransport())) {
				JTCityModel city = cityService.findById(cityDTO.getNearestRailTransport().getId());
				if (null != cityModel) {
					cityModel.setNearestRailTransportCity(city);
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Nearest Rail Transport Is Required"));
			}

			cityService.saveCity(cityModel);
			return ResponseEntity.ok().body(new MessageResponse("Address Updated"));

		}
		return ResponseEntity.badRequest().body(new MessageResponse("Address Not Found With Given Id"));
	}

	@SuppressWarnings("unchecked")
	public AbstractConverter getConverterInstance() {
		return getConverter(cityPopulator, JTCityDTO.class.getName());
	}
}
