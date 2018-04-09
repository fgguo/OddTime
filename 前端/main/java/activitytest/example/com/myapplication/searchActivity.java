package activitytest.example.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import activitytest.example.com.myapplication.adapter.SearchResultAdapter;
import activitytest.example.com.myapplication.base.BaseMessage;
import activitytest.example.com.myapplication.base.BaseUi;
import activitytest.example.com.myapplication.base.C;
import activitytest.example.com.myapplication.entity.Bean;
import activitytest.example.com.myapplication.entity.User;
import activitytest.example.com.myapplication.util.HttpUtil;
import activitytest.example.com.myapplication.widge.SearchView;
import okhttp3.Call;
import okhttp3.Response;

import static android.view.View.GONE;

/**
 * Created by lawrence on 2017/3/30.
 */

public class searchActivity extends BaseUi implements SearchView.SearchViewListener{
    /**
     * 搜索结果列表view
     */
    private ListView lvResults;

    /**
     * 搜索view
     */
    private SearchView searchView;


    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;

    /**
     * 搜索结果列表adapter
     */
    private SearchResultAdapter resultAdapter;

    private List<Bean> dbData;

    /**
     * 热搜版数据
     */
    private List<String> hintData;

    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;

    /**
     * 搜索结果的数据
     */
    private List<Bean> resultData;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 4;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;


    private EditText editText;
    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    public static void setHintSize(int hintSize) {
        searchActivity.hintSize = hintSize;
    }

   private String token;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        取消bar
        setContentView(R.layout.search_activity);
        initData();
        initViews();
        Intent intent=getIntent();
        User user=User.getInstance();
        token=user.getToken();
        String hotname= intent.getStringExtra("热搜");
        if (!hotname.equals("x")){
            editText.setText(hotname);
            onSearch(hotname);
            searchView.lvTips.setVisibility(GONE);
        }

    }

    /**
     * 初始化视图
     */
    private void initViews() {
        editText=(EditText)findViewById(R.id.search_et_input);
        lvResults = (ListView) findViewById(R.id.main_lv_search_results);
        searchView = (SearchView) findViewById(R.id.main_search_layout);
//        editText.setOnClickListener(searchView);
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setTipsHintAdapter(hintAdapter);
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);

        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(searchActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //从数据库获取数据
        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }

    /**
     * 获取db 数据
     */
    private void getDbData() {
        int size = 100;
        dbData = new ArrayList<>(size);
//        for (int i = 0; i < size; i++) {
//            dbData.add(new Bean(R.drawable.icon, "android开发必备技能" + (i + 1), "Android自定义view——自定义搜索view", i * 20 + 2 + ""));
//        }
    }

    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData() {
        hintData = new ArrayList<>(hintSize);
        hintData.add("修家电");
            hintData.add("办理手续");
        hintData.add("Logo设计");
        hintData.add("课程补习");
        hintData.add("作业辅导");
        hintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hintData);
    }
//    官方提供了多种ListItem的Layout (R.layout)，以下是较为常用的，更多的请查看API DOC的R.layout http://androidappdocs.appspot.com/reference/android/R.layout.html：
//            ◾android.R.layout.simple_list_item_1   一行text
//◾android.R.layout.simple_list_item_2   一行title，一行text
//◾android.R.layout.simple_list_item_single_choice   单选按钮
//◾android.R.layout.simple_list_item_multiple_choice   多选按钮
//◾android.R.layout.simple_list_item_checked    checkbox

    /**
     * 获取自动补全data 和adapter
     */
    public void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
//            for (int i = 0, count = 0; i < dbData.size()
//                    && count < hintSize; i++) {
//                if (dbData.get(i).getTitle().contains(text.trim())) {
//                    autoCompleteData.add(dbData.get(i).getTitle());
//                    count++;
//                }
//            }
            try {
                Log.d("search", text.toString());
                HttpUtil.sendOkHttpRequest(C.api.getskillname, "fragment", text.toString(),"tokenkey",token, new okhttp3.Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //得到服务器返回的具体内容
                        try {
                            JSONObject jsonObiect = new JSONObject(response.body().string());
                            JSONArray k = new JSONArray(jsonObiect.getString("result"));
                            for (int i = 0; i < k.length(); i++) {
                                String j = k.get(i).toString();
                                Log.d("到这", j);
                                autoCompleteData.add(j);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        autoCompleteAdapter.notifyDataSetChanged();
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        //在这里对异常情况进行处理
                        toast("未即受到服务器传回数据");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            //根据从数据库找到提示来判定提示框的大小，这里将Listview置于Linearlayout中通过外部大小来调整listView，这里
            //可能存在逻辑漏洞（listview.setvisible（GONE）时Linear可能还存在，这里需要在实际运行环境时改善）
//            ViewGroup.LayoutParams lp;
//            if (autoCompleteData.size()==0){
//                searchView.lvTips.setVisibility(GONE);
//            }else if (autoCompleteData.size()==1){
//                lp= searchView.linearLayout.getLayoutParams();
//                lp.width=360;
//                lp.height=60;
//                searchView.linearLayout.setLayoutParams(lp);
//            }
//            else if (autoCompleteData.size()==2){
//                lp= searchView.linearLayout.getLayoutParams();
//                lp.width=360;
//                lp.height=120;
//                searchView.linearLayout.setLayoutParams(lp);
//            }else if (autoCompleteData.size()==3){
//                lp= searchView.linearLayout.getLayoutParams();
//                lp.width=360;
//                lp.height=180;
//                searchView.linearLayout.setLayoutParams(lp);
//            }else {
//                lp= searchView.linearLayout.getLayoutParams();
//                lp.width=360;
//                lp.height=240;
//                searchView.linearLayout.setLayoutParams(lp);
//            }
        }
            if (autoCompleteAdapter == null) {
                Log.d("zhe","sdd");
                autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
            } else {
                Log.d("zhe","sdd1");
                autoCompleteAdapter.notifyDataSetChanged();
            }

    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
//            for (int i = 0; i < dbData.size(); i++) {
//                if (dbData.get(i).getTitle().contains(text.trim())) {
//                    resultData.add(dbData.get(i));
//                }
//            }
                HashMap<String, String> urlParams = new HashMap<String, String>();
            urlParams.put("skill",text);
            urlParams.put("tokenkey",token);
            try {
                doTaskAsync(C.task.searchResult, C.api.getSearchResult, urlParams);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        if (resultAdapter == null) {
            resultAdapter = new SearchResultAdapter(this, resultData, R.layout.item_bean_list);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        dbData.clear();
        //更新result数据
        getResultData(text);
        lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
        if (lvResults.getAdapter() == null) {
            //获取搜索数据 设置适配器
            lvResults.setAdapter(resultAdapter);
        } else {
            //更新搜索数据
            resultAdapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "完成搜素", Toast.LENGTH_SHORT).show();
    }
    @Override
public void onTaskComplete(int taskId, BaseMessage message) {
    super.onTaskComplete(taskId, message);
    switch (taskId) {
        case C.task.searchResult:
            String a=message.getMessage();
            if (!a.equals(null))
                Log.d("message is ",a);
           try{resultData= (List<Bean>)message.getResultList("Bean");
               if (resultData==null){
                   Log.d("x","no data");
               }else {
                   Log.d("x","have data");
               }
               resultAdapter = new SearchResultAdapter(this, resultData, R.layout.item_bean_list);
               resultAdapter.notifyDataSetChanged();
               lvResults.setAdapter(resultAdapter);
           }
           catch (java.lang.Exception e){
               e.printStackTrace();
           }
            break;
}}


}

