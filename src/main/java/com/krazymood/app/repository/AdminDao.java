package com.krazymood.app.repository;

import com.krazymood.app.entities.Users;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AdminDao {
    @PersistenceContext EntityManager entityManager;

    public List<Users> findAllByIsWatched(boolean flag) {
        String sql="SELECT U FROM Users U WHERE U.isWatched="+flag;
        List<Users> users = (List<Users>) entityManager.createQuery(sql,Users.class);
        return users;
    }
}
