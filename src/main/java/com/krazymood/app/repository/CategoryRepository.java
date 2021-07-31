package com.krazymood.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krazymood.app.component.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findAllByVisibility(boolean i);
}
