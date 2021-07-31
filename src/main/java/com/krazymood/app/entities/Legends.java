package com.krazymood.app.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.UpdateTimestamp;

import com.krazymood.app.component.Category;
import com.krazymood.app.component.SubCategory;

@Entity
public class Legends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String legendName;
    public String language;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    public Date dob;
    public String profilePic;
    public String description;

    @ManyToOne
    public Category category;
    
    @ManyToOne
    public SubCategory subCategory;

    @Transient
    public List<String> tranlegendName;

    @Transient
    public int categoryId;

    public Legends(String language, Category category, String legenName) {
        this.language = language;
        this.legendName = legenName;
        this.category = category;
    }

  
    public Legends(Category category,SubCategory subCategory,String language, String legendName, String description, Date dob , String profilePic) {
        this.category = category;
        this.subCategory = subCategory;
        this.language = language;
        this.legendName = legendName;
        this.description = description;
        this.profilePic = profilePic;
        this.dob =dob;
    }

    public Legends() {
		// TODO Auto-generated constructor stub
	}


	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLegendName() {
        return legendName;
    }

    public void setLegendName(String legendName) {
        this.legendName = legendName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getTranlegendName() {
        return tranlegendName;
    }

    public void setTranlegendName(List<String> tranlegendName) {
        this.tranlegendName = tranlegendName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}


}
