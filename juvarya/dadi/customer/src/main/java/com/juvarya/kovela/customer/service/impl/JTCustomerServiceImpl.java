package com.juvarya.kovela.customer.service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.juvarya.kovela.customer.model.Role;
import com.juvarya.kovela.customer.model.User;
import com.juvarya.kovela.customer.repository.RoleRepository;
import com.juvarya.kovela.customer.repository.UserRepository;
import com.juvarya.kovela.customer.service.JTCustomerService;
import com.juvarya.kovela.model.ERole;

@Service("jtCustomerService")
public class JTCustomerServiceImpl implements JTCustomerService {

	@Resource(name = "userRepository")
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User findById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		}
		return null;
	}

	@Override
	public User findByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		}
		return null;
	}

	@Override
	public Page<User> findByRolesContainsIgnoreCase(String role, Pageable pageable) {
		Optional<Role> optionalRole = roleRepository.findByName(ERole.valueOf(role));
		return userRepository.findByRolesContainsIgnoreCase(optionalRole.get(), pageable);
	}

	@Override
	public Optional<User> findByPrimaryContact(String primaryContact) {
		try {
			return userRepository.findByPrimaryContact(primaryContact);
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Page<User> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Page<User> findByPostalCodes(List<Long> code, String type, Pageable pageable) {
		return userRepository.findByPostalCodesAndType(code, type, pageable);
	}

	@Override
	public Optional<User> findByCustomerAndType(Long customerId, String type) {
		return userRepository.findByIdAndType(customerId, type);
	}

}
