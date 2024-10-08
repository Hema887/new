package com.juvarya.kovela.customer.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juvarya.kovela.commonservices.dto.LoggedInUser;
import com.juvarya.kovela.customer.dto.JTUserGroupDTO;
import com.juvarya.kovela.customer.model.JTUserGroupModel;
import com.juvarya.kovela.customer.populator.JTUserGroupPopulator;
import com.juvarya.kovela.customer.service.JTUserGroupService;
import com.juvarya.kovela.security.services.CustomerState;
import com.juvarya.kovela.utils.converter.AbstractConverter;
import com.juvarya.kovela.utils.converter.JTBaseEndpoint;

@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/jtUserGroup")
public class JTUserGroupEndPoint extends JTBaseEndpoint{
	
	@Resource(name = "jtUserGroupService")
	private JTUserGroupService jtUserGroupService;
	
	@Resource(name = "customerState")
	private CustomerState customerState;
	
	@Resource(name="jtUserGroupPopulator")
	private JTUserGroupPopulator jtUserGroupPopulator;
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping("/save")
	public JTUserGroupDTO saveUserGroup(HttpServletRequest request,@RequestBody JTUserGroupDTO userGroupDTO) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		LoggedInUser user = customerState.getLoggedInUser(request);
		
		JTUserGroupModel groupModel = new JTUserGroupModel();
		if(null != user && (null != user && !CollectionUtils.isEmpty(user.getRoles()))
				&& (user.getRoles().contains("ROLE_ADMIN"))
				&& null != userGroupDTO.getProfileId()) {
			groupModel.setProfileId(userGroupDTO.getProfileId());
			groupModel.setCode(userGroupDTO.getCode());
			groupModel = jtUserGroupService.saveUserGroup(groupModel);
			return (JTUserGroupDTO) getConverterInstance().convert(groupModel);
		}
		
		return null;
	}
	
	@SuppressWarnings({ "unchecked" })
	private AbstractConverter getConverterInstance() {
		return getConverter(jtUserGroupPopulator, JTUserGroupDTO.class.getName());
	}
}
