package activitytest.example.com.myapplication.entity;

import activitytest.example.com.myapplication.R;
import activitytest.example.com.myapplication.base.BaseModel;

/**
 * Created by lawrence on 2017/3/9.
 */


public class Message  extends BaseModel{
    private String specific;
    private String  mType;
    private int photo;
    public Message(){
    }
    public String getSpecific(){
        return specific;
    }
    public void setSpecific(String specific){this.specific=specific;}
    public String getmType(){
        return mType;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void setmType(String mType){this.mType=mType;
    if (this.mType.equals("1")){
            this.photo= R.drawable.getoffer;
        }else if (this.mType.equals("2")){
            this.photo=R.drawable.offergetted;
        }
        else  {

        this.photo=R.drawable.appointment;
    }
    }
    public int getPhoto(){
        return this.photo;
    }

}
