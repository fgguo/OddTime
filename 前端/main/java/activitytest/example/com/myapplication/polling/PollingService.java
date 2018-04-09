package activitytest.example.com.myapplication.polling;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;

import activitytest.example.com.myapplication.FabuActivity;
import activitytest.example.com.myapplication.Fragment.FragmentMore;
import activitytest.example.com.myapplication.MainActivity;
import activitytest.example.com.myapplication.MessageActivity;
import activitytest.example.com.myapplication.R;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.Message;
import activitytest.example.com.myapplication.entity.User;
import activitytest.example.com.myapplication.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Response;

public class PollingService extends Service {

    public static final String ACTION = "com.ryantang.service.PollingService";

    private NotificationManager mManager;
   private Notification notification;
  private String token;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        initNotifiManager();
        User user=User.getInstance();
        token=user.getToken();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        new PollingThread().start();
    }

    //初始化通知栏配置
    private void initNotifiManager() {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    //弹出Notification
    private void showNotification() {
        //Navigator to the new activity when click the notification title
        Intent i = new Intent(this,MessageActivity.class);
        i.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,
             0);
        //android.util.AndroidRuntimeException: Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag.
        //如果报这个异常就是 最后一个参数修改的原因  原参数 Intent.FLAG_ACTIVITY_NEW_TASK

        notification=new NotificationCompat.Builder(this)
                .setContentTitle("New message")
                .setSmallIcon(R.drawable.ic_launcher).setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
//                .setLargeIcon()

        mManager.notify(0, notification);
    }

    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     * @Author Ryan
     * @Create 2013-7-13 上午10:18:34
     */
    int count = 0;
    class PollingThread extends Thread {
        @Override
        public void run() {
            System.out.println("Polling...");
            count ++;
            //当计数能被5整除时弹出通知
            if (count % 2 == 0) {

                try {

                    HttpUtil.sendOkHttpRequest(C.api.checkNotifications, "1","2","tokenkey",token, new okhttp3.Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //得到服务器返回的具体内容
                          String a=response.body().string();
                            if (a.equals("1")){
                                showNotification();
                                System.out.println("New message!");
                            }
                        }

                        @Override
                        public void onFailure(Call call, IOException e) {
                            //在这里对异常情况进行处理

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service:onDestroy");
    }

}
