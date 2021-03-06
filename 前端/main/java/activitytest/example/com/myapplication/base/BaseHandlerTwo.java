package activitytest.example.com.myapplication.base;

/**
 * Created by lawrence on 2017/12/24.
 */



import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import activitytest.example.com.myapplication.util.AppUtil;

public class BaseHandlerTwo extends Handler {

    public BaseUiTwo ui;

    public BaseHandlerTwo (BaseUiTwo ui) {
        this.ui = ui;
    }

    public BaseHandlerTwo (Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
        try {
            int taskId;
            String result;
            switch (msg.what) {
                case BaseTask.TASK_COMPLETE:
//                    ui.hideLoadBar();隐藏加载圈
                    taskId = msg.getData().getInt("task");
                    result = msg.getData().getString("data");
                    if (result != null) {
                        ui.onTaskComplete(taskId, AppUtil.getMessage(result));
                    } else if (!AppUtil.isEmptyInt(taskId)) {
                        ui.onTaskComplete(taskId);
                    } else {
                        ui.toast("消息错误");
                    }
                    break;
                case BaseTask.NETWORK_ERROR:
//                    ui.hideLoadBar();
                    taskId = msg.getData().getInt("task");
                    ui.onNetworkError(taskId);
                    break;
                case BaseTask.SHOW_LOADBAR:
//                    ui.showLoadBar();
                    break;
                case BaseTask.HIDE_LOADBAR:
//                    ui.hideLoadBar();
                    break;
                case BaseTask.SHOW_TOAST:
//                    ui.hideLoadBar();
                    result = msg.getData().getString("data");
                    ui.toast(result);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ui.toast(e.getMessage());
        }
    }

}