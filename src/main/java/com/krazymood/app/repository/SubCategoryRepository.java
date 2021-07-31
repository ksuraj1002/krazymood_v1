package com.krazymood.app.repository;

import java.util.List;
import java.util.Set;

import com.krazymood.app.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import com.krazymood.app.component.SubCategory;


public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

	Set<SubCategory> findByCategory_Id(Integer category);

    List<SubCategory> findAllByVisibility(boolean b);
}
