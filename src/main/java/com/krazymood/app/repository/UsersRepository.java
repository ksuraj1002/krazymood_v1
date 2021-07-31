package com.krazymood.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krazymood.app.entities.Users;

import java.util.Set;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Set<Users> findAllByIsWatched(boolean b);
}
