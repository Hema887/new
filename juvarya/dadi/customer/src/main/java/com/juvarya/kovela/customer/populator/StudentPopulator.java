package com.juvarya.kovela.customer.populator;

import org.springframework.stereotype.Component;

import com.juvarya.kovela.customer.dto.StudentDTO;
import com.juvarya.kovela.customer.model.StudentModel;
import com.juvarya.kovela.utils.converter.Populator;

@Component
public class StudentPopulator implements Populator<StudentModel, StudentDTO> {

	@Override
	public void populate(StudentModel arg0, StudentDTO arg1) {
		arg1.setAge(arg0.getAge());
		arg1.setId(arg0.getId());
		arg1.setMarks(arg0.getMarks());
		arg1.setName(arg0.getName());
	}

}
