package activitytest.example.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import activitytest.example.com.myapplication.UI.SesameCreditPanel;
import activitytest.example.com.myapplication.UI.SesameItemModel;
import activitytest.example.com.myapplication.UI.SesameModel;

public class CraditActivity extends AppCompatActivity implements View.OnClickListener {
    private SimpleDateFormat formater = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
    ImageView back;
    int cradit;
//    LinearLayout craditup;
//    LinearLayout mytittle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cradit);
        back=(ImageView) findViewById(R.id.cancel_image);
        //craditup=(LinearLayout)findViewById(R.id.craditupp);
        //mytittle=(LinearLayout)findViewById(R.id.mytitle);
        //空指针报错，只注释上两行，导致craditup，mytittle为空。
//        craditup.setOnClickListener(this);
//        mytittle.setOnClickListener(this);
        back.setOnClickListener(this);
        Intent intent=getIntent();
        String craditget=intent.getStringExtra("cradit");
        cradit=Integer.parseInt(craditget);
       ((SesameCreditPanel) findViewById(R.id.panel)).setDataModel(getData(cradit));
    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.cancel_image:
                finish();
                break;
           /* case R.id.craditupp:
                Intent intent1=new Intent(CraditActivity.this,CraditupActivity.class);
                startActivity(intent1);
                break;
            case R.id.mytitle:
                Intent intent2=new Intent(CraditActivity.this,MytittleActivity.class);
                startActivity(intent2);
                break;*/
        }
    }
    private SesameModel getData(int a) {
        SesameModel model = new SesameModel();
        model.setUserTotal(a);
//        if(a){}
        model.setAssess("信用良好");
        model.setTotalMin(350);
        model.setTotalMax(950);
        model.setFirstText("BETA");
        model.setTopText("因为信用 所以简单");
        model.setFourText("评估时间:" + formater.format(new Date()));
        ArrayList<SesameItemModel> sesameItemModels = new ArrayList<SesameItemModel>();

        SesameItemModel ItemModel350 = new SesameItemModel();
        ItemModel350.setArea("一般");
        ItemModel350.setMin(350);
        ItemModel350.setMax(590);
        sesameItemModels.add(ItemModel350);

        SesameItemModel ItemModel550 = new SesameItemModel();
        ItemModel550.setArea("中等");
        ItemModel550.setMin(550);
        ItemModel550.setMax(600);
        sesameItemModels.add(ItemModel550);

        SesameItemModel ItemModel600 = new SesameItemModel();
        ItemModel600.setArea("良好");
        ItemModel600.setMin(550);
        ItemModel600.setMax(650);
        sesameItemModels.add(ItemModel600);

        SesameItemModel ItemModel650 = new SesameItemModel();
        ItemModel650.setArea("优秀");
        ItemModel650.setMin(650);
        ItemModel650.setMax(700);
        sesameItemModels.add(ItemModel650);

        SesameItemModel ItemModel700 = new SesameItemModel();
        ItemModel700.setArea("极好");
        ItemModel700.setMin(700);
        ItemModel700.setMax(950);
        sesameItemModels.add(ItemModel700);

        model.setSesameItemModels(sesameItemModels);
        return model;
    }
}
