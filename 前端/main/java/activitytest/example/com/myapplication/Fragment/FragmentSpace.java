package activitytest.example.com.myapplication.Fragment;

/**
 * Created by lawrence on 2017/3/7.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import activitytest.example.com.myapplication.BriefActivity;
import activitytest.example.com.myapplication.ChangefaceActivity;
import activitytest.example.com.myapplication.CraditActivity;
import activitytest.example.com.myapplication.LingshiActivity;
import activitytest.example.com.myapplication.MoreinformationActivity;
import activitytest.example.com.myapplication.R;
import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseUiTwo;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.Offer;
import activitytest.example.com.myapplication.entity.User;
import activitytest.example.com.myapplication.myskillActivity;

import static android.app.Activity.RESULT_OK;

public class FragmentSpace extends BaseUiTwo implements View.OnClickListener{
  LinearLayout jianjie;
    LinearLayout gengduoziliao;
    LinearLayout cradit;
    LinearLayout lingshi;
    LinearLayout myskill;
    TextView briftext;
    TextView lingshitext;
    TextView netname;
     private User user;
    ImageView facephoto;
    HashMap<String, String> para;
    ArrayList<User> userlist =new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_space, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        jianjie=(LinearLayout)getActivity().findViewById(R.id.jianjie);
        gengduoziliao=(LinearLayout)getActivity().findViewById(R.id.gengduoziliao);
        lingshi=(LinearLayout)getActivity().findViewById(R.id.lingshii);
        facephoto=(ImageView)getActivity().findViewById(R.id.facephoto);
        myskill=(LinearLayout)getActivity().findViewById(R.id.myskill);
        briftext=(TextView)getActivity().findViewById(R.id.briftext);
        lingshitext=(TextView)getActivity().findViewById(R.id.lingshi1);
        netname=(TextView)getActivity().findViewById(R.id.netname);
        gengduoziliao.setOnClickListener(this);
        myskill.setOnClickListener(this);
        lingshi.setOnClickListener(this);
        jianjie.setOnClickListener(this);
        facephoto.setOnClickListener(this);
        cradit=(LinearLayout)getActivity().findViewById(R.id.cradit);
        cradit.setOnClickListener(this);
//        Intent intent=getActivity().getIntent();
//        String username=intent.getStringExtra("username");
        //借用当前Activity中intent接收参数，不知是否可行。
        user=new User();
    User usr=User.getInstance();
       String token= usr.getToken();
        para = new HashMap<String, String>();
        para.put("tokenkey",token);
        try {
            user.setBirthday("");
            user.setBrief("");
            user.setCradit("600");
            user.setLingshi("");
            user.setLocation("");
            user.setNetname("");
            user.setSex("");
            user.setSkill1("");
            user.setSkill2("");
            user.setSkill3("");
            user.setSkill4("");
            user.setWork("");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        try {
            doTaskAsync(C.task.getuser,C.api.getuser,para);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Glide.with(getContext()).load(user.getPhotolocal()).into(facephoto);
        netname.setText(user.getNetname());
        briftext.setText(user.getBrief());
    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.facephoto:
                Intent intent6=new Intent(getActivity(), ChangefaceActivity.class);
                startActivityForResult(intent6,2);
                break;
            case R.id.jianjie:
                Intent intent1=new Intent(getActivity(),BriefActivity.class);
                intent1.putExtra("brief",user.getBrief());
                startActivityForResult(intent1,1);
                break;
            case R.id.gengduoziliao:
                Intent intent2=new Intent(getActivity(),MoreinformationActivity.class);
                intent2.putExtra("birthday",user.getBirthday());
                intent2.putExtra("sex",user.getSex());
                intent2.putExtra("work",user.getWork());
                intent2.putExtra("location",user.getLocation());
                startActivityForResult(intent2,3);
                break;
           case R.id.cradit:
                Intent intent3=new Intent(getActivity(),CraditActivity.class);
                intent3.putExtra("cradit",user.getCradit());
                getActivity().startActivity(intent3);
                break;
            case R.id.lingshii:
                Intent intent4=new Intent(getActivity(),LingshiActivity.class);
                intent4.putExtra("lingshi",user.getLingshi());
                startActivityForResult(intent4,5);
                break;
            case R.id.myskill:
                Intent intent5=new Intent(getActivity(),myskillActivity.class);
                intent5.putExtra("skill1",user.getSkill1());
                intent5.putExtra("skill2",user.getSkill2());
                intent5.putExtra("skill3",user.getSkill3());
                intent5.putExtra("skill4",user.getSkill4());
                startActivityForResult(intent5,4);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case 1:
                if (resultCode==RESULT_OK){
                    try {
                        String returnedData=data.getStringExtra("data_return");
                        Log.d("returnbrif",returnedData);
                        briftext.setText(returnedData);
                        user.setBrief(returnedData);
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }

                }
                break;
            case 2:
                if (resultCode==RESULT_OK){
                    try{
                        int returnedImage=data.getIntExtra("data_image",R.id.face2);
                        Log.d("returnphoto"+returnedImage,"");
                        Glide.with(getContext()).load(returnedImage).into(facephoto);
                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                break;
            case 3:
                if (resultCode==RESULT_OK){
                    try{
                        user.setSex(data.getStringExtra("data_return1"));
                        user.setLocation(data.getStringExtra("data_return2"));
                        user.setWork(data.getStringExtra("data_return3"));
                        user.setBirthday(data.getStringExtra("data_return4"));
                    }  catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                break;
            case 4:
                if (resultCode==RESULT_OK){
                    try{
                        user.setSkill1(data.getStringExtra("data_return5"));
                        user.setSkill2(data.getStringExtra("data_return6"));
                        user.setSkill3(data.getStringExtra("data_return7"));
                        user.setSkill4(data.getStringExtra("data_return8"));
                    }  catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                break;
            case 5: if (resultCode==RESULT_OK){
                try{
                    user.setLingshi(data.getStringExtra("data_return0"));
                }  catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case C.task.getuser:
                try {
                    String a=message.getMessage();
                    if (!a.equals(null))
                        Log.d("message is ",a);
                     userlist=(ArrayList<User>) message.getResultList("User");
                    Iterator<User> it = userlist.iterator();
                    while (it.hasNext()) {user=it.next();}
                    briftext.setText(user.getBrief());
                    netname.setText(user.getNetname());
                    user.setPhotonumber(user.getPhotonumber());
                    Glide.with(getContext()).load(user.getPhotolocal()).into(facephoto);
                }catch (Exception e ){
                    e.printStackTrace();
                }

                break;
        }}
}
