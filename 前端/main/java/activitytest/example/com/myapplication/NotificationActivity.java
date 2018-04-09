package activitytest.example.com.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener{
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        back=(Button)findViewById(R.id.back_button);
        back.setOnClickListener(this);
      getFragmentManager().beginTransaction()
                          .replace(R.id.preframlayout,new notificationFragment())
                          .commit();
    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
        }
    }
    public  static class notificationFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
        }
    }
}
