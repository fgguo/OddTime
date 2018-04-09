package activitytest.example.com.myapplication.Fragment;

/**
 * Created by lawrence on 2017/3/7.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import activitytest.example.com.myapplication.MyApplication;
import activitytest.example.com.myapplication.base.BaseHandlerTwo;
import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import activitytest.example.com.myapplication.adapter.OfferAdapter;
import activitytest.example.com.myapplication.base.BaseUiTwo;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.Offer;
import activitytest.example.com.myapplication.R;
import activitytest.example.com.myapplication.entity.User;
import activitytest.example.com.myapplication.util.AppCache;

public class FragmentAt extends BaseUiTwo {
    private ArrayList<Offer> Offerlist =new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    private OfferAdapter adapter;
    private RecyclerView recyclerView;
    private String photourl;
    private LinkedList<String> photolist=new LinkedList<String>();
    private  Bitmap photo;
    private int count;
    private int count1=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     return    inflater.inflate(R.layout.fragment_at, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        this.setHandler(new BlogHandler(this));
        initOffers();
        try {
            if (count == 0) {
                List<Offer> offers = DataSupport.findAll(Offer.class);
                for (Offer offer : offers) {
                    if (offer.getHeadshot()==null){
                        Log.d("没存进去","s");
                    }
                    byte[]images= offer.getHeadshot();
                    Bitmap bitmap= BitmapFactory.decodeByteArray(images,0,images.length);
                    offer.setPicturee(bitmap);
                    Offerlist.add(offer);
                    Log.d("a", "b");
                }
                recyclerView=(RecyclerView)getActivity().findViewById(R.id.recycler_view);
                GridLayoutManager layoutManager=new GridLayoutManager(MyApplication.getContext(),2);
//                StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new OfferAdapter(Offerlist);
                recyclerView.setAdapter(adapter);
            }
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
    private byte[]img(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        }catch (NullPointerException e){
        }
        return baos.toByteArray();}
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
                            count1=0;
                        initOffers();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
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

    private void initOffers(){
        try{
            HashMap<String, String> urlParams = new HashMap<String, String>();
            User user=User.getInstance();
            String a=user.getToken();
            Log.d("Fragmentat token is",a);
            urlParams.put("tokenkey",a);
        doTaskAsync(C.task.getbasictask, C.api.getbasictask,urlParams);
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case C.task.getbasictask:
                String a=message.getMessage();
                if (!a.equals(null))
                    Log.d("message is ",a);
                try
                {
                    Offerlist =(ArrayList<Offer>)message.getResultList ("Offer");
                    Log.d("Success","success");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                count= Offerlist.size();
                if (count>0){
                    DataSupport.deleteAll(Offer.class);
                    Log.d("表已删除","s");
                }
                Iterator<Offer> it = Offerlist.iterator();
                while (it.hasNext()) {
                  Offer k= it.next();
                    k.save();
                    photourl= k.getPicture();
                    Log.d("Success",photourl);
//                    loadImage(photourl);
                    photolist.add(photourl);
                    loadImage(photourl);
//                    k.setPicturee(photo);
                    Log.d("Success","图片设置方法执行");
                    if(photolist==null){
                        Log.d("Success","图片是空的");
                    }
                }
                Log.d("Success","1");
                recyclerView=(RecyclerView)getActivity().findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(MyApplication.getContext(),2);
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
                    } else {
                        Toast.makeText(getActivity(),"刷新失败"+" ×️ ",Toast.LENGTH_LONG).show();
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
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case BaseTask.LOAD_IMAGE:
                        Log.d("Success","2");
                       photo = AppCache.getImage(photolist.getFirst());
                        Log.d("Success","3");
                        //后面的东西貌似不执行
                        byte[]images=img(photo);
                        if (images==null){
                            Toast.makeText(getActivity(),"转化出错 ",Toast.LENGTH_LONG).show();
                            Log.d("zhuanhuachucuo","s");
                        }else {
                            photolist.remove();
                        }
                        if (count1+1<=count){
                            Offerlist.get(count1).save();
                            Offerlist.get(count1).setPicturee(photo);
                            Offerlist.get(count1).setHeadshot(images);
                            //升级Litepal需要将版本号+1  !!!!
                            Log.d("zhekuaicunle1","s");
                            Offerlist.get(count1).save();
                            Log.d("zhekuaicunle2","s");
                        }
                        Log.d("Success","开始测试图片");
                        if(Offerlist.get(count1).getPicturee()==null){
                            Log.d("Success","图片回调是空的");
                        }
                        count1++;
                        recyclerView=(RecyclerView)getActivity().findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(MyApplication.getContext(),2);
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

}