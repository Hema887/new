package com.juvarya.kovela.customer.service;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;

import com.juvarya.kovela.customer.model.StudentModel;

public interface JTStudentService {
	
	JTStudentService save(StudentModel model);

	java.util.Optional<StudentModel> findById(Long id);

	Page<StudentModel> getAll(Pageable pageable);

	void deleteStore(Long id);

	Page<StudentModel> getAll(org.springframework.data.domain.Pageable pageable);
}


