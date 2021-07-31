package com.krazymood.app.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krazymood.app.component.Category;
import com.krazymood.app.component.SubCategory;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
public class Contents {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	
	@ManyToOne
	public Category category;
	
	@ManyToOne
	public SubCategory subCategory;
	
	@Column(name="language",columnDefinition = "varchar(255) default 'hindi'")
	public String language;
	
	public String imgname;
	
	@Column(name="header",columnDefinition = "varchar(1000) COLLATE utf8mb4_unicode_ci")
	public String header;
	 
	//public Blob imgSize;
	@Column(columnDefinition = "LONGTEXT COLLATE utf8mb4_unicode_ci")
	public String text;

	public String userName;

	public long txtLngth;

	@Column(columnDefinition = "LONGTEXT COLLATE utf8mb4_unicode_ci")
	public String engHeader;
	
	public long views;
	
	public boolean isSpecific;
	
	@ManyToOne
	public Legends legends;

	@Transient
	public long legendId;

	private LocalDateTime createdOn = LocalDateTime.now();
	private LocalDateTime updatedOn = LocalDateTime.now();

	@PrePersist
	public void setCreationspot(){
		this.createdOn = LocalDateTime.now();
		this.updatedOn = LocalDateTime.now();
	}
	@PreUpdate
	public void setUpationspot(){
		this.updatedOn = LocalDateTime.now();
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

	public Contents() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contents(String userName,String text,String header, long txtLngth, Category category, SubCategory subCategory,String language,String imgname, Legends legends,String engHeader,boolean isSpecific) {
		this.userName=userName;
		this.text = text;
		this.txtLngth = txtLngth;
		this.category = category;
		this.subCategory = subCategory;
		this.imgname = imgname;
		this.header = header;
		this.language = language;
		this.legends = legends;
		this.engHeader=engHeader;
		this.isSpecific=isSpecific;
	}
	
	public Contents(long id,String imgname,String text,String engHeader,String header, Category category,SubCategory subCategory,long views) {
		this.id=id;
		this.imgname=imgname;
		this.text=text;
		this.engHeader=engHeader;
		this.header=header;
		this.category=category;
		this.subCategory=subCategory;
		this.views=views;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getTxtLngth() {
		return txtLngth;
	}

	public void setTxtLngth(long txtLngth) {
		this.txtLngth = txtLngth;
	}

	public String getImgname() {
		return imgname;
	}

	public void setImgname(String imgname) {
		this.imgname = imgname;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}


	public Legends getLegends() {
		return legends;
	}

	public void setLegends(Legends legends) {
		this.legends = legends;
	}

	public long getLegendId() {
		return legendId;
	}

	public void setLegendId(long legendId) {
		this.legendId = legendId;
	}

	public String getEngHeader() {
		return engHeader;
	}

	public void setEngHeader(String engHeader) {
		this.engHeader = engHeader;
	}

	public long getViews() {
		return views;
	}

	public void setViews(long views) {
		this.views = views;
	}

	public boolean isSpecific() {
		return isSpecific;
	}

	public void setSpecific(boolean isSpecific) {
		this.isSpecific = isSpecific;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Contents{" +
				"id=" + id +
				", category=" + category +
				", subCategory=" + subCategory +
				", language='" + language + '\'' +
				", imgname='" + imgname + '\'' +
				", header='" + header + '\'' +
				", text='" + text + '\'' +
				", txtLngth=" + txtLngth +
				", engHeader='" + engHeader + '\'' +
				", views=" + views +
				", isSpecific=" + isSpecific +
				", legends=" + legends +
				", legendId=" + legendId +
				'}';
	}
}
