package com.juvarya.kovela.customer.service;

import java.util.List;

import com.juvarya.kovela.customer.model.JTDesignationModel;

public interface JTDesignationService {
	JTDesignationModel saveDesignation(JTDesignationModel designation);
	void removeDesignation(Long designationId);
	List<JTDesignationModel> getDesignationList();
}
