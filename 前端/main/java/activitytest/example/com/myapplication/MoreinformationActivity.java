package activitytest.example.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseUi;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.User;

public class MoreinformationActivity extends BaseUi implements View.OnClickListener {
    EditText ed1;
    EditText ed2;
    EditText ed3;
    EditText ed4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinformation);
         ed1 = (EditText) findViewById(R.id.ed1);
         ed2 = (EditText) findViewById(R.id.ed2);
         ed3 = (EditText) findViewById(R.id.ed3);
         ed4 = (EditText) findViewById(R.id.ed4);
        Button back = (Button) findViewById(R.id.back_button);
        Button queding = (Button) findViewById(R.id.fabubotton);
        back.setOnClickListener(this);
        queding.setOnClickListener(this);
        Intent intent=getIntent();
        String location=intent.getStringExtra("location");
        ed2.setText(location);
        String sex=intent.getStringExtra("sex");

        ed1.setText(sex);
        String birthday=intent.getStringExtra("birthday");
        ed3.setText(birthday);
        String work=intent.getStringExtra("work");
        ed4.setText(work);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.fabubotton:
                String location=ed2.getText().toString();
                String sex=ed1.getText().toString();
                if(!(sex.equals("男")||sex.equals("女"))){
                    Toast.makeText(MoreinformationActivity.this,"修改失败，性别必须为男或女"+" ✔️ ",Toast.LENGTH_LONG).show();
                    finish();
                }
                        String birthday=ed3.getText().toString();
                    String work=ed4.getText().toString();
                HashMap<String, String> para = new HashMap<String, String>();
                User user=User.getInstance();
                String token=user.getToken();
                para.put("tokenkey",token);
                para.put("sex",sex);
                para.put("address",location);
                para.put("job",work);
                para.put("birthday",birthday);
                doTaskAsync(13, C.api.modifyuser,para);
                Intent intent= new Intent();
                intent.putExtra("data_return1",sex);
                intent.putExtra("data_return2",location);
                intent.putExtra("data_return3",work);
                intent.putExtra("data_return4",birthday);
                setResult(RESULT_OK,intent);
                finish();
        }
    }
    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case 13:
                String a=message.getMessage();
                if (!a.equals(null))
                    Log.d("message is ",a);
                if ("200".equals(message.getCode())){
                Toast.makeText(MoreinformationActivity.this,"修改成功"+" ✔️ ",Toast.LENGTH_LONG).show();
            }
        }
    }
}
