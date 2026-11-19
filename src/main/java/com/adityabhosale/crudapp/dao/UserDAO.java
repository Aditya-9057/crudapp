package com.adityabhosale.crudapp.dao;

import com.adityabhosale.crudapp.model.User;

public interface UserDAO {
    void register(User user); //insert query
    User login(String username, String password);//select query
    boolean exists(String username); //select query

}
