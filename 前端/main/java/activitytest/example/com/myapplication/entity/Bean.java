package activitytest.example.com.myapplication.entity;

import activitytest.example.com.myapplication.R;
import activitytest.example.com.myapplication.base.BaseModel;
import activitytest.example.com.myapplication.util.SDUtil;

/**
 * Created by lawrence on 2017/3/30.
 */

public class Bean extends BaseModel{
    private int iconId;
    private String iconIdget;
    private String username;
    private String userskill;
    private String usertime;

    public Bean() {
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconIdget(String k) {
        this.iconIdget = k;
        if (k.equals("1")){
            iconId= R.drawable.face1;
        }else if (k.equals("2")){
            iconId= R.drawable.face2;
        }else if (k.equals("3")){
            iconId= R.drawable.face3;
        }
        else if (k.equals("4")){
            iconId= R.drawable.face4;
        }
        else if (k.equals("5")){
            iconId= R.drawable.face5;
        }
        else if (k.equals("6")){
            iconId= R.drawable.face6;
        }
        else if (k.equals("7")){
            iconId= R.drawable.face7;
        }
        else if (k.equals("8")){
            iconId= R.drawable.face8;
        }
        else if (k.equals("9")){
            iconId= R.drawable.face9;
        }
        else if (k.equals("10")){
            iconId= R.drawable.face10;
        }
        else if (k.equals("11")){
            iconId= R.drawable.face11;
        }
        else {
            iconId= R.drawable.face12;
        }
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getIconIdget() {
        return iconIdget;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String title) {
        this.username= title;
    }

    public String getUserskill() {
        return userskill;
    }

    public void setUserskill(String content) {
        this.userskill = content;
    }

    public String getUsertime() {
        return usertime;
    }

    public void setUsertime(String comments) {
        this.usertime = comments;
    }
}
