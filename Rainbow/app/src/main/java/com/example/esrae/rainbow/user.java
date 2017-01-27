package com.example.esrae.rainbow;

import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by esrae on 10/27/2016.
 */

public class user  {
    private String User_Name ;
    private String User_Email;
    private String U_id;
    public user(String user_Name, String user_Email) {
        User_Name = user_Name;
        User_Email = user_Email;
    }

    public user(String user_Name) {
        User_Name = user_Name;
    }

    public String getU_id() {
        return U_id;
    }

    public void setU_id(String u_id) {
        U_id = u_id;
    }

    public user(String user_Name, String user_Email, String u_id) {
        User_Name = user_Name;
        User_Email = user_Email;
        U_id = u_id;
    }

    public user() {
    }

    public String getUser_Name() {
        return User_Name;
    }

    public String getUser_Email() {
        return User_Email;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public void setUser_Email(String user_Email) {
        User_Email = user_Email;
    }
}
