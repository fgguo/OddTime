package activitytest.example.com.myapplication.base;

/**
 * Created by lawrence on 2017/4/22.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;

import activitytest.example.com.myapplication.Fragment.FragmentAt;
import activitytest.example.com.myapplication.MyApplication;
import activitytest.example.com.myapplication.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Response;


public class BaseTaskPool {

    // task thread pool
    static private ExecutorService taskPool;

    // for HttpUtil.getNetType
    private Context context;

    public BaseTaskPool (BaseUi ui) {
        this.context = ui;
        taskPool = Executors.newCachedThreadPool();
    }
public BaseTaskPool(BaseUiTwo ui){
    this.context = ui.getActivity();
    taskPool = Executors.newCachedThreadPool();
}

    // http post task with params
    public void addTask (int taskId, String taskUrl, HashMap<String, String> taskArgs, BaseTask baseTask, int delayTime) {
        baseTask.setId(taskId);
        try {
            Log.d("这是","添加到任务池");
            taskPool.execute(new TaskThread(context, taskUrl, taskArgs,null, baseTask, delayTime));
        } catch (Exception e) {
            taskPool.shutdown();
        }
    }

    // http post task without params
    public void addTask (int taskId, String taskUrl, BaseTask baseTask, int delayTime) {
        baseTask.setId(taskId);
        try {
            taskPool.execute(new TaskThread(context, taskUrl, null,null, baseTask, delayTime));
        } catch (Exception e) {
            taskPool.shutdown();
        }
    }
    public void addTask (int taskId, String taskUrl, HashMap<String, String> taskArgs, File file, BaseTask baseTask, int delayTime) {
        baseTask.setId(taskId);
        try {
            taskPool.execute(new TaskThread(context, taskUrl, taskArgs, file, baseTask, delayTime));
        } catch (Exception e) {
            taskPool.shutdown();
        }
    }
    // custom task
    public void addTask (int taskId, BaseTask baseTask, int delayTime) {
        baseTask.setId(taskId);
        Log.d("程序可以运行到addtask","  ");
        try {
            taskPool.execute(new TaskThread(context, null, null,null, baseTask, delayTime));
        } catch (Exception e) {
            taskPool.shutdown();
        }
    }

    // task thread logic
    private class TaskThread implements Runnable {
        private Context context;
        private String taskUrl;
        private HashMap<String, String> taskArgs;
        private File file;
        private BaseTask baseTask;
        private int delayTime = 0;
        public TaskThread(Context context, String taskUrl, HashMap<String, String> taskArgs,File file, BaseTask baseTask, int delayTime) {
            this.context = context;
            this.taskUrl = taskUrl;
            this.taskArgs = taskArgs;
            this.baseTask = baseTask;
            this.delayTime = delayTime;
            this.file  =  file;
        }
        @Override
        public void run() {
            try {
                Log.d("程序可以运行到run()","x");
                baseTask.onStart();
                String httpResult = null;
                // set delay time
                if (this.delayTime > 0) {
                    Thread.sleep(this.delayTime);
                }
                try {
                    // remote task
                    if (this.taskUrl != null) {

                        // http get
                        if (taskArgs == null) {
                            HttpUtil.get(C.api.base+taskUrl,new okhttp3.Callback(){
                                @Override
                                public void onResponse(Call call, Response response)throws IOException{
                                    String responseData=response.body().string();
                                    baseTask.onComplete(responseData);
                                }
                                @Override
                                public void onFailure(Call call,IOException e){
                                    Log.d("网络请求异常","(无参数)");
//                                    Toast.makeText(MyApplication.getContext(),"网络请求失败"+" ×️ ",Toast.LENGTH_SHORT).show(); 报错,应该是在分线程更新UI的缘故
                                }
                            });
                        } else {
                            if (file != null) {
                                String a=file.getPath();
                                Log.d("s",a);
                                HttpUtil.post(C.api.base+taskUrl,taskArgs,file,new okhttp3.Callback(){
                                    @Override
                                    public void onResponse(Call call, Response response)throws IOException{
                                        String responseData=response.body().string();
                                        baseTask.onComplete(responseData);
                                    }
                                    @Override
                                    public void onFailure(Call call,IOException e){

                                    }
                                });
                            } else {
                                HttpUtil.post(C.api.base+taskUrl,taskArgs,new okhttp3.Callback(){
                                    @Override
                                    public void onResponse(Call call, Response response)throws IOException{
                                        String responseData=response.body().string();
                                        baseTask.onComplete(responseData);
                                    }
                                    @Override
                                    public void onFailure(Call call,IOException e){
                                        Log.d("网络请求异常","(有参数)");
                                    }
                                });
                            }
                        }
                    }
else{baseTask.onComplete();}
                } catch (Exception e) {
                    e.printStackTrace(); // debug
                    baseTask.onError(e.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    baseTask.onStop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}