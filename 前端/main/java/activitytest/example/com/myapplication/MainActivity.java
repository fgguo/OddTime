package activitytest.example.com.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Iterator;

import activitytest.example.com.myapplication.Fragment.FragmentAt;
import activitytest.example.com.myapplication.Fragment.FragmentAuth;
import activitytest.example.com.myapplication.Fragment.FragmentMore;
import activitytest.example.com.myapplication.Fragment.FragmentSpace;
import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseUi;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.Offer;
import activitytest.example.com.myapplication.entity.User;
import activitytest.example.com.myapplication.polling.PollingService;
import activitytest.example.com.myapplication.polling.PollingUtils;

public class MainActivity extends BaseUi
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    private FragmentAt fragmentAt;
    private FragmentAuth fragmentAuth;
    private FragmentSpace fragmentSpace;
    private FragmentMore fragmentMore;
    // ���岼�ֶ���
    private FrameLayout atFl, authFl, spaceFl, moreFl;

    // ����ͼƬ�������
    private ImageView atIv, authIv, spaceIv, moreIv;
    public String username;

    // ���尴ťͼƬ���
//    private ImageView toggleImageView, plusImageView;

    // ����PopupWindow
//    private PopupWindow popWindow;
    // ��ȡ�ֻ���Ļ�ֱ��ʵ���
    private DisplayMetrics dm;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("基础");
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//        点击滑动菜单图标变幻
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Intent intent=getIntent();

        initView();

        initData();
