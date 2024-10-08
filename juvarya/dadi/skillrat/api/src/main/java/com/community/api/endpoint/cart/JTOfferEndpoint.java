package com.community.api.endpoint.cart;

import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferCodeImpl;
import org.broadleafcommerce.core.offer.domain.OfferImpl;
import org.broadleafcommerce.core.offer.service.OfferService;
import org.broadleafcommerce.core.offer.service.type.OfferDiscountType;
import org.broadleafcommerce.core.offer.service.type.OfferType;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.community.api.endpoint.dto.JTOfferCodeDTO;
import com.community.api.endpoint.dto.JTOfferDTO;
import com.community.api.jwt.payload.response.MessageResponse;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTStore;
import com.community.core.catalog.service.JTOfferCodeService;
import com.community.core.catalog.service.JTStoreService;

@RestController
@RequestMapping("/offer")
public class JTOfferEndpoint {

	@Autowired
	private OfferService offerService;

	@Resource(name = "jtOfferCodeService")
	private JTOfferCodeService jtOfferCodeService;

	@Resource(name = "jtStoreService")
	private JTStoreService jtStoreService;

	@Resource(name = "blCustomerState")
	private CustomerState customerState;

	@SuppressWarnings({ "rawtypes", "static-access" })
	@PostMapping("/saveOffer")
	public ResponseEntity saveOffer(@RequestBody JTOfferDTO offerDTO, HttpServletRequest request) {
		OfferImpl offer = new OfferImpl();

		JTCustomer jtCustomer = (JTCustomer) customerState.getCustomer(request);
		JTStore jtStore = jtStoreService.customerStore(jtCustomer.getId());

		if (Objects.nonNull(jtStore)) {
			offer.setDescription(offerDTO.getDescription());
			offer.setMarketingMessage(offerDTO.getMarketingMessage());
			offer.setArchived(offerDTO.getArchived());
			offer.setName(offerDTO.getName());

			final OfferType offerType = OfferType.getInstance(offerDTO.getOfferType());
			if (null == offerType) {
				return ResponseEntity.ok().body(new MessageResponse("Invalid OfferType"));
			}
			offer.setType(offerType);

			final OfferDiscountType discountType = OfferDiscountType.getInstance(offerDTO.getDiscountType());
			if (null == discountType) {
				return ResponseEntity.ok().body(new MessageResponse("Invalid OfferDiscountType"));
			}
			offer.setDiscountType(discountType);
			offer.setValue(offerDTO.getValue());
			offer.setEndDate(offerDTO.getEndDate());
			offer.setStartDate(offerDTO.getStartDate());
			offer.setAutomaticallyAdded(offerDTO.getAutomatic());
			offerService.save(offer);
			return ResponseEntity.ok().body(new MessageResponse("Offer Created"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Stored not found"));
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	@PostMapping("/saveOfferCode")
	public ResponseEntity saveOfferCode(@RequestBody JTOfferCodeDTO offerCode, HttpServletRequest request) {
		OfferCodeImpl offerCodeImpl = new OfferCodeImpl();

		JTCustomer jtCustomer = (JTCustomer) customerState.getCustomer(request);
		JTStore jtStore = jtStoreService.customerStore(jtCustomer.getId());

		if (Objects.nonNull(jtStore)) {
			offerCodeImpl.setOfferCode(offerCode.getOfferCode());
			offerCodeImpl.setStartDate(offerCode.getOfferCodeStartDate());
			offerCodeImpl.setEndDate(offerCode.getOfferCodeEndDate());

			Offer offer = offerService.findOfferById(offerCode.getOfferId());
			offerCodeImpl.setOffer(offer);

			jtOfferCodeService.saveOfferCode(offerCodeImpl);
			return ResponseEntity.ok().body(new MessageResponse("Offer Code Created"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Stored not found"));
	}
}
