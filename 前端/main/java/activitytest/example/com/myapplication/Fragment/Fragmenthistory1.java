package activitytest.example.com.myapplication.Fragment;

/**
 * Created by lawrence on 2017/4/16.
 */



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import activitytest.example.com.myapplication.MyApplication;
import activitytest.example.com.myapplication.R;
import activitytest.example.com.myapplication.adapter.MessageAdapter;
import activitytest.example.com.myapplication.adapter.OfferAdapter;
import activitytest.example.com.myapplication.base.BaseHandlerTwo;
import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseTask;
import activitytest.example.com.myapplication.base.BaseUiTwo;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.Message;
import activitytest.example.com.myapplication.entity.Offer;
import activitytest.example.com.myapplication.entity.User;
import activitytest.example.com.myapplication.util.AppCache;

/**
 * Created by lawrence on 2017/4/16.
 */
public class Fragmenthistory1 extends LazyLoadFragment {
    private List<Message> messageList = new ArrayList<>();
    private ArrayList<Offer> Offerlist =new ArrayList<>();
    private OfferAdapter adapter;
    private RecyclerView recyclerView;
    private String photourl;
    private LinkedList<String> photolist=new LinkedList<String>();
    private Bitmap photo;
    private int count;
    private int count1=0;
    @Override
    protected void lazyLoad() {
        this.setHandler(new BlogHandler(this));
        count1=0;
        initOffers();
try {


    recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
    GridLayoutManager layoutManager = new GridLayoutManager(MyApplication.getContext(), 1);
//                StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);
    adapter = new OfferAdapter(Offerlist);
    recyclerView.setAdapter(adapter);
}catch (NullPointerException e){
    e.printStackTrace();
}

    }


    private void initOffers(){
        try{
            HashMap<String, String> urlParams = new HashMap<String, String>();
            User user=User.getInstance();
            String a=user.getToken();
            urlParams.put("tokenkey",a);
            doTaskAsync(998, C.api.getPublishTasks,urlParams);
        }

        catch (Exception e){
            e.printStackTrace();
        }}
    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case 998:
                String a=message.getMessage();
                if (!a.equals(null))
                    Log.d("message is ",a);
                try
                {
                    Offerlist =(ArrayList<Offer>)message.getResultList ("Offer");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                count= Offerlist.size();
                Iterator<Offer> it = Offerlist.iterator();
                while (it.hasNext()) {
                    Offer k= it.next();
                    photourl= k.getPicture();
                    Log.d("Success",photourl);
//                    loadImage(photourl);
                    photolist.add(photourl);
                    loadImage(photourl);
//                    k.setPicturee(photo);
                    Log.d("Success","图片设置方法执行");
                    if(photo==null){
                        Log.d("Success","图片是空的");
                    }
                }
                Log.d("Success","1");
                recyclerView=(RecyclerView)getActivity().findViewById(R.id.recycler_view);
                GridLayoutManager layoutManager=new GridLayoutManager(MyApplication.getContext(),1);
//                StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                adapter =new OfferAdapter(Offerlist);
                recyclerView.setAdapter(adapter);
                // login logic
                try {
                    String message1 = message.getMessage();
                    // login success
                    if (message1.equals("获取成功")) {
                        Toast.makeText(getActivity(),"刷新成功"+" ✔️ ",Toast.LENGTH_LONG).show();
                        // login fail
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    toast(e.getMessage());
                }
                break;
        }
    }

    private class BlogHandler extends BaseHandlerTwo {
        public BlogHandler(BaseUiTwo ui) {
            super(ui);
        }
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case BaseTask.LOAD_IMAGE:
                        Log.d("Success","2");
                        photo = AppCache.getImage(photolist.getFirst());
                        photolist.remove();
                        if (count1+1<=count){
                            Offerlist.get(count1).save();
                            Offerlist.get(count1).setPicturee(photo);
                        }
                        Log.d("Success","开始测试图片");
                        if(Offerlist.get(count1).getPicturee()==null){
                            Log.d("Success","图片回调是空的");
                        }
                        count1++;
                        recyclerView=(RecyclerView)getActivity().findViewById(R.id.recycler_view);
                        GridLayoutManager layoutManager=new GridLayoutManager(MyApplication.getContext(),1);
//                        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter =new OfferAdapter(Offerlist);
                        Log.d("Success","3");
                        recyclerView.setAdapter(adapter);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                ui.toast(e.getMessage());
            }
        }
    }
    @Override
    protected int setContentView() {
        return R.layout.fragment_at;
    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
        Log.d(TAG, "Fragment1" + "已经对用户不可见，可以停止加载数据");
    }
}

