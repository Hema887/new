//package com.juvarya.kovela.customer.practise;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.juvarya.kovela.customer.dto.JTCountryDTO;
//import com.juvarya.kovela.customer.model.JTCountryModel;
//
//@PostMapping("/save")
//public ResponseEntity saveCountry(@RequestBody JTCountryDTO countryDTO)
//		throws ClassNotFoundException, InstantiationException, IllegalAccessException {
//	JTCountryModel countryModel = new JTCountryModel();
//	countryModel.setId(countryDTO.getId());
//	countryModel.setIsoCode(countryDTO.getIsoCode());
//	countryModel.setName(countryDTO.getName());
//	countryModel = countryService.saveCountry(countryModel);
//
//	JTCountryDTO jtCountryDTO = (JTCountryDTO) getConverterInstance().convert(countryModel);
//	return ResponseEntity.ok().body(jtCountryDTO);
//}