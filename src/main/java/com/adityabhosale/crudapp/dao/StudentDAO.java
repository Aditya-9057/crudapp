package com.adityabhosale.crudapp.dao;

import com.adityabhosale.crudapp.model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {
    void insert(Student student);
    void update(Student student);
    void delete(int id);
    Student getStudentById(int id);
    List<Student> getAllStudents();
}
