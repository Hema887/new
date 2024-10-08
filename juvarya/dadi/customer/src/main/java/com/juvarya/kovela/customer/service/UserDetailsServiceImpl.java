package com.juvarya.kovela.customer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juvarya.kovela.customer.model.User;
import com.juvarya.kovela.customer.repository.UserRepository;

@Service
public class UserDetailsServiceImpl {
	@Autowired
	UserRepository userRepository;

	@Transactional
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("LoggedInUser Not Found with username: " + username));
		return user;
	}

	@Transactional
	public User loadUserByPrimaryContact(String primaryContact) {
		try {
			Optional<User> optionalUser = userRepository.findByPrimaryContact(primaryContact);
			if (optionalUser.isPresent()) {
				return optionalUser.get();
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional
	public User loadUserByEmail(String email) {
		try {
			Optional<User> optionalUser = userRepository.findByEmail(email);
			if (optionalUser.isPresent()) {
				return optionalUser.get();
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	

}
