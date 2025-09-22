package com.thecodealchemist.spring_boot_project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class StudentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void readStudentFromDb() {
        Map<String, Object> result=this.jdbcTemplate.queryForMap("select * from students where id=?" , 2 );
        System.out.println(result);
    }
}
