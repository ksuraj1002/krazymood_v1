package com.krazymood.app.repository;

import com.krazymood.app.entities.Legends;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LegendRepository extends JpaRepository<Legends,Long> {

	Set<Legends> findByLanguageAndCategory_Id(String language, Integer category);

	Set<Legends> findByLanguageAndSubCategory_Id(String language, Integer subctgory);
}
