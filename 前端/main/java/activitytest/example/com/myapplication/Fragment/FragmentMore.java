package activitytest.example.com.myapplication.Fragment;

/**
 * Created by lawrence on 2017/3/7.
 */
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import activitytest.example.com.myapplication.BriefActivity;
import activitytest.example.com.myapplication.MyApplication;
import activitytest.example.com.myapplication.R;
import activitytest.example.com.myapplication.adapter.MessageAdapter;
import activitytest.example.com.myapplication.adapter.PDFragmentAdapter;
import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseUiTwo;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.Message;
import activitytest.example.com.myapplication.entity.Offer;
import activitytest.example.com.myapplication.entity.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentMore extends BaseUiTwo {
    private ArrayList<Message> messageList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        User user=User.getInstance();
        String username=user.getToken();
        HashMap<String, String> para = new HashMap<String, String>();
        para.put("tokenkey",username);
        try {
            doTaskAsync(16, C.api.getNotifications,para);
        }catch (Exception e){
            e.printStackTrace();
        }
        swipeRefresh=(SwipeRefreshLayout)getView().findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshOffers();
            }
        });
    }
    private void refreshOffers(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            try {
                                User user=User.getInstance();
                                String username=user.getToken();
                                HashMap<String, String> para = new HashMap<String, String>();
                                para.put("tokenkey",username);
                                doTaskAsync(16, C.api.getNotifications,para);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getActivity(),"刷新失败"+" ×️ ",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        finally {
                            swipeRefresh.setRefreshing(false);
                        }
                    }
                });
            }
        }).start();
    }
    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case 16:
                String a=message.getMessage();
                if (!a.equals(null))
                    Log.d("message is ",a);
                try{
                    messageList =(ArrayList<Message>)message.getResultList("Message");
                }catch (Exception e){
                    e.printStackTrace();
                }
                MessageAdapter adapter = new MessageAdapter(MyApplication.getContext(), R.layout.message_item, messageList);
                ListView listView = (ListView)getActivity().findViewById(R.id.list_blacklist);
                listView.setAdapter(adapter);
        }
    }

}
