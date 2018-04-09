
package activitytest.example.com.myapplication;
import activitytest.example.com.myapplication.base.BaseAuth;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseUi;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.User;

/**
 * Created by lawrence on 2017/3/4.
 */

public class RegisterActivity  extends BaseUi {
    EditText username, password,netname;
    Button register;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rigister);
        username = (EditText) this.findViewById(R.id.et_username);
        password = (EditText) this.findViewById(R.id.et_password);
        netname =  (EditText)this.findViewById(R.id.et_netname);

        View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_register:
                        Log.d("点击","注册");
                        doTaskRegister();
                        break;
                    default:
                        Log.d("根本","没点击？？");
                }
            }
        };
        findViewById(R.id.btn_register).setOnClickListener(mOnClickListener);
    }
    private void doTaskRegister() {
        if (username.length() > 0 && password.length() > 0) {
            HashMap<String, String> urlParams = new HashMap<String, String>();
            urlParams.put("account", username.getText().toString());
            urlParams.put("password", password.getText().toString());
            urlParams.put("netname",netname.getText().toString());
            Log.d("写入map","已经写入");
            try {
                this.doTaskAsync(C.task.register, C.api.register, urlParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Log.d("？？","没进来？？？");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // async task callback methods

    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case C.task.register:
                User user = null;
                // login logic
                try {
                    String issuccessful=message.getCode();
                    String a=message.getMessage();
                    if (!a.equals(null))
                    Log.d("message is ",a);
                    // login success
                    if (issuccessful.equals("200")) {
                        //   BaseAuth.setUaer(user);
                        toast("注册成功！");
                        BaseAuth.setRegister(true);
                        BaseAuth.setLogin(true);
                        User usr=User.getInstance();
                        usr.setToken(a);
                        Log.d("token is ",usr.getToken());
                        // login fail
                    } else {
                        // BaseAuth.setUaer(user); // set sid
                        BaseAuth.setRegister(false);
                        toast(issuccessful);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    toast(e.getMessage());
                }
                // login complete
                // turn to main
                if (BaseAuth.isRegister()) {
                    // start service
                    //   BaseService.start(this, NoticeService.class);
                    // turn to index
                    forward(LoginActivity.class);
                }
                break;
        }
    }

    @Override
    public void onNetworkError (int taskId) {
        super.onNetworkError(taskId);
    }
}

