package activitytest.example.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lawrence on 2017/3/10.
 */

public class MessageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jump_view);
        Intent intent =new Intent(this,MainActivity.class);
        intent.putExtra("jump",1);
        startActivity(intent);
    }
}
