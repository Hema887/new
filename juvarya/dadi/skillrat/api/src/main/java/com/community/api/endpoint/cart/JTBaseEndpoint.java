package com.community.api.endpoint.cart;

import org.broadleafcommerce.profile.web.core.CustomerState;

import com.broadleafcommerce.rest.api.endpoint.BaseEndpoint;
import com.community.api.endpoint.dto.JTResponseDTO;
import com.community.rest.api.wrapper.JTConstants;

public abstract class JTBaseEndpoint extends BaseEndpoint{
	public boolean isAnonymousCustomer() {
		if(null == CustomerState.getCustomer()
				|| CustomerState.getCustomer().isAnonymous()) {
			return true;
		}
		return false;
	}
	protected JTResponseDTO updateUnathorizedMessage(JTResponseDTO response, String message) {
		response.setMessage(message);
		response.setStatusCode(JTConstants.UNAUTHORIZED);
		return response;
	}
}
