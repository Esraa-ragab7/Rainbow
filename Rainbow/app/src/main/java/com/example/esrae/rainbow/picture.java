package com.example.esrae.rainbow;


/**
 * Created by esrae on 10/27/2016.
 */

public class picture {
    private String pic_Name ;
    private String pic_Link;

    public picture(String pic_Link, String pic_Name) {
        this.pic_Link = pic_Link;
        this.pic_Name = pic_Name;
    }

    public String getPic_Name() {
        return pic_Name;
    }

    public void setPic_Name(String pic_Name) {
        this.pic_Name = pic_Name;
    }

    public String getPic_Link() {
        return pic_Link;
    }

    public void setPic_Link(String pic_Link) {
        this.pic_Link = pic_Link;
    }
}
