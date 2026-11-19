package com.adityabhosale.crudapp.dao;

import com.adityabhosale.crudapp.exception.DAOException;
import com.adityabhosale.crudapp.model.User;
import com.adityabhosale.crudapp.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOimpl implements UserDAO {
    private static final String REGISTER_SQL = "INSERT INTO users(name,mobile,username,password) VALUES (?,?,?,?)";
    private static final String LOGIN_SQL = "SELECT * FROM users WHERE username=? AND password=?";
    private static final String EXISTS_SQL = "SELECT 1 FROM users WHERE username=? ";


    @Override
    public void register(User user) {
       try(Connection con = JDBCUtils.fetchConnection();
           PreparedStatement ps = con.prepareStatement(REGISTER_SQL)){

           ps.setString(1, user.getName());
           ps.setString(2, user.getMobile());
           ps.setString(3, user.getUsername());
           ps.setString(4, user.getPassword());

           ps.executeUpdate();

       }catch(SQLException e){
           throw new DAOException("Register Failed",e);
       }
    }

    @Override
    public User login(String username, String password) {
        User user = null;

        try(Connection con = JDBCUtils.fetchConnection();
            PreparedStatement ps = con.prepareStatement(LOGIN_SQL)){

            ps.setString(1, username);
            ps.setString(2, password);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    user = new User(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5));
                }
            }

        }catch (SQLException e){
            throw new DAOException("Login Failed",e);
        }


        return user;
    }

    @Override
    public boolean exists(String username) {
        try(Connection con = JDBCUtils.fetchConnection();
            PreparedStatement ps = con.prepareStatement(EXISTS_SQL)){

            ps.setString(1, username);

            try(ResultSet rs = ps.executeQuery()){
                return (rs.next()); //return 1;
            }

        }catch(SQLException e){
            throw new DAOException("Check User Failed",e);
        }

    }
}
