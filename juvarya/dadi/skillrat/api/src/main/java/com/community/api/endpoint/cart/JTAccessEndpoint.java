package com.community.api.endpoint.cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.community.api.endpoint.dto.JTResponseDTO;

@RestController
@RequestMapping("/access")
public class JTAccessEndpoint {
	
	@GetMapping("/response")
	public JTResponseDTO returnAccessResponse(HttpServletRequest request,
			HttpServletResponse servletResponse) {
		JTResponseDTO response= new JTResponseDTO();
		response.setMessage(request.getParameter("message"));
		response.setStatusCode(request.getParameter("statusCode"));
		return response;
	}
}
