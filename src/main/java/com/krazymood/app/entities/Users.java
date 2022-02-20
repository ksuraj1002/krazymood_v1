package com.krazymood.app.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public String name;
	@Column(columnDefinition = "text",nullable = false,unique = true)
	public String email;
	@Column(columnDefinition = "text",nullable = false)
	public String contents;
	public String subCategory;
	public boolean isWatched;
	public boolean isSubscribed;

	private LocalDateTime createdOn = LocalDateTime.now();
	private LocalDateTime updatedOn = LocalDateTime.now();
	
	@Transient
	public String hiddenURL;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public boolean isWatched() {
		return isWatched;
	}
	public void setWatched(boolean isWatched) {
		this.isWatched = isWatched;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getHiddenURL() {
		return hiddenURL;
	}
	public void setHiddenURL(String hiddenURL) {
		this.hiddenURL = hiddenURL;
	}

	public boolean isSubscribed() {
		return isSubscribed;
	}

	public void setSubscribed(boolean subscribed) {
		isSubscribed = subscribed;
	}
}
