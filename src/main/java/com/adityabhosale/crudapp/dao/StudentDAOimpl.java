package com.adityabhosale.crudapp.dao;

import com.adityabhosale.crudapp.exception.DAOException;
import com.adityabhosale.crudapp.model.Student;
import com.adityabhosale.crudapp.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOimpl implements StudentDAO {
    private static final String INSERT_SQL = "INSERT INTO student(name,email,mobile) VALUES(?,?,?)";
    private static final String UPDATE_SQL = "DELETE FROM student WHERE id=?";
    private static final String DELETE_SQL = "UPDATE student SET name=?,email=?,mobile=? WHERE id=?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM student WHERE id=?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM student ORDER BY id";






    @Override
    public void insert(Student student)  {

        try(Connection con = JDBCUtils.fetchConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_SQL)){

                ps.setString(1, student.getName());
                ps.setString(2,student.getEmail());
                ps.setString(3,student.getMobile());

                ps.executeUpdate();


        } catch (SQLException e){

            throw new DAOException("Unable to Insert Student",e);

        }
    }

    @Override
    public void update(Student student) {
        try(Connection con = JDBCUtils.fetchConnection();
            PreparedStatement ps = con.prepareStatement(UPDATE_SQL)){

            ps.setString(1, student.getName());
            ps.setString(2,student.getEmail());
            ps.setString(3,student.getMobile());

            ps.setInt(4,student.getId());

            ps.executeUpdate();

        } catch (SQLException e){

            throw new DAOException("Unable to update Student",e);

        }

    }

    @Override
    public void delete(int id) {
        try(Connection con = JDBCUtils.fetchConnection();
            PreparedStatement ps = con.prepareStatement(DELETE_SQL)){

            ps.setInt(1,id);
            ps.executeUpdate();

        } catch (SQLException e){

            throw new DAOException("Unable to delete Student",e);

        }

    }

    @Override
    public Student getStudentById(int id) {
        Student student = null;
        try(Connection con = JDBCUtils.fetchConnection();
            PreparedStatement ps = con.prepareStatement(SELECT_BY_ID_SQL)){
            ps.setInt(1,id);

            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setMobile(rs.getString("mobile"));

                }
            }


        } catch (SQLException e){

            throw new DAOException("Unable to fetch Student",e);

        }
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try(Connection con = JDBCUtils.fetchConnection();
            PreparedStatement ps = con.prepareStatement(SELECT_ALL_SQL);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setMobile(rs.getString("mobile"));

                students.add(student);
            }


        } catch (SQLException e){

            throw new DAOException("Unable to fetch Student",e);

        }
        return students;
    }
}
