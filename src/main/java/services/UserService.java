package services;

import controllers.DB;


import java.sql.Connection;

public class UserService {
    Connection connection;

    public  UserService(){
        connection = DB.getConnection();
    }




}
