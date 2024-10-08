package com.juvarya.kovela.customer.controllers;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juvarya.kovela.customer.dto.JTZodiacSignDTO;
import com.juvarya.kovela.customer.dto.MessageDTO;
import com.juvarya.kovela.customer.model.JTZodiacSignModel;
import com.juvarya.kovela.customer.populator.JTZodiacSignPopulator;
import com.juvarya.kovela.customer.service.JTZodiacSignService;
import com.juvarya.kovela.utils.converter.AbstractConverter;
import com.juvarya.kovela.utils.converter.JTBaseEndpoint;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/jtZodiacSign")
public class JTZodiacSignEndpoint extends JTBaseEndpoint{
	
	@Resource(name = "jtZodiacSignService")
	private JTZodiacSignService zodiacSignService;
	
	@Resource(name = "jtZodiacSignPopulator")
	private JTZodiacSignPopulator populator;
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody JTZodiacSignDTO dto) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTZodiacSignModel model = new JTZodiacSignModel();
		if(Objects.nonNull(dto)) {
			model.setZodiacSign(dto.getZodiacSign());
			model = zodiacSignService.save(model);
			JTZodiacSignDTO signDTO = (JTZodiacSignDTO) getConverterInstance().convert(model);
			return ResponseEntity.ok().body(signDTO);
		}
		return ResponseEntity.ok().body(new MessageDTO("details shouldn't be empty"));
	}
	
	@SuppressWarnings("unchecked")
	private AbstractConverter getConverterInstance() {
		return getConverter(populator, JTZodiacSignDTO.class.getName());
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/list")
	public ResponseEntity signsList() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		List<JTZodiacSignModel> list = zodiacSignService.findAll();
		if(!CollectionUtils.isEmpty(list)) {
			List<JTZodiacSignDTO> zodiacSignDTO = getConverterInstance().convertAll(list);
			return ResponseEntity.ok().body(zodiacSignDTO);
		}
		return ResponseEntity.ok().body(new MessageDTO("nothing found"));
	}
}
