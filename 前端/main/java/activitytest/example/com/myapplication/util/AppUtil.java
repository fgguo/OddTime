package activitytest.example.com.myapplication.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.C;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;

/**
 * Created by lawrence on 2017/4/22.
 */

public class AppUtil extends Activity{
    /* md5 加密 */
    static public String md5 (String str) {
        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (algorithm != null) {
            algorithm.reset();
            algorithm.update(str.getBytes());
            byte[] bytes = algorithm.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : bytes) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();
        }
        return "";

    }
    static public BaseMessage getMessage (String jsonStr) throws Exception {
        BaseMessage message = new BaseMessage();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr);
            if (jsonObject != null) {
                Log.d("code",jsonObject.getString("code"));
                Log.d("mssage",jsonObject.getString("message"));
                Log.d("result",jsonObject.getString("result"));
                message.setCode(jsonObject.getString("code"));
                message.setMessage(jsonObject.getString("message"));
                message.setResult(jsonObject.getString("result"));
            }
        } catch (JSONException e) {
//            throw new Exception(C.err.jsonFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    /* 判断int是否为空 */
    static public boolean isEmptyInt (int v) {
        Integer t = new Integer(v);
        return t == null ? true : false;
    }
    static public String ucfirst (String str) {
        if (str != null && str != "") {
            str  = str.substring(0,1).toUpperCase()+str.substring(1);
        }
        return str;
    }
    public static long getUsedMemory () {
        long total = Runtime.getRuntime().totalMemory();
        long free = Runtime.getRuntime().freeMemory();
        return total - free;
    }
    public static Bitmap createBitmap(String path, int w, int h) {
        try {
            File f = new File(path);
            if (f.exists() == false)
                return null;
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            // 这里是整个方法的关键，inJustDecodeBounds设为true时将不为图片分配内存


            BitmapFactory.decodeFile(path, opts);
            int srcWidth = opts.outWidth;// 获取图片的原始宽度
            int srcHeight = opts.outHeight;// 获取图片原始高度
            int destWidth = 0;
            int destHeight = 0;
            // 缩放的比例
            double ratio = 0.0;
            if (srcWidth < w || srcHeight < h) {
                ratio = 0.0;
                destWidth = srcWidth;
                destHeight = srcHeight;
            } else if (srcWidth > srcHeight) {// 按比例计算缩放后的图片大小
                ratio = (double) srcWidth / w;
                destWidth = w;
                destHeight = (int) (srcHeight / ratio);
            } else {
                ratio = (double) srcHeight / h;
                destHeight = h;
                destWidth = (int) (srcWidth / ratio);
            }
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放
            newOpts.inSampleSize = (int) ratio + 1;
            // inJustDecodeBounds设为false表示把图片读进内存中
            newOpts.inJustDecodeBounds = false;
            // 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
            newOpts.outHeight = destHeight;
            newOpts.outWidth = destWidth;
            // 获取缩放后图片
            return    BitmapFactory.decodeFile(path, opts);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
}
