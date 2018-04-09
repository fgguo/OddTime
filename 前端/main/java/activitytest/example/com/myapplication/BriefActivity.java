package activitytest.example.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseUi;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.User;

public class BriefActivity extends BaseUi implements View.OnClickListener{
    private Button back;
    private Button fabu;
    private TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brief);
        back = (Button) findViewById(R.id.back_button);
        fabu = (Button) findViewById(R.id.fabubotton);
        text =(TextView) findViewById(R.id.textbrif);
        back.setOnClickListener(this);
        fabu.setOnClickListener(this);
        Intent intent=getIntent();
        String brief=intent.getStringExtra("brief");
        text.setText(brief);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.fabubotton:
                HashMap<String, String> para = new HashMap<String, String>();
                User user=User.getInstance();
                String token=user.getToken();
                para.put("tokenkey",token);
                para.put("profile",text.getText().toString());
                doTaskAsync(10,C.api.modifyuser,para);
                Intent intent= new Intent();
                intent.putExtra("data_return",text.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
        }
    }
    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case 10:
                String a=message.getMessage();
                if (!a.equals(null))
                    Log.d("message is ",a);
                if ("200".equals(message.getCode())){
                Toast.makeText(BriefActivity.this,"修改成功"+" ✔️ ",Toast.LENGTH_LONG).show();
            }
        }
    }
}


