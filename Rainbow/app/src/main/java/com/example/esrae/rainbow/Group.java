package com.example.esrae.rainbow;


import java.util.List;

/**
 * Created by esrae on 10/27/2016.
 */

public class Group {
    private String Group_Name ;
    private String Admin_Name;

    public Group(String group_Name, String admin_Name) {
        Group_Name = group_Name;
        Admin_Name = admin_Name;
    }

    public Group() {
    }

    public void setGroup_Name(String group_Name) {
        Group_Name = group_Name;
    }


    public void setAdmin_Name(String admin_Name) {
        Admin_Name = admin_Name;
    }

    public String getGroup_Name() {
        return Group_Name;
    }

    public String getAdmin_Name() {
        return Admin_Name;
    }

}
