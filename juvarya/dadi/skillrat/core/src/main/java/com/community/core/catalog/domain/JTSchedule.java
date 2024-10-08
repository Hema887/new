package com.community.core.catalog.domain;

import java.util.Date;

public interface JTSchedule {
	Long getId();
	void setId(Long id);
	Date getStartDate();
	void setStartDate(Date startDate);
	String getDuration();
	void setDuration(String duration);
	JTProduct getJtProduct();
	void setJtProduct(JTProduct jtProduct);
	boolean isActive();
	void setActive(boolean active);
	String getStatus();
	void setStatus(String status);
	JTCustomer getTrainer();
	void setTrainer(JTCustomer trainer);
	public String getStartTime();
	public void setStartTime(String startTime);
	public String getEndTime();
	public void setEndTime(String endTime);
}
