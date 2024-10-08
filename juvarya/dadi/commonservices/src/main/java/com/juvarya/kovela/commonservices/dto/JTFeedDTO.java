package com.juvarya.kovela.commonservices.dto;

import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTFeedDTO {
	private Long id;
	private String description;
	private String latitude;
	private String longitude;
	private String feedType;
	private Date creationTime;
	private Long customer;
	private Long jtProfile;
	private Long donar;
	private Long city;
	private List<MultipartFile> files;
	private List<JTMediaDTO> mediaList;
	private Boolean following;
	private Boolean like;
	private Long likesCount;
	private JTProfileDTO jtProfileDTO;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getFeedType() {
		return feedType;
	}

	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Long getCustomer() {
		return customer;
	}

	public void setCustomer(Long customer) {
		this.customer = customer;
	}

	public Long getJtProfile() {
		return jtProfile;
	}

	public void setJtProfile(Long jtProfile) {
		this.jtProfile = jtProfile;
	}

	public Long getDonar() {
		return donar;
	}

	public void setDonar(Long donar) {
		this.donar = donar;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public List<JTMediaDTO> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<JTMediaDTO> mediaList) {
		this.mediaList = mediaList;
	}

	public Boolean getFollowing() {
		return following;
	}

	public void setFollowing(Boolean following) {
		this.following = following;
	}

	public Boolean getLike() {
		return like;
	}

	public void setLike(Boolean like) {
		this.like = like;
	}

	public Long getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(Long likesCount) {
		this.likesCount = likesCount;
	}

	public JTProfileDTO getJtProfileDTO() {
		return jtProfileDTO;
	}

	public void setJtProfileDTO(JTProfileDTO jtProfileDTO) {
		this.jtProfileDTO = jtProfileDTO;
	}
}
