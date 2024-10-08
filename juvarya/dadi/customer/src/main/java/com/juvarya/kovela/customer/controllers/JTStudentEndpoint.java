package com.juvarya.kovela.customer.controllers;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.juvarya.kovela.customer.populator.StudentPopulator;
import com.juvarya.kovela.customer.service.JTStudentService;

@SuppressWarnings("unused")
public class JTStudentEndpoint {		
	
		@Autowired
		private StudentPopulator studentPopulator;
		
		@Autowired
		private JTStudentService studentService;
}