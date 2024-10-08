package com.juvarya.kovela.customer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juvarya.kovela.customer.model.Role;
import com.juvarya.kovela.customer.model.User;

import java.util.List;
import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

	Page<User> findByRolesContainsIgnoreCase(Role role, Pageable pageable);

	Boolean existsByPrimaryContact(String primaryContact);

	Optional<User> findByPrimaryContact(String primaryContact);

	@Query("SELECT users FROM User users WHERE users.postalCode.code IN :code AND users.type =:type")
	Page<User> findByPostalCodesAndType(@Param("code") List<Long> code, @Param("type") String type, Pageable pageable);

	Optional<User> findByIdAndType(Long userId, String type);

}
