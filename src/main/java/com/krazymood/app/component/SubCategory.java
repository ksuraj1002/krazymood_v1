package com.krazymood.app.component;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krazymood.app.entities.Contents;

@Entity
public class SubCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	public String subCatName;
	
	
	  @ManyToOne(cascade = CascadeType.ALL)
	  @JsonIgnore
	  public Category category;

	  public boolean visibility=true;
	 
	
	 @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "subCategory")
	 @JsonIgnore
	public Set<Contents> contents;
	
	
	public SubCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

    public SubCategory(Integer subCategoryId) {
		this.id=subCategoryId;
    }

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubCatName() {
		return subCatName;
	}
	public void setSubCatName(String subCatName) {
		this.subCatName = subCatName;
	}
	
	
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public SubCategory(String subCatName, Category category) {
		super();
		this.subCatName = subCatName;
		this.category = category;
	}
	
	public Set<Contents> getContents() {
		return contents;
	}
	public void setContents(Set<Contents> contents) {
		this.contents = contents;
	}
	
	

}
