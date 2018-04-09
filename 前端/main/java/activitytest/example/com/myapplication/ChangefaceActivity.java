package activitytest.example.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;

import activitytest.example.com.myapplication.Fragment.FragmentAt;
import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseUi;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.User;

public class ChangefaceActivity extends BaseUi implements View.OnClickListener{
    HashMap<String, String> para;
    private Button back;
    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeface);
        ImageButton face1=(ImageButton)findViewById(R.id.face1);
        ImageButton face2=(ImageButton)findViewById(R.id.face2);
        ImageButton face3=(ImageButton)findViewById(R.id.face3);
        ImageButton face4=(ImageButton)findViewById(R.id.face4);
        ImageButton face5=(ImageButton)findViewById(R.id.face5);
        ImageButton face6=(ImageButton)findViewById(R.id.face6);
        ImageButton face7=(ImageButton)findViewById(R.id.face7);
        ImageButton face8=(ImageButton)findViewById(R.id.face8);
        ImageButton face9=(ImageButton)findViewById(R.id.face9);
        ImageButton face10=(ImageButton)findViewById(R.id.face10);
        ImageButton face11=(ImageButton)findViewById(R.id.face11);
        ImageButton face12=(ImageButton)findViewById(R.id.face12);
        back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(this);
     face1.setOnClickListener(this);
        face2.setOnClickListener(this);
        face3.setOnClickListener(this);
        face4.setOnClickListener(this);
        face5.setOnClickListener(this);
        face6.setOnClickListener(this);
        face7.setOnClickListener(this);
        face8.setOnClickListener(this);
        face9.setOnClickListener(this);
        face10.setOnClickListener(this);
        face11.setOnClickListener(this);
        face12.setOnClickListener(this);
         para = new HashMap<String, String>();
                User user=User.getInstance();
        String token=user.getToken();
        para.put("tokenkey",token);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.face1:
                para.put("iconId","1");
                doTaskAsync(299, C.api.modifyuser,para);
                Intent intent1= new Intent();
                int a=R.drawable.face1;
                intent1.putExtra("data_image",a);
                setResult(RESULT_OK,intent1);
                finish();
                break;
            case R.id.face2:
                para.put("iconId","2");
                doTaskAsync(299, C.api.modifyuser,para);
                Intent intent2= new Intent();
                int b=R.drawable.face2;
                intent2.putExtra("data_image",b);
                setResult(RESULT_OK,intent2);
                finish();
                break;
            case R.id.face3:
                para.put("iconId","3");
                doTaskAsync(299, C.api.modifyuser,para);
                Intent intent3= new Intent();
                int c=R.drawable.face3;
                intent3.putExtra("data_image",c);
                setResult(RESULT_OK,intent3);
                finish();
                break;
            case R.id.face4:
                para.put("iconId","4");
                doTaskAsync(299, C.api.modifyuser,para);
                Intent intent4= new Intent();
                int d=R.drawable.face4;
                intent4.putExtra("data_image",d);
                setResult(RESULT_OK,intent4);
                finish();
                break;
            case R.id.face5:
                para.put("iconId","5");
                doTaskAsync(299, C.api.modifyuser,para);
                Intent intent5= new Intent();
                int e=R.drawable.face5;
                intent5.putExtra("data_image",e);
                setResult(RESULT_OK,intent5);
                finish();
                break;
            case R.id.face6:
                para.put("iconId","6");
                doTaskAsync(299, C.api.modifyuser,para);
                Intent intent6= new Intent();
                int f=R.drawable.face6;
                intent6.putExtra("data_image",f);
                setResult(RESULT_OK,intent6);
                finish();
                break;
            case R.id.face7:
                para.put("iconId","7");
                doTaskAsync(299, C.api.modifyuser,para);
                Intent intent7= new Intent();
                int g=R.drawable.face7;
                intent7.putExtra("data_image",g);
                setResult(RESULT_OK,intent7);
                finish();
                break;
            case R.id.face8:
                para.put("iconId","8");
                doTaskAsync(299, C.api.modifyuser,para);
                Intent intent8= new Intent();
                int h=R.drawable.face8;
                intent8.putExtra("data_image",h);
                setResult(RESULT_OK,intent8);
                finish();
                break;
            case R.id.face9:
                para.put("iconId","9");
                doTaskAsync(299, C.api.modifyuser,para);
                Intent intent9= new Intent();
                int i=R.drawable.face9;
                intent9.putExtra("data_image",i);
                setResult(RESULT_OK,intent9);
                finish();
                break;
            case R.id.face10:
                para.put("iconId","10");
                doTaskAsync(299, C.api.modifyuser,para);
                Intent intent10= new Intent();
                int j=R.drawable.face10;
                intent10.putExtra("data_image",j);
                setResult(RESULT_OK,intent10);
                finish();
                break;
            case R.id.face11:
                para.put("iconId","11");
                doTaskAsync(299, C.api.modifyuser,para);
                Intent intent11= new Intent();
                int k=R.drawable.face11;
                intent11.putExtra("data_image",k);
                setResult(RESULT_OK,intent11);
                finish();
                break;
            case R.id.face12:
                Intent intent12= new Intent();
                int l=R.drawable.face12;
                intent12.putExtra("data_image",l);
                setResult(RESULT_OK,intent12);
                para.put("iconId","12");
                doTaskAsync(299, C.api.modifyuser,para);
                finish();
                break;
        }}
    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case 299:
                String a=message.getMessage();
                if (!a.equals(null))
                    Log.d("message is ",a);
                if ("200".equals(message.getCode())){
                    Toast.makeText(ChangefaceActivity.this,"修改成功"+" ✔️ ",Toast.LENGTH_LONG).show();
                }
        }
    }
}