//        SharedPreferences pref=getSharedPreferences("data",MODE_APPEND);
//        String token=pref.getString("token","");
//        User user=User.getInstance();
//        user.setToken(token);
        int jump=intent.getIntExtra("jump",0);
        if (jump==1){
            clickMoreBtn();
        }else{
        // ��ʼ��Ĭ��Ϊѡ�е���ˡ���̬����ť
        clickAtBtn();}
        PollingUtils.startPollingService(this, 1, PollingService.class, PollingService.ACTION);

    }


  @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

       if (id ==R.id.only_lingshi){
            Intent fabu =new Intent(MainActivity.this,FabuActivity.class);
            startActivity(fabu);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            Intent log3=new Intent(MainActivity.this,histroymessionActivity.class);
            startActivity(log3);
        } else if (id == R.id.nav_manage) {
            Intent log2=new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(log2);
        }else if (id == R.id.nav_send) {
            Intent log=new Intent(MainActivity.this,HelpActivity.class);
            startActivity(log);
        }else if(id==R.id.nav_header_main){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void initView() {
        // ʵ�������ֶ���
        atFl = (FrameLayout) findViewById(R.id.layout_at);
        authFl = (FrameLayout) findViewById(R.id.layout_auth);
        spaceFl = (FrameLayout) findViewById(R.id.layout_space);
        moreFl = (FrameLayout) findViewById(R.id.layout_more);

        // ʵ����ͼƬ�������
        atIv = (ImageView) findViewById(R.id.image_at);
        authIv = (ImageView) findViewById(R.id.image_auth);
        spaceIv = (ImageView) findViewById(R.id.image_space);
        moreIv = (ImageView) findViewById(R.id.image_more);

        // ʵ������ťͼƬ���
//        toggleImageView = (ImageView) findViewById(R.id.toggle_btn);
//        plusImageView = (ImageView) findViewById(R.id.plus_btn);

    }

    /**
     * ��ʼ������
     */
    private void initData() {
        // �����ֶ������ü���
        atFl.setOnClickListener(this);
        authFl.setOnClickListener(this);
        spaceFl.setOnClickListener(this);
        moreFl.setOnClickListener(this);

        // ����ťͼƬ���ü���
//        toggleImageView.setOnClickListener(this);
    }

    /**
     * ����¼�
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // �����̬��ť
            case R.id.layout_at:
                clickAtBtn();
                break;
            // ���������ذ�ť
            case R.id.layout_auth:
                clickAuthBtn();
                break;
            // ����ҵĿռ䰴ť
            case R.id.layout_space:
                clickSpaceBtn();
                break;
            // ������ఴť
            case R.id.layout_more:
                clickMoreBtn();
                break;
            // ����м䰴ť
//            case R.id.toggle_btn:
//                clickToggleBtn();
//                break;
//            case R.id.imageView1:
//                Intent fabu=new Intent(MainActivity.this,FabuActivity.class);
//                startActivity(fabu);
//                popWindow.dismiss();
//                break;
        }
    }

    /**
     * ����ˡ���̬����ť
     */
    private void clickAtBtn() {
//        doTaskAsync(C.task.getbasictask, C.api.getbasictask);
        // ʵ����Fragmentҳ��
        fragmentAt = new FragmentAt();
        // �õ�Fragment���������
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // �滻��ǰ��ҳ��
        fragmentTransaction.replace(R.id.frame_content, fragmentAt);
        // ��������ύ
        fragmentTransaction.commit();
        // �ı�ѡ��״̬
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("基础");
        atFl.setSelected(true);
        atIv.setSelected(true);

        authFl.setSelected(false);
        authIv.setSelected(false);

        spaceFl.setSelected(false);
        spaceIv.setSelected(false);

        moreFl.setSelected(false);
        moreIv.setSelected(false);
    }

    /**
     * ����ˡ�������ء���ť
     */
    private void clickAuthBtn() {
        // ʵ����Fragmentҳ��
        fragmentAuth = new FragmentAuth();
        // �õ�Fragment���������
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // �滻��ǰ��ҳ��
        fragmentTransaction.replace(R.id.frame_content, fragmentAuth);
        // ��������ύ
        fragmentTransaction.commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("专业");
        atFl.setSelected(false);
        atIv.setSelected(false);

        authFl.setSelected(true);
        authIv.setSelected(true);

        spaceFl.setSelected(false);
        spaceIv.setSelected(false);

        moreFl.setSelected(false);
        moreIv.setSelected(false);
    }

    /**
     * ����ˡ��ҵĿռ䡱��ť
     */
    private void clickSpaceBtn() {
        // ʵ����Fragmentҳ��
        fragmentSpace = new FragmentSpace();
        // �õ�Fragment���������
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // �滻��ǰ��ҳ��
        fragmentTransaction.replace(R.id.frame_content, fragmentSpace);
        // ��������ύ
        fragmentTransaction.commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我");
        atFl.setSelected(false);
        atIv.setSelected(false);

        authFl.setSelected(false);
        authIv.setSelected(false);

        spaceFl.setSelected(true);
        spaceIv.setSelected(true);

        moreFl.setSelected(false);
        moreIv.setSelected(false);
    }

    /**
     * ����ˡ����ࡱ��ť
     */
    private void clickMoreBtn() {
        // ʵ����Fragmentҳ��
        fragmentMore = new FragmentMore();
        // �õ�Fragment���������
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // �滻��ǰ��ҳ��
        fragmentTransaction.replace(R.id.frame_content, fragmentMore);
        // ��������ύ
        fragmentTransaction.commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("消息");
        atFl.setSelected(false);
        atIv.setSelected(false);

        authFl.setSelected(false);
        authIv.setSelected(false);

        spaceFl.setSelected(false);
        spaceIv.setSelected(false);

        moreFl.setSelected(true);
        moreIv.setSelected(true);
    }
//    @Override
//    public void onTaskComplete(int taskId, BaseMessage message) {
//        super.onTaskComplete(taskId, message);
//        ArrayList<Offer> rewardlist=new ArrayList<Offer>();
//        switch (taskId) {
//            case C.task.getbasictask:
//                try
//                {
//                    rewardlist=(ArrayList<Offer>)message.getResultList ("Offer");
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Iterator it=rewardlist.iterator();
//                while(it.hasNext()){
//                    Log.d("id",it.next().toString());
//                }
//                // login logic
//                try {
//                    String message1 = message.getMessage();
//                    // login success
//                    if (message1.equals("succeed")) {
//                        Toast.makeText(MainActivity.this,"刷新成功"+" ✔️ ",Toast.LENGTH_LONG).show();
//                        // login fail
//                    } else {
//                        Toast.makeText(MainActivity.this,"刷新失败"+" ×️ ",Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    toast(e.getMessage());
//                }
//                break;
//        }
//    }
@Override
protected void onDestroy() {
    super.onDestroy();
    //Stop polling service
    System.out.println("Stop polling service...");
    PollingUtils.stopPollingService(this, PollingService.class, PollingService.ACTION);
}

}
