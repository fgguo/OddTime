package activitytest.example.com.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import org.litepal.LitePal;

/**
 * Created by lawrence on 2017/3/8.
 */

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate(){
        super.onCreate();
        context=getApplicationContext();
        LitePal.initialize(context);
    }
    public static Context getContext(){
        return context;
    }
}
