package activitytest.example.com.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import activitytest.example.com.myapplication.base.BaseHandler;
import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseTask;
import activitytest.example.com.myapplication.base.BaseUi;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.User;
import activitytest.example.com.myapplication.util.AppCache;

public class OfferActivity extends BaseUi implements View.OnClickListener{
   public static final String OFFER_NAME="offer_name";
    public static final String OFFER_MORE_INFORMATION="offer_more_information";
    public static final String OFFER_PICTURE="offer_picture";
   private ImageView offerImageView;
    FloatingActionButton actionButton;
    private String picture;
    private String offerid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        this.setHandler(new BlogHandler(this));
        Intent intent=getIntent();
        String offerName= intent.getStringExtra(OFFER_NAME);
//        byte [] offerPicture= intent.getByteArrayExtra(OFFER_PICTURE);
//        Bitmap bitmap= BitmapFactory.decodeByteArray(offerPicture, 0, offerPicture.length);
        String morinformation=intent.getStringExtra(OFFER_MORE_INFORMATION);
         picture=intent.getStringExtra(OFFER_PICTURE);
       offerid=intent.getStringExtra("offerid");
        Log.d("over", picture);
        loadImage(picture);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        actionButton=(FloatingActionButton)findViewById(R.id.floatbutton);
        actionButton.setOnClickListener(this);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
         offerImageView=(ImageView)findViewById(R.id.offer_image_view);
        TextView offerContentText=(TextView)findViewById(R.id.offer_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(offerName);

//        String offerContent=generateOfferContent(offerName);
        offerContentText.setText(morinformation);
    }
//    private String generateOfferContent(String offerName){
//        StringBuilder offerContent =new StringBuilder();
//        for (int i=0;i<500;i++){
//            offerContent.append(offerName);
//        }
//        return  offerContent.toString();
//    }
@Override
public void onClick(View v){
    switch (v.getId()) {
        case R.id.floatbutton:
            User user=User.getInstance();
            String token=user.getToken();
            HashMap<String, String> para = new HashMap<String, String>();
            para.put("taskid",offerid);
            para.put("tokenkey",token);
            doTaskAsync(15, C.api.takeTask,para);
            break;}
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return  super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case 15:
                String a=message.getMessage();
                if (!a.equals(null))
                    Log.d("message is ",a);
                if ("200".equals(message.getCode())){
                Toast.makeText(OfferActivity.this,"成功领取任务"+" ✔️ ",Toast.LENGTH_LONG).show();
            }
        }
    }
    private class BlogHandler extends BaseHandler {
        public BlogHandler(BaseUi ui) {
            super(ui);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case BaseTask.LOAD_IMAGE:
                        Log.d("over","1");
                        Bitmap face = AppCache.getImage(picture);
                        if(face==null){
                            Log.d("Success","图片是空的");
                        }
                        else {
                            Log.d("Success","图片存在");
                        }
//                        Glide.with(OfferActivity.this).load(face).into(offerImageView);
                        offerImageView.setImageBitmap(face);
                        Log.d("over","1");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                ui.toast(e.getMessage());
            }
        }

    }
}

