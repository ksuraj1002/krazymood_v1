package com.krazymood.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krazymood.app.entities.Visitors;

public interface VisitorRepository extends JpaRepository<Visitors, Long> {

	Visitors findByMobileOrEmail(String cnxnMode, String cnxnMode2);

	Visitors findByEmail(String email);

}
