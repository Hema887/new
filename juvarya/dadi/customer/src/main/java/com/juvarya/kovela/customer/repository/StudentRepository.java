package com.juvarya.kovela.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juvarya.kovela.customer.model.StudentModel;
@Repository("jtStudentRepository")
public interface StudentRepository extends JpaRepository<StudentModel, Long> {

}
