package com.juvarya.kovela.customer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juvarya.kovela.customer.model.JTZodiacSignModel;
import com.juvarya.kovela.customer.repository.JTZodiacSignRepository;
import com.juvarya.kovela.customer.service.JTZodiacSignService;

@Service("jtZodiacSignService")
public class JTZodiacSignServiceImpl implements JTZodiacSignService{
	
	@Resource(name = "jtZodiacSignRepository")
	private JTZodiacSignRepository repository;

	@Override
	public JTZodiacSignModel save(JTZodiacSignModel model) {
		return repository.save(model);
	}

	@Override
	public List<JTZodiacSignModel> findAll() {
		return repository.findAll();
	}

}
