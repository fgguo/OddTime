package activitytest.example.com.myapplication.base;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import activitytest.example.com.myapplication.entity.Offer;
import activitytest.example.com.myapplication.base.BaseModel;
import activitytest.example.com.myapplication.util.AppUtil;

/**
 * Created by lawrence on 2017/4/22.
 */

public class BaseMessage {
    private String code;
    private String message;
    private String resultSrc;
    private Map<String, BaseModel> resultMap;
    private Map<String, ArrayList<? extends BaseModel>> resultList;
    private ArrayList resultArrayList=new ArrayList();
    public BaseMessage () {
        this.resultMap = new HashMap<String, BaseModel>();
        this.resultList = new HashMap<String, ArrayList<? extends BaseModel>>();
    }

    @Override
    public String toString () {
        return code + " | " + message + " | " + resultSrc;
    }

    public String getCode () {
        return this.code;
    }

    public void setCode (String code) {
        this.code = code;
    }

    public String getMessage () {
        return this.message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public String getResult () {
        return this.resultSrc;
    }

    public Object getResult (String modelName) throws Exception {
        Object model = this.resultMap.get(modelName);
        // catch null exception
        if (model == null) {
            throw new Exception("Message data is empty");
        }
        return model;
    }

    public ArrayList<? extends BaseModel> getResultList (String modelName) throws Exception {
        ArrayList<? extends BaseModel> modelList = this.resultList.get(modelName);
        // catch null exception
        if (modelList == null || modelList.size() == 0) {
            throw new Exception("Message data list is empty");
        }
        return modelList;
    }

    @SuppressWarnings("unchecked")
    public void setResult (String result) throws Exception {
        this.resultSrc = result;
        if (result.length() > 0) {
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(result);
            Iterator<String> it = jsonObject.keys();
            while (it.hasNext()) {
                // initialize
                String jsonKey = it.next();
                String modelName = getModelName(jsonKey);
                String modelClassName = "activitytest.example.com.myapplication.entity." + modelName;
                //取到model名字
                JSONArray modelJsonArray = jsonObject.optJSONArray(jsonKey);
                // JSONObject
                if (modelJsonArray == null) {
                    JSONObject modelJsonObject = jsonObject.optJSONObject(jsonKey);
                    if (modelJsonObject == null) {
                        throw new Exception("Message result is invalid");
                    }
                    this.resultMap.put(modelName, json2model(modelClassName, modelJsonObject));
                    // JSONArray
                } else {
                    ArrayList<BaseModel> modelList = new ArrayList<BaseModel>();
                    for (int i = 0; i < modelJsonArray.length(); i++) {
//                        Log.d("model","模型"+modelJsonArray.length()+"遍历第"+i+"次");
                        JSONObject modelJsonObject = modelJsonArray.optJSONObject(i);
//                        Log.d("model",modelJsonObject.toString());
                        modelList.add(json2model(modelClassName, modelJsonObject));
                    }
                    this.resultList.put(modelName, modelList);
                }
            }
        }
    }
    @SuppressWarnings("unchecked")
    private BaseModel json2model (String modelClassName, JSONObject modelJsonObject) throws Exception  {
        // auto-load model class
        Log.d("model",modelClassName);
        BaseModel modelObj = (BaseModel) Class.forName(modelClassName).newInstance();
        Class<? extends BaseModel> modelClass = modelObj.getClass();
        // auto-setting model fields
        Iterator<String> it = modelJsonObject.keys();
        while (it.hasNext()) {
            String varField = it.next();
//            Log.d("model",varField);
            String varValue = modelJsonObject.getString(varField);
//            Log.d("model",varValue);
            Field field = modelClass.getDeclaredField(varField);
            field.setAccessible(true); // have private to be accessable
            field.set(modelObj, varValue);
        }
        return modelObj;
    }

    private String getModelName (String str) {
        String[] strArr = str.split("\\W");
        if (strArr.length > 0) {
            str = strArr[0];
        }
        return AppUtil.ucfirst(str);
    }
}
