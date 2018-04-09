package activitytest.example.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import activitytest.example.com.myapplication.base.BaseAuth;
import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseUi;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.User;

/**
 * Created by lawrence on 2017/3/4.
 */

public class LoginActivity extends BaseUi {


    EditText username, password;
    CheckBox mCheckBox;
    private SharedPreferences settings;
    Button login;
    TextView register;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        // setContentView(R.layout.activity_rigister);
        username = (EditText) this.findViewById(R.id.et_username);
        password = (EditText) this.findViewById(R.id.et_password);
        mCheckBox = (CheckBox) this.findViewById(R.id.remember);
        settings = getPreferences(Context.MODE_PRIVATE);
        if (BaseAuth.isLogin()) {
            Log.d("Login 进行到这儿","1");
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
else {
        Intent intent=getIntent();
        String sym=intent.getStringExtra("sym");
        if (sym==null){
            sym="";
        }

            //		showNotice();

        if (!sym.equals("no")) {
            if (settings.getBoolean("remember", false)) {
                mCheckBox.setChecked(true);
                username.setText(settings.getString("username", ""));
                password.setText(settings.getString("password", ""));
                doTaskLogin();
            }
        }else {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("remember", false);
            editor.putString("username", "");
            editor.putString("password", "");
            editor.apply();
        }
        // remember checkbox
        mCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                if (mCheckBox.isChecked()) {
                    editor.putBoolean("remember", true);
                    editor.putString("username", username.getText().toString());
                    editor.putString("password", password.getText().toString());
                } else {
                    editor.putBoolean("remember", false);
                    editor.putString("username", "");
                    editor.putString("password", "");
                }
                editor.commit();
            }
        });

        // login submit
        View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_login :
                        doTaskLogin();
                        //   break;
                        break;
                    case R.id.btn_register:
                        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        findViewById(R.id.btn_login).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_register).setOnClickListener(mOnClickListener);
    }}
    private void doTaskLogin() {
        if (username.length() > 0 && password.length() > 0) {
            HashMap<String, String> urlParams = new HashMap<String, String>();
            urlParams.put("account", username.getText().toString());
            urlParams.put("password", password.getText().toString());
            try {
                this.doTaskAsync(C.task.login, C.api.login, urlParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            toast("输入不能为空！");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // async task callback methods

    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case C.task.login:
                User user = null;
                String  issuccessful;
                // login logic
                try {
                    issuccessful= message.getCode();
                    // login success
                    if ("200".equals(issuccessful)) {
                        BaseAuth.setLogin(true);
                        User usr=User.getInstance();
                        String token=message.getMessage();
                        usr.setToken(token);
//                        SharedPreferences.Editor editor=getSharedPreferences("data",MODE_APPEND).edit();
//                        editor.putString("token",token);
//                        editor.apply();
                        // toast("登录成功！");
                        // login fail
                    }
                    else {
                        BaseAuth.setLogin(false);
                        toast(issuccessful);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //  toast(e.getMessage());
                }
                // login complete
                if (BaseAuth.isLogin()) {
                    // start service
                    //   BaseService.start(this, NoticeService.class);
                    // turn to index

                    forward(MainActivity.class);
                }
                else{
                    toast("请重新输入");
                }
                break;
        }
    }

    @Override
    public void onNetworkError (int taskId) {
        super.onNetworkError(taskId);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // other methods

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return super.onKeyDown(keyCode, event);
    }


}


