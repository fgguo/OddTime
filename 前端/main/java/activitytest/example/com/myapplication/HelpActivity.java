package activitytest.example.com.myapplication;

import android.graphics.Bitmap;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import activitytest.example.com.myapplication.Fragment.FragmentAt;
import activitytest.example.com.myapplication.base.BaseHandler;
import activitytest.example.com.myapplication.base.BaseHandlerTwo;
import activitytest.example.com.myapplication.base.BaseTask;
import activitytest.example.com.myapplication.base.BaseUi;
import activitytest.example.com.myapplication.base.BaseUiTwo;
import activitytest.example.com.myapplication.util.AppCache;

public class HelpActivity extends BaseUi implements View.OnClickListener {
    public Button button;
    public ImageView imageView;
    private String photourl;
    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHandler(new BlogHandler(this));
        setContentView(R.layout.activity_help);
        button = (Button) findViewById(R.id.hellophoto);
        imageView = (ImageView) findViewById(R.id.photoo);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hellophoto:
                photourl = " http://192.168.43.226:8080/HelloSpringMVC/assets/kkk.jpg";
                Log.d("help success",photourl);
                loadImage(photourl);
                break;
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
                        Bitmap face = AppCache.getImage(photourl);
                        Log.d("Success","开始检测图片");
                        if(face==null){
                            Log.d("Success","图片是空的");
                        }
                        else {
                            Log.d("Success","图片存在");
                        }
                        imageView.setImageBitmap(face);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                ui.toast(e.getMessage());
            }
        }

    }
}
