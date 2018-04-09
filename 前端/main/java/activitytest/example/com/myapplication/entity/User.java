package activitytest.example.com.myapplication.entity;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

import activitytest.example.com.myapplication.R;
import activitytest.example.com.myapplication.base.BaseModel;

/**
 * Created by lawrence on 2017/3/4.
 */

public class User extends BaseModel {
    // default is no login

    private boolean isLogin = false;
    private boolean isRegister=false;
    private String name="1";
    private String password;
    private String netname;
    private String photonumber;
    private String cradit;
    private String brief;
    private String lingshi;
    private String skill1;
    private String skill2;
    private String skill3;
    private String skill4;
    private String sex;
    private String location;
    private String birthday;
    private  String work;
    private int photolocal;
    private String token="";
    public String getSkill2() {
        return skill2;
    }

    public String getSkill3() {
        return skill3;
    }

    public String getSkill4() {
        return skill4;
    }

    public void setSkill4(String skill4) {
        this.skill4 = skill4;
    }

    public void setSkill3(String skill3) {
        this.skill3 = skill3;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }



    public String getSkill1() {
        return skill1;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }



    public String getToken() {
        return user.token;
    }

    public void setToken(String token) {
        user.token = token;
    }

    // single instance for login
    static private User user = new User();
    static public User getInstance () {
        if (User.user == null) {
            User.user = new User();
        }
        return User.user;
    }
    public String getUserName(){
      return user.getName();
   }
   public void setUserName(String name){
       user.name=name;
   }
    public User () {}
    public String getName () {
        return this.name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getPass () {
        return this.password;
    }

    public void setPass (String password) {
        this.password = password;
    }
    public Boolean getLogin () {
        return this.isLogin;
    }

    public void setLogin (boolean isLogin) {
        this.isLogin = isLogin;
    }
    public Boolean getRegister () {
        return this.isRegister;
    }

    public void setRegister (boolean isRegister) {
        this.isRegister = isRegister;
    }
    public String  getNetname(){return this.netname;}
    public void setNetname(String netname){this.netname=netname;};
    public String  getPhotonumber(){return this.photonumber;}
    public void setPhotonumber(String k){this.photonumber=k;
        if (k.equals("1")){
            photolocal= R.drawable.face1;
        }else if (k.equals("2")){
            photolocal= R.drawable.face2;
        }else if (k.equals("3")){
            photolocal= R.drawable.face3;
        }
        else if (k.equals("4")){
            photolocal= R.drawable.face4;
        }
        else if (k.equals("5")){
            photolocal= R.drawable.face5;
        }
        else if (k.equals("6")){
            photolocal= R.drawable.face6;
        }
        else if (k.equals("7")){
            photolocal= R.drawable.face7;
        }
        else if (k.equals("8")){
            photolocal= R.drawable.face8;
        }
        else if (k.equals("9")){
            photolocal= R.drawable.face9;
        }
        else if (k.equals("10")){
            photolocal= R.drawable.face10;
        }
        else if (k.equals("11")){
            photolocal= R.drawable.face11;
        }
        else {
            photolocal= R.drawable.face12;
        }
    };
    public String  getCradit(){return this.cradit;}
    public void setCradit(String netname){this.cradit=netname;};

    public int getPhotolocal() {
        return photolocal;
    }
    public String  getBrief(){return this.brief;}
    public void setBrief(String netname){this.brief=netname;};
    public String  getLingshi(){return this.lingshi;}
    public void setLingshi(String netname){this.lingshi=netname;};
    public String  getSex(){return this.sex;}
    public void setSex(String netname){this.sex=netname;};
    public String  getLocation(){return this.location;}
    public void setLocation(String netname){this.location=netname;};
    public String  getBirthday(){return this.birthday;}
    public void setBirthday(String netname){this.birthday=netname;};
    public String  getWork(){return this.work;}
    public void setWork(String netname){this.work=netname;};


}

