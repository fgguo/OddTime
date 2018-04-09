package activitytest.example.com.myapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import activitytest.example.com.myapplication.Fragment.Fragmenthistory1;
import activitytest.example.com.myapplication.Fragment.Fragmenthistory2;
import activitytest.example.com.myapplication.adapter.PDFragmentAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

public class histroymessionActivity extends AppCompatActivity implements View.OnClickListener{
 private TextView tvtittlecontent;
    private TextView tvtittlecontent2;
    TabLayout tittles;
    ViewPager vpdetails;
    CircleImageView backbutton;
    private Fragmenthistory1 fragment1;
    private Fragmenthistory2 fragment2;
    protected List<Fragment> mFragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histroymession);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backbutton=(CircleImageView)findViewById(R.id.back_button);
        backbutton.setOnClickListener(this);
//      toolbar.setNavigationIcon(R.drawable.back_icon);
         tittles=(TabLayout)findViewById(R.id.tittle);
         vpdetails=(ViewPager)findViewById(R.id.vpdetails);
        fragment1=new Fragmenthistory1();
        fragment2=new Fragmenthistory2();
        mFragmentList.add(fragment1);
        mFragmentList.add(fragment2);
        tittles.addTab(tittles.newTab().setCustomView(R.layout.history_tab1));
        tvtittlecontent=(TextView)findViewById(R.id.tabcontent1);
        tvtittlecontent.setText("历史悬赏令");
        tvtittlecontent.setTextColor(getResources().getColor(R.color.blue));
        tvtittlecontent.setTextSize(15);
        tittles.addTab(tittles.newTab().setCustomView(R.layout.history_tab2));
        tvtittlecontent2=(TextView)findViewById(R.id.tabcontent2);
        tvtittlecontent2.setText("我的历史服务");
        tvtittlecontent2.setTextColor(getResources().getColor(R.color.base_color_text_black));
        tvtittlecontent2.setTextSize(15);
        tittles.setTabGravity(TabLayout.GRAVITY_CENTER);
        tittles.setTabMode(TabLayout.MODE_FIXED);
        initViewpager();
        initToolbars();
    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;}}
private void initViewpager(){
//
//    tittles.setupWithViewPager(vpdetails);
    vpdetails.setAdapter(new PDFragmentAdapter(getSupportFragmentManager(),mFragmentList));
//    vpdetails.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                tittles.getTabAt(position).select();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
   vpdetails.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tittles));
}
private void initToolbars(){


    tittles.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
vpdetails.setCurrentItem(tab.getPosition());
switch (tab.getPosition()){
    case 0:  tvtittlecontent.setTextColor(getResources().getColor(R.color.blue));
        tvtittlecontent.setTextSize(16);
        break;
    case 1:tvtittlecontent2.setTextColor(getResources().getColor(R.color.blue));
        tvtittlecontent2.setTextSize(16);
        default:break;
}
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            tvtittlecontent.setTextColor(getResources().getColor(R.color.base_color_text_black));
            tvtittlecontent.setTextSize(14);
            tvtittlecontent2.setTextColor(getResources().getColor(R.color.base_color_text_black));
            tvtittlecontent2.setTextSize(14);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });
}
}
