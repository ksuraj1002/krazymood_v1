package com.krazymood.app.component;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krazymood.app.entities.Contents;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	public String title;
	public boolean visibility;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category",fetch = FetchType.LAZY)
	//@JsonIgnore
	public Set<SubCategory> subCategory;
	
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy = "category")
	@JsonIgnore
	public Set<Contents> contents;
	
	
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(String title,boolean visibility) {
		this.title = title;
		this.visibility = visibility;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	public Set<SubCategory> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Set<SubCategory> subCategory) {
		this.subCategory = subCategory;
	}

	public Set<Contents> getContents() {
		return contents;
	}

	public void setContents(Set<Contents> contents) {
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", title=" + title + ", visibility=" + visibility + ", subCategory=" + subCategory
				+ ", contents=" + contents + "]";
	}

	
}
