package com.juvarya.kovela.customer.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "JTUSER", indexes = {
		@Index(name = "idx_userid", columnList = "id", unique = true) }, uniqueConstraints = {
				@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email") })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "FIRST_NAME")
	private String fullName;

	@Size(max = 20)
	private String username;

	@Size(max = 50)
	@Email
	private String email;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@Column(name = "PROFILE_PICTURE")
	private Long profilePicture;

	@NotBlank
	@Column(name = "PRIMARY_CONTACT")
	private String primaryContact;

	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "GOTHRA")
	private String gothra;

	@Column(name = "WHATSAPP_ENABLED")
	private Boolean whatsAppEnabled;

	@Column(name = "CREATION_TIME")
	private Date creationTime;

	@Column(name = "ZODIAC_SIGN")
	private String zodiacSign;

	@Column(name = "TYPE")
	private String type;

	@OneToOne
	@JoinColumn(name = "JT_POSTALCODE")
	private JTPostalCodeModel postalCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Long getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(Long profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGothra() {
		return gothra;
	}

	public void setGothra(String gothra) {
		this.gothra = gothra;
	}

	public Boolean getWhatsAppEnabled() {
		return whatsAppEnabled;
	}

	public void setWhatsAppEnabled(Boolean whatsAppEnabled) {
		this.whatsAppEnabled = whatsAppEnabled;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getZodiacSign() {
		return zodiacSign;
	}

	public void setZodiacSign(String zodiacSign) {
		this.zodiacSign = zodiacSign;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JTPostalCodeModel getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(JTPostalCodeModel postalCode) {
		this.postalCode = postalCode;
	}

}
