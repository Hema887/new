 package com.community.api.endpoint.cart;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.broadleafcommerce.profile.core.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.community.api.endpoint.data.JTAddressListData;
import com.community.api.endpoint.dto.JTAddressDTO;
import com.community.api.endpoint.dto.JTAddressListDto;
import com.community.api.endpoint.dto.JTResponseDTO;
import com.community.core.catalog.service.JTAddressService;
import com.community.rest.api.wrapper.JTConstants;

@RestController
@RequestMapping(value = "/address", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class JTAddressEndpoint extends JTBaseEndpoint {

	@Autowired
	private ModelMapper modelMapper;

	@Resource(name = "blAddressService")
	private AddressService addressService;
	
	@Resource(name = "jtAddressService")
	private JTAddressService jtAddressService;
	
	
	@PostMapping("/save")
	public JTAddressDTO saveCategories(@RequestBody JTAddressDTO jtAddressDto) {
		Address address = new AddressImpl();
		address.setId(jtAddressDto.getId());
		address.setAddressLine1(jtAddressDto.getAddressLine1());
		address.setAddressLine2(jtAddressDto.getAddressLine2());
		address.setAddressLine3(jtAddressDto.getAddressLine3());
		address.setCity(jtAddressDto.getCity());
		address.setPostalCode(jtAddressDto.getPostalCode());
		Address jtaddress = addressService.saveAddress(address);
		return modelMapper.map(jtaddress, JTAddressDTO.class);
	}

	@PatchMapping("/update")
	public JTAddressDTO update(@RequestParam Long id, @RequestParam String addressLine1,
			@RequestParam String addressLine2, @RequestParam String addressLine3) {
		JTResponseDTO response = new JTResponseDTO();
		if (id != null) {
			Address model = addressService.readAddressById(id);
			model.setAddressLine1(addressLine1);
			model.setAddressLine2(addressLine2);
			model.setAddressLine3(addressLine3);
			model = addressService.saveAddress(model);
			return modelMapper.map(model, JTAddressDTO.class);
		} else {
			response.setMessage("id is required");
			response.setStatusCode(JTConstants.UNAUTHORIZED);
			return (JTAddressDTO) updateUnathorizedMessage(response, "id is required");
		}
	}
	
	@GetMapping("/list")
	public JTAddressListDto getAddresses(@RequestParam int pageNo, @RequestParam int pageSize) {
		List<Address> addresses = jtAddressService.getAddresses(pageNo, pageSize);
		JTAddressListData listData = new JTAddressListData();
		listData.setAddresses(addresses);
		List<JTAddressDTO> addressDto= new ArrayList<>();
		for(Address jtAddress: addresses) {
			JTAddressDTO addressDTO = new JTAddressDTO();
			addressDTO.setId(jtAddress.getId());
			addressDTO.setAddressLine1(jtAddress.getAddressLine1());
			addressDTO.setAddressLine2(jtAddress.getAddressLine2());
			addressDTO.setAddressLine3(jtAddress.getAddressLine3());
			addressDTO.setCity(jtAddress.getCity());
			addressDTO.setPostalCode(jtAddress.getPostalCode());
			addressDto.add(addressDTO);
			
		}
		JTAddressListDto response = modelMapper.map(addressDto, JTAddressListDto.class);
		response.setAddresses(addressDto);
		response.setPage(pageNo);
		response.setPageSize(pageSize);
		return modelMapper.map(response, JTAddressListDto.class);
	}


}
