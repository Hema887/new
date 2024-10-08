package com.juvarya.kovela.customer.service;

import java.util.List;

import com.juvarya.kovela.customer.model.JTZodiacSignModel;

public interface JTZodiacSignService {
	
	JTZodiacSignModel save(JTZodiacSignModel model);
	List<JTZodiacSignModel> findAll();
}
