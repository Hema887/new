package com.juvarya.kovela.customer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juvarya.kovela.customer.model.JTAddressModel;
import com.juvarya.kovela.model.JTAddressType;

@Repository("jtAddressRepository")
public interface JTAddressRepository extends JpaRepository<JTAddressModel, Long> {
	@Query("SELECT address FROM JTAddressModel address WHERE address.user.id =:id")
	Page<JTAddressModel> findByUser(@Param("id") Long id, Pageable pageable);

	JTAddressModel findByTargetIdAndDefaultAddress(Long targetId, Boolean defaultAddress);

	List<JTAddressModel> findByTargetId(Long targetid);

	@Query("SELECT profiles FROM JTAddressModel profiles WHERE profiles.postalCode.city.district.isoCode=:isoCode AND profiles.addressType=:addressType")
	Page<JTAddressModel> findByPostalCodeAndAddressType(@Param("isoCode") String isoCode,
			@Param("addressType") JTAddressType addressType, Pageable page);
}
