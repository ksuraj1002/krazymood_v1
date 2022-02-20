
package com.krazymood.app.entities;

import com.krazymood.app.component.Category;
import com.krazymood.app.component.SubCategory;

import javax.persistence.*;
import java.time.LocalDateTime;


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

	@Transient
	public long legendId;

	@Transient Integer categoryId,subCategoryId,pageNum;
	@Transient String isSpecificStr,tempCategoryName;

	private LocalDateTime createdOn = LocalDateTime.now();
	private LocalDateTime updatedOn = LocalDateTime.now();

	public boolean isSpecific() {
		return isSpecific;
	}

	public String getTempCategoryName() {
		return tempCategoryName;
	}

	public void setTempCategoryName(String tempCategoryName) {
		this.tempCategoryName = tempCategoryName;
	}



	@PrePersist
	public void setCreationspot(){
		this.createdOn = LocalDateTime.now();
		this.updatedOn = LocalDateTime.now();
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	@PreUpdate
	public void setUpationspot(){
		this.updatedOn = LocalDateTime.now();
	}


	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public String getIsSpecificStr() {
		return isSpecificStr;
	}

	public void setIsSpecificStr(String isSpecificStr) {
		this.isSpecificStr = isSpecificStr;
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

	public boolean isSpecific(boolean b) {
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
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
				", legendId=" + legendId +
				'}';
	}
}
