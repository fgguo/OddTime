package activitytest.example.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseUi;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.User;


public class myskillActivity extends BaseUi implements View.OnClickListener{
private Button back;
private Button fabu;
        EditText ed1;
        EditText ed2;
        EditText ed3;
        EditText ed4;
@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myskill);
        back = (Button) findViewById(R.id.back_button);
        fabu = (Button) findViewById(R.id.fabubotton);
        ed1=(EditText)findViewById(R.id.ed1);
        ed2=(EditText)findViewById(R.id.ed2);
        ed3=(EditText)findViewById(R.id.ed3);
        ed4=(EditText)findViewById(R.id.ed4);
        back.setOnClickListener(this);
        fabu.setOnClickListener(this);
        Intent intent=getIntent();
        String skill1=intent.getStringExtra("skill1");
        ed2.setText(skill1);
        String skill2=intent.getStringExtra("skill2");
        ed1.setText(skill2);
        String skill3=intent.getStringExtra("skill3");
        ed3.setText(skill3);
        String skill4=intent.getStringExtra("skill4");
        ed4.setText(skill4);
        }
@Override
public void onClick(View v) {
        switch (v.getId()) {
        case R.id.back_button:
        finish();
        break;
        case R.id.fabubotton:
         String skill1=ed1.getText().toString();
                String skill2=ed2.getText().toString();
                String skill3=ed3.getText().toString();
                String skill4=ed4.getText().toString();
                String skill=";"+skill1+";"+skill2+";"+skill3+";"+skill4+";";
                HashMap<String, String> para = new HashMap<String, String>();
                User user=User.getInstance();
                String token=user.getToken();
                para.put("tokenkey",token);
                para.put("skills",skill);
                doTaskAsync(12, C.api.modifyuser,para);
                Intent intent= new Intent();
                intent.putExtra("data_return5",skill1);
                intent.putExtra("data_return6",skill2);
                intent.putExtra("data_return7",skill3);
                intent.putExtra("data_return8",skill4);
                setResult(RESULT_OK,intent);
        finish();
        }
        }
        @Override
        public void onTaskComplete(int taskId, BaseMessage message) {
                super.onTaskComplete(taskId, message);
                switch (taskId) {
                        case 12:
                                String a=message.getMessage();
                                if (!a.equals(null))
                                        Log.d("message is ",a);
                                if ("200".equals(message.getCode())){
                                Toast.makeText(myskillActivity.this,"修改成功"+" ✔️ ",Toast.LENGTH_LONG).show();
                        }
                }
        }
        }