/**
 * 
 */
package com.juvarya.kovela.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.juvarya.kovela.customer.model.User;

/**
 * @author juvi
 *
 */
public interface JTCustomerService {
	User findById(Long id);

	User findByEmail(String email);

	Page<User> findByRolesContainsIgnoreCase(String role, Pageable pageable);

	Optional<User> findByPrimaryContact(String primaryContact);

	Page<User> getAllUsers(Pageable pageable);

	Page<User> findByPostalCodes(@Param("code") List<Long> code, String type, Pageable pageable);

	Optional<User> findByCustomerAndType(Long customerId, String type);
}
