package activitytest.example.com.myapplication.entity;

import android.graphics.Bitmap;

import activitytest.example.com.myapplication.base.BaseModel;

/**
 * Created by lawrence on 2017/2/22.
 */

public class Offer extends BaseModel{
    private String id;
    private String name;
    private String paying;
    private String time;
    private String picture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String description;
    private Bitmap picturee;
    private byte[] headshot;//头像
    public Offer(){
    }
    public void setTime(String time){this.time=time;}
    public void setName(String name){this.name=name;}
    public void setPaying(String paying){this.paying=paying;}
    public void setPicture(String picture){this.picture=picture;}
    public void setDescription(String description){this.description=description;}
    public void setPicturee(Bitmap picturee){this.picturee=picturee;}
    public void setHeadshot(byte[] headshot) {
        this.headshot = headshot;
    }
    public byte[] getHeadshot() {
        return headshot;
    }
    public String getTitle(){
        return name;
    }
    public String getPaying(){
        return paying;
    }
    public String getTime(){
        return time;
    }
    public String getPicture() {return picture;}
    public Bitmap getPicturee(){return picturee;
    }
    public String getDescription(){
        return description;
    }
}
