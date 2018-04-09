package activitytest.example.com.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseUi;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.Offer;
import activitytest.example.com.myapplication.entity.User;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by lawrence on 2017/4/11.
 */

public class FabuActivity extends BaseUi implements View.OnClickListener {
    private Button home;
    private Button fabu;
    private EditText e1;
    private EditText e2;
    private EditText e3;
    private EditText e4;
    private CardView cardView;
    private ImageView photo;
    private Uri imageUri;
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO = 2;
    public CircleImageView cancelimage;
    private Bitmap image;
    String imagePath = null;
    String token;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabu);
        User user=User.getInstance();
        token=user.getToken();
        init();
    }

    public void init() {
        cardView=(CardView)findViewById(R.id.camera_icon);
        photo=(ImageView)findViewById(R.id.america_imageView);
        home = (Button) findViewById(R.id.home_button);
        fabu = (Button) findViewById(R.id.fabubotton);
        cancelimage=(CircleImageView)findViewById(R.id.cancel_image);
        cancelimage.setVisibility(View.INVISIBLE);
        e1=(EditText)findViewById(R.id.edit1);
        e2=(EditText)findViewById(R.id.edit2);
        e3=(EditText)findViewById(R.id.edit3);
        e4=(EditText)findViewById(R.id.edit4);
        home.setOnClickListener(this);
        fabu.setOnClickListener(this);
        cardView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_button:
                finish();
                break;
            case R.id.fabubotton:
                String s1=e1.getText().toString();
                String s2=e2.getText().toString();
                String s3=e3.getText().toString();
                String s4=e4.getText().toString();
                if(imagePath!=null){
                    File file = new File(imagePath);
                    doPosttask(s1,s2,s3,s4,file);
                } else {
//                    doPosttask(s1,s2,s3,s4);
                    toast("您应选择一张图片");
                }
                break;
            case R.id.camera_icon:

//                AlertDialog.Builder builder = new AlertDialog.Builder(FabuActivity.this);
//                final String[] cities = { "拍摄", "从相册选择"};
//                //    设置一个下拉的列表选择项
//                builder.setItems(cities, new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        switch (which) {
//                            case 0:
//                                File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
//                                try {
//                                    if (outputImage.exists()){
//                                        outputImage.delete();
//                                    }
//                                    outputImage.createNewFile();
//                                }catch (IOException e){
//                                    e.printStackTrace();
//                                }
//                                if(Build.VERSION.SDK_INT>=24){
//                                    imageUri= FileProvider.getUriForFile(FabuActivity.this,"com.example.cameraalbumtext.fileprovider",outputImage);
//                                }else {
//                                    imageUri= Uri.fromFile(outputImage);
//                                }
//                                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
//                                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//                                startActivityForResult(intent,TAKE_PHOTO);
//
//                                break;
//                            case 1:
                                if (ContextCompat.checkSelfPermission(FabuActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(FabuActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                                } else {
                                    openAlbum();
                                }
//                                break;
//                        }
//                    }});
//                builder.show();
                break;
        }

    }
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
//            case TAKE_PHOTO:if(resultCode==RESULT_OK){
//                try{
//                    Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                    photo.setImageBitmap(bitmap);
//                    image=bitmap;
//                    cancelimage();
//                }catch (FileNotFoundException e){
//                    e.printStackTrace();
//                }
//            }
//                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            photo.setImageBitmap(bitmap);
            image=bitmap;
            cancelimage();
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
    private void cancelimage(){
        cancelimage.setVisibility(View.VISIBLE);
        cancelimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo.setImageBitmap(null);
                cancelimage.setVisibility(View.GONE);
            }
        });
    }
//    private void doPosttask(String s1,String s2,String s3,String s4) {
//        HashMap<String, String> urlParams = new HashMap<String, String>();
//        if(TextUtils.isEmpty(s1)) {
//            Toast.makeText(this,"请输入物品名称",Toast.LENGTH_SHORT).show();
//        }
//        if(TextUtils.isEmpty(s2)) {
//            Toast.makeText(this,"请输入物品金额",Toast.LENGTH_SHORT).show();
//        }
//        if(TextUtils.isEmpty(s4)) {
//            Toast.makeText(this,"请输入详细信息",Toast.LENGTH_SHORT).show();
//        }
//        else{
//            urlParams.put("name", s1);
//            urlParams.put("money", s2);
//            urlParams.put("time", s3);
//            urlParams.put("detail", s4);
//            urlParams.put("tokenkey",token);
//            try {
//                doTaskAsync(C.task.posttask, C.api.posttask, urlParams);
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//            finish();
//        }
//    }
    private void doPosttask(String s1,String s2,String s3,String s4, File file) {
        HashMap<String, String> urlParams = new HashMap<String, String>();
        if(TextUtils.isEmpty(s1)) {
            Toast.makeText(this,"请输入物品名称",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(s2)) {
            Toast.makeText(this,"请输入物品金额",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(s4)) {
            Toast.makeText(this,"请输入详细信息",Toast.LENGTH_SHORT).show();
        }
        else{
            urlParams.put("name", s1);
            urlParams.put("money", s2);
            urlParams.put("time", s3);
            urlParams.put("detail", s4);
            urlParams.put("tokenkey",token);
            if(file==null) {
                try {
                    doTaskAsync(C.task.posttask, C.api.posttask, urlParams);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    doTaskAsync(C.task.posttask, C.api.posttask, urlParams,file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        finish();
    }
    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case C.task.posttask:
                String a=message.getMessage();
                if (!a.equals(null))
                    Log.d("message is ",a);
                // login logic
                try {
                    String message1 = message.getMessage();
                    // login success
                    if (message1.equals("发布成功")) {
                        Toast.makeText(FabuActivity.this,"发布成功"+" ✔️ ",Toast.LENGTH_LONG).show();
                        // login fail
                    } else {
                        Toast.makeText(FabuActivity.this,"发布失败"+" ×️ ",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    toast(e.getMessage());
                }
                break;
        }
    }
}