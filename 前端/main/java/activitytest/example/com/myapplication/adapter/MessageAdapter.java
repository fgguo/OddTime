package activitytest.example.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import activitytest.example.com.myapplication.R;
import activitytest.example.com.myapplication.entity.Message;

import static activitytest.example.com.myapplication.MyApplication.getContext;

/**
 * Created by lawrence on 2017/3/9.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    private int resourceId;
    public MessageAdapter(Context context, int textViewResourceId, List<Message> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position,View convertView, ViewGroup parent){
        Message message=getItem(position);
        String a=message.getmType();
       message.setmType(a);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView Messagespecie=(ImageView)view.findViewById(R.id.message_photo);
        TextView Messagetittle=(TextView)view.findViewById(R.id.message_title);
//        LinearLayout Messagelayout=(LinearLayout)view.findViewById(R.id.message_layout);
        Glide.with(getContext()).load(message.getPhoto()).into(Messagespecie);
        Messagetittle.setText(message.getSpecific());
//        Messagelayout.setBackgroundResource(message.getColor());
//        Messagelayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        return view;
    }
}
