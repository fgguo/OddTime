package activitytest.example.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import activitytest.example.com.myapplication.R;

public class VersionActivity extends AppCompatActivity implements View.OnClickListener {
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        back=(Button)findViewById(R.id.back_button);
        back.setOnClickListener(this);
        Toast.makeText(VersionActivity.this,"暂无新版本",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
        }
    }
    //@Override
//public void onTaskComplete(int taskId, BaseMessage message) {
//    super.onTaskComplete(taskId, message);
//    switch (taskId) {
//        case C.task.searchResult:
//           try{resultData= (List<Bean>)message.getResultList("Bean");}
//           catch (java.lang.Exception e){
//               e.printStackTrace();
//           }
//            break;
//}}

//    HashMap<String, String> urlParams = new HashMap<String, String>();
//            urlParams.put("search",text);
//            doTaskAsync(C.task.searchResult,C.api.getSearchResult,urlParams);

//          HttpUtil.sendOkHttpRequest(C.api.getskillname,new okhttp3.Callback(){
//                @Override
//                public void onResponse(Call call, Response response)throws IOException{
//                    //得到服务器返回的具体内容
//                try{    JSONArray k=new JSONArray(response);
//                    for (int i=0;i<k.length();i++){
//                        JSONObject skillObject=k.getJSONObject(i);
//                        autoCompleteData.add(skillObject.getString("skillname"));
//                    }
//                }
//                catch (JSONException e){
//                    e.printStackTrace();
//                }
//                }
//                @Override
//                public void onFailure(Call call,IOException e){
//                    //在这里对异常情况进行处理
//                    toast("未即受到服务器传回数据");
//                }
//            });
}
