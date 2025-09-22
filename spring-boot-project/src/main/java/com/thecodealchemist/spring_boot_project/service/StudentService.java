package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;
    public void fetchstudent() {
       studentDao.readStudentFromDb();
    }
}
