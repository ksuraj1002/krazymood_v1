package com.krazymood.app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.krazymood.app.component.SubCategory;

@Entity
public class Feedbacks {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public String texts;
	public long txtLength;
	public String guestUUID;
	
	@ManyToOne
	public Contents contents;
	
	@ManyToOne
	public SubCategory subCategory;
	
	@ManyToOne
	public Visitors visitors;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTexts() {
		return texts;
	}
	public void setTexts(String texts) {
		this.texts = texts;
	}
	public long getTxtLength() {
		return txtLength;
	}
	public void setTxtLength(long txtLength) {
		this.txtLength = txtLength;
	}
	public Contents getContents() {
		return contents;
	}
	public void setContents(Contents contents) {
		this.contents = contents;
	}
	public SubCategory getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}
	public String getGuestUUID() {
		return guestUUID;
	}
	public void setGuestUUID(String guestUUID) {
		this.guestUUID = guestUUID;
	}
	public Visitors getVisitors() {
		return visitors;
	}
	public void setVisitors(Visitors visitors) {
		this.visitors = visitors;
	}
	
	
}
