package com.juvarya.kovela.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.juvarya.kovela.commonservices.dto.LoggedInUser;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class JwtUtils {
    private static final String ROLES = "roles";

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${kovela.secret.key}")
    private String jwtSecret="kovelasecretkey";

//    @Value("${bezkoder.app.jwtExpirationMs}")
    private int jwtExpirationMs=60 * 24 * 60 * 60 * 1000;

    @Value("${skillrat.app.refreshTokenExpiryInDays:10}")
    private int refreshTokeExpirationInDays;

    public String generateJwtToken(LoggedInUser loggedInUserDetails) throws JsonProcessingException {
    	String json = getJsonFromLoggedInUser(loggedInUserDetails);
        return Jwts.builder()
                .setSubject(json)
                .addClaims(Map.of(ROLES, loggedInUserDetails.getRoles()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private long getExpiration(int expiryInDays) {
        return new Date().toInstant()
                .plus(expiryInDays, ChronoUnit.DAYS)
                .toEpochMilli();
    }

    public String generateRefreshToken(LoggedInUser loggedInUserDetails) throws JsonProcessingException {
    	String json = getJsonFromLoggedInUser(loggedInUserDetails);
        return Jwts.builder()
                .setSubject(json)
                .setIssuedAt(new Date())
                .addClaims(Map.of(ROLES, loggedInUserDetails.getRoles()))
                .setExpiration(new Date(getExpiration(refreshTokeExpirationInDays)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

	private String getJsonFromLoggedInUser(LoggedInUser loggedInUserDetails) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	String json = ow.writeValueAsString(loggedInUserDetails);
		return json;
	}


    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    @SuppressWarnings("unchecked")
	public LoggedInUser getUserFromToken(String token) throws JsonProcessingException {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token);
        List<String> roles = (List<String>) claimsJws.getBody().get(ROLES);
        LoggedInUser loggedInUser = new LoggedInUser();
        if(null != claimsJws.getBody().getSubject()) {
        	ObjectMapper mapper = new ObjectMapper();
        	loggedInUser=mapper.readValue(claimsJws.getBody().getSubject(), LoggedInUser.class);
        }
        loggedInUser.setRoles(new HashSet<>(roles));
        return loggedInUser;
    }
}
