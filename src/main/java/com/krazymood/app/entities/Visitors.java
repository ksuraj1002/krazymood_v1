package com.krazymood.app.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Visitors {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public String visitorName;
	public String email;
	public String mobile;
	public boolean isVerified;
	public String macAddress;
	
	@OneToMany()
	public List<Feedbacks> feedbacks;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Feedbacks> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<Feedbacks> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	
	
}
