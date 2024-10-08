package com.community.api.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.profile.web.core.CustomerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.community.api.endpoint.dto.LoggedInUser;
import com.community.api.jwt.services.UserDetailsImpl;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.service.JTCustomerService;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Resource(name = "jtCustomerService")
	private JTCustomerService jtCustomerService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

				LoggedInUser loggedInUser = jwtUtils.getUserFromToken(jwt);
				JTCustomer customer = jtCustomerService.findById(loggedInUser.getId());
				loggedInUser.setUserName(customer.getUsername());
				CustomerState.setCustomer(customer);
				List<GrantedAuthority> authorities = loggedInUser.getRoles().stream()
						.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
				UserDetails userDetails = new UserDetailsImpl(loggedInUser.getId(), loggedInUser.getPrimaryContact(),
						loggedInUser.getEmail(), loggedInUser.getPassword(), loggedInUser.getUserName(), authorities);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
}
