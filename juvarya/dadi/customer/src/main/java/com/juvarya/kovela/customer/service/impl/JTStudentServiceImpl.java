package com.juvarya.kovela.customer.service.impl;

import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.juvarya.kovela.customer.model.StudentModel;
import com.juvarya.kovela.customer.repository.StudentRepository;
import com.juvarya.kovela.customer.service.JTStudentService;

@Service("StudentRepository")
public abstract class JTStudentServiceImpl implements JTStudentService {

	@Resource(name = "StudentRepository")
	private StudentRepository repository;

	@Override
	@Transactional
	public JTStudentService save(StudentModel model) {
		return (JTStudentService) repository.save(model);
	}

	@Override
	public Optional<StudentModel> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<StudentModel> getAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public void deleteStore(Long id) {
		repository.deleteById(id);
	}

}
