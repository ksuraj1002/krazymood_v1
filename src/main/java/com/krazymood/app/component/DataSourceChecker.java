package com.krazymood.app.component;


import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSourceChecker {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void checkDataSource() {

        System.out.println(
            "DataSource class: " +
            dataSource.getClass().getName()
        );
    }
}