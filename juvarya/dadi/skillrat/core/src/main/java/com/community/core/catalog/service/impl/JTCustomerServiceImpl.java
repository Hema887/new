package com.community.core.catalog.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.event.BroadleafApplicationEventPublisher;
import org.broadleafcommerce.common.id.service.IdGenerationService;
import org.broadleafcommerce.profile.core.dao.CustomerDao;
import org.broadleafcommerce.profile.core.dao.RoleDao;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.listener.PostRegistrationObserver;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.community.core.azure.service.JTCustomerRoleService;
import com.community.core.catalog.dao.JTCustomerDao;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTCustomerRole;
import com.community.core.catalog.domain.impl.JTCustomerRoleImpl;
import com.community.core.catalog.service.JTCustomerService;
import com.community.core.config.JTCoreConstants;
import com.community.core.eventlistener.JTEventPublisher;

@Service("jtCustomerService")
public class JTCustomerServiceImpl implements JTCustomerService {

	@SuppressWarnings("unused")
	private static final Log LOG = LogFactory.getLog(JTCustomerServiceImpl.class);

	@Resource(name = "jtCustomerDao")
	private JTCustomerDao jtCustomerDao;

	@Resource(name = "blCustomerDao")
	private CustomerDao customerDao;

	@SuppressWarnings("unused")
	@Autowired
	private JTEventPublisher jtEventPublisher;

	@Resource(name = "blRoleDao")
	private RoleDao roleDao;

	@Resource(name = "blPasswordEncoder")
	protected PasswordEncoder passwordEncoderBean;

	@Resource(name = "blIdGenerationService")
	protected IdGenerationService idGenerationService;

	@Resource(name = "jtCustomerRoleService")
	private JTCustomerRoleService jtCustomerRoleService;

	@Autowired
	@Qualifier("blApplicationEventPublisher")
	protected BroadleafApplicationEventPublisher eventPublisher;

	protected final List<PostRegistrationObserver> postRegisterListeners = new ArrayList<PostRegistrationObserver>();

	@Override
	@Transactional
	public JTCustomer save(JTCustomer customer) {

		if (customer.getId() == null) {
			customer.setId(findNextCustomerId());
		}

		if (customer.getUnencodedPassword() != null) {
			customer.setPassword(encodePassword(customer.getUnencodedPassword()));
		}

		customer.setDeactivated(false);
		jtCustomerDao.save(customer);
		CustomerState.setCustomer(customer);
		JTCustomer updatedCustomer = (JTCustomer) customerDao.readCustomerByEmail(customer.getEmailAddress());
		createRegisteredCustomerRoles(updatedCustomer);
		return updatedCustomer;
	}

	public void createRegisteredCustomerRoles(Customer customer) {
		JTCustomerRole customerRole = new JTCustomerRoleImpl();
		if (null != customer.getUsername() && customer.getUsername().endsWith(JTCoreConstants.JUVARYACOM)) {
			customerRole.setRoleName(JTCoreConstants.ROLE_ADMIN);
			customerRole.setJtCustomer((JTCustomer) customer);
		} else {
			customerRole.setRoleName(JTCoreConstants.ROLE_USER);
			customerRole.setJtCustomer((JTCustomer) customer);
		}
		jtCustomerRoleService.save(customerRole);
	}

	public JTCustomer findCustomer(Long id) {
		return jtCustomerDao.findCustomer(id);
	}

	protected void notifyPostRegisterListeners(Customer customer) {
		for (Iterator<PostRegistrationObserver> iter = postRegisterListeners.iterator(); iter.hasNext();) {
			PostRegistrationObserver listener = iter.next();
			listener.processRegistrationEvent(customer);
		}
	}

	public Long findNextCustomerId() {
		return idGenerationService.findNextId(Customer.class.getCanonicalName());
	}

	public Customer readCustomerByUsername(String username) {
		return customerDao.readCustomerByUsername(username);
	}

	public String encodePassword(String rawPassword) {
		return passwordEncoderBean.encode(rawPassword);
	}

	@Override
	public Customer validateCustomer(JTCustomer jtCustomer) {
		Customer customer = readCustomerByUsername(jtCustomer.getEmailAddress());
		if (customer instanceof JTCustomer) {
			return (JTCustomer) customer;
		}
		return null;
	}

	@Override
	public JTCustomer findById(Long id) {
		return jtCustomerDao.findById(id);
	}

	@Override
	public JTCustomer findByPrimaryContact(String contact) {
		return jtCustomerDao.findByPrimaryContact(contact);
	}

	@Override
	public JTCustomer findByEmail(String email) {
		return jtCustomerDao.findByEmail(email);
	}

}