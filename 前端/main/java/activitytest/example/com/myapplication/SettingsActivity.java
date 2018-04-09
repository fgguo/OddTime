package activitytest.example.com.myapplication;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;

import activitytest.example.com.myapplication.base.BaseAuth;
import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseUi;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.Offer;
import activitytest.example.com.myapplication.entity.User;
import okhttp3.FormBody;

public class SettingsActivity extends BaseUi implements View.OnClickListener{

    LinearLayout xiaoxitongzhi;
    LinearLayout neicunqingli;
    LinearLayout banbengengxin;
    LinearLayout logout;
    TextView crashtext;
    Button back;
    String token;
    User user;
    ProgressDialog progressDialog;
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {

                case 0:

                    Toast.makeText(SettingsActivity.this,"清理完成", Toast.LENGTH_SHORT).show();

                    try {

                        crashtext.setText(CacheDataManager.getTotalCacheSize(SettingsActivity.this));

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

            }

        };

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.setting_activity);

        xiaoxitongzhi=(LinearLayout)findViewById(R.id.xiaoxitongzhi);
        neicunqingli=(LinearLayout)findViewById(R.id.neicunqingli);
        banbengengxin=(LinearLayout)findViewById(R.id.banbengengxin);
        logout=(LinearLayout)findViewById(R.id.logout);
        crashtext=(TextView)findViewById(R.id.crashtext) ;
        back=(Button)findViewById(R.id.back_button);
        progressDialog=new ProgressDialog(SettingsActivity.this);
        progressDialog.setTitle("正在清理中");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        back.setOnClickListener(this);

        xiaoxitongzhi.setOnClickListener(this);
        neicunqingli.setOnClickListener(this);
        banbengengxin.setOnClickListener(this);
        logout.setOnClickListener(this);
        user=User.getInstance();
        token=user.getToken();

        try {

            crashtext.setText(CacheDataManager.getTotalCacheSize(this));

        } catch (Exception e) {

            e.printStackTrace();

        }

        }
    @Override
    public void onClick(View v){
        switch (v.getId()) {

            case R.id.xiaoxitongzhi:
                Intent intent1=new Intent(SettingsActivity.this,NotificationActivity.class);
                startActivity(intent1);
                break;
            case R.id.neicunqingli:
                progressDialog.show();
                new Thread(new clearCache()).start();
                break;
            case R.id.banbengengxin:
                Intent intent2=new Intent(SettingsActivity.this,VersionActivity.class);
                startActivity(intent2);
                break;
            case R.id.back_button:
                Intent intent3=new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.logout:
                try {
                    HashMap<String, String> urlParams = new HashMap<String, String>();
                    urlParams.put("tokenkey", token);
                    doTaskAsync(1030, C.api.logout, urlParams);
                    user.setToken("");
                }catch(Exception e) {
                 e.printStackTrace();
            }
                break;
        }
    }
    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case 1030:
                String isSuccessful=message.getCode();
                if (isSuccessful.equals("200")){
                    BaseAuth.setLogin(false);
                    Intent intent4=new Intent(SettingsActivity.this,LoginActivity.class);
                    intent4.putExtra("sym","no");
                    startActivity(intent4);
                }
                break;
        }}
    class clearCache implements Runnable {

        @Override

        public void run() {

            try {

                    CacheDataManager.clearAllCache(SettingsActivity.this);

                Thread.sleep(2000);

                if (CacheDataManager.getTotalCacheSize(SettingsActivity.this).startsWith("0")) {

                    handler.sendEmptyMessage(0);

                }
                DataSupport.deleteAll(Offer.class);
            } catch (Exception e) {

                return;

            }
            runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       progressDialog.dismiss();
                    }
                });
        }


    }

    }
