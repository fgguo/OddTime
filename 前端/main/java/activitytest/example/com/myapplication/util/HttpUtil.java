package activitytest.example.com.myapplication.util;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import activitytest.example.com.myapplication.entity.User;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by lawrence on 2017/4/22.
 */
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

public class HttpUtil {
    static public int WAP_INT = 1;
    static public int NET_INT = 2;
    static public int WIFI_INT = 3;
    static public int NONET_INT = 4;

    static private Uri APN_URI = null;

    static public int getNetType (Context ctx) {
        // has network
        ConnectivityManager conn = null;
        try {
            conn = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (conn == null) {
            return HttpUtil.NONET_INT;
        }
        NetworkInfo info = conn.getActiveNetworkInfo();
        boolean available = info.isAvailable();
        if (!available){
            return HttpUtil.NONET_INT;
        }
        // check use wifi
        String type = info.getTypeName();
        if (type.equals("WIFI")) {
            return HttpUtil.WIFI_INT;
        }
        // check use wap
        APN_URI = Uri.parse("content://telephony/carriers/preferapn");
        Cursor uriCursor = ctx.getContentResolver().query(APN_URI, null, null, null, null);
        if (uriCursor != null && uriCursor.moveToFirst()) {
            String proxy = uriCursor.getString(uriCursor.getColumnIndex("proxy"));
            String port = uriCursor.getString(uriCursor.getColumnIndex("port"));
            String apn = uriCursor.getString(uriCursor.getColumnIndex("apn"));
            if (proxy != null && port != null && apn != null && apn.equals("cmwap") && port.equals("80") &&
                    (proxy.equals("10.0.0.172") || proxy.equals("010.000.000.172"))) {
                return HttpUtil.WAP_INT;
            }
        }
        return HttpUtil.NET_INT;
    }

    public static void get(final String address, final Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Log.d("api",address);
        Request request = new Request.Builder()
                .url(address)
                .build();
         client.newCall(request).enqueue(callback);
    }
    public static void post(final String address,final HashMap urlParams,final Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {

                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder abc=new FormBody.Builder();
                    Iterator iter = urlParams.entrySet().iterator();
                    while (iter.hasNext()) {
                       Map.Entry entry = (Map.Entry) iter.next();
                        String key = entry.getKey().toString();
                     String val = entry.getValue().toString();
                        abc.add(key,val);}
                    RequestBody requestBody=abc.build();
                    Request request=new Request.Builder().url(address).post(requestBody).build();
                    client.newCall(request).enqueue(callback);


        }}).start();
    }
    public static void post(final String address, final HashMap urlParams, final File file,final Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String[] values = new String[10];
                    int i = 0;
                    Iterator it = urlParams.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        values[i] = entry.getValue().toString();
                        Log.d("" + i, values[i]);
                        i++;
                    }
                           String a = file.getPath();
                           String b = file.getName();
                           String imageType = "multipart/form-data";
                           OkHttpClient client = new OkHttpClient();
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    // 这里演示添加用户ID
//        builder.addFormDataPart("userId", "20160519142605");
                    builder.setType(MultipartBody.FORM).addFormDataPart("photo",b,
                            RequestBody.create(MediaType.parse("image/jpeg"), new File(a)))
                            .addFormDataPart("name", urlParams.get("name").toString())
                            .addFormDataPart("money", urlParams.get("money").toString())
                            .addFormDataPart("time", urlParams.get("time").toString())
                            .addFormDataPart("detail", urlParams.get("detail").toString())
                            .addFormDataPart("tokenkey",urlParams.get("tokenkey").toString());

                    RequestBody requestBody = builder.build();
                    Request.Builder reqBuilder = new Request.Builder();
                    Request request = reqBuilder
                            .url(address)
                            .post(requestBody)
                            .build();
                          /*  RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                            Log.d(a, b);
                            Log.d(urlParams.get("name").toString(),urlParams.get("paying").toString());
                            Log.d(urlParams.get("time").toString(),urlParams.get("description").toString());
                            RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("picture", b, fileBody)
                            .addFormDataPart("name", urlParams.get("name").toString())
                            .addFormDataPart("paying", urlParams.get("paying").toString())
                            .addFormDataPart("time", urlParams.get("time").toString())
                            .addFormDataPart("description", urlParams.get("description").toString())
                            .build();
                    Request request = new Request.Builder()
                            .url(address)
                            .post(requestBody).build();
                            */
                    client.newCall(request).enqueue(callback);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void sendOkHttpRequest(String address,String key,String value,String a,String b,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
            RequestBody requestBody=new FormBody.Builder().add(key,value).add(a,b).build();
        Request request=new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }
}
