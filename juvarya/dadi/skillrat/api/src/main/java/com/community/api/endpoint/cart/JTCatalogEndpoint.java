package com.community.api.endpoint.cart;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.broadleafcommerce.rest.api.endpoint.catalog.CatalogEndpoint;
import com.community.api.endpoint.dto.JTProductDTO;
import com.community.core.catalog.dao.JTProductDao;

@RestController
@RequestMapping(value = "/catalog/", 
produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class JTCatalogEndpoint extends CatalogEndpoint {

	@Resource(name = "jtProductDao")
	private JTProductDao jtProductDao;

	@PostMapping("/create")
	public JTProductDTO createProduct(@RequestBody JTProductDTO jtProductDto,
			HttpServletRequest request) {
		return jtProductDto;
	}
}
