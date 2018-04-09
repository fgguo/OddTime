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


public class LingshiActivity extends BaseUi implements View.OnClickListener{
        private Button back;
        private Button fabu;
        private EditText editText;
@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lingshi);
        back = (Button) findViewById(R.id.back_button);
        fabu = (Button) findViewById(R.id.fabubotton);
        editText=(EditText)findViewById(R.id.textbrif);
        back.setOnClickListener(this);
        fabu.setOnClickListener(this);
        Intent intent=getIntent();
        String lingshi=intent.getStringExtra("lingshi");
        Log.d("lingshi",lingshi);
        editText.setText(lingshi);
        }
@Override
public void onClick(View v) {
        switch (v.getId()) {
        case R.id.back_button:
        finish();
        break;
        case R.id.fabubotton:
                String lingshi=editText.getText().toString();
                HashMap<String, String> para = new HashMap<String, String>();
                User user=User.getInstance();
                String token=user.getToken();
                para.put("tokenkey",token);
                para.put("availableTime",lingshi);
                doTaskAsync(11, C.api.modifyuser,para);
                Intent intent= new Intent();
                intent.putExtra("data_return0",lingshi);
                setResult(RESULT_OK,intent);
        Toast.makeText(LingshiActivity.this,"修改成功"+" ✔️ ",Toast.LENGTH_LONG).show();
        finish();
        }
        }
        @Override
        public void onTaskComplete(int taskId, BaseMessage message) {
                super.onTaskComplete(taskId, message);
                switch (taskId) {
                        case 11:
                                String a=message.getMessage();
                                if (!a.equals(null))
                                        Log.d("message is ",a);
                                if ("200".equals(message.getCode())){
                                Toast.makeText(LingshiActivity.this,"修改成功"+" ✔️ ",Toast.LENGTH_LONG).show();
                        }
                }
        }
        }
