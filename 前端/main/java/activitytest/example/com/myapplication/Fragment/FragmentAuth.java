package activitytest.example.com.myapplication.Fragment;

/**
 * Created by lawrence on 2017/3/7.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import activitytest.example.com.myapplication.MyApplication;
import activitytest.example.com.myapplication.R;
import activitytest.example.com.myapplication.adapter.MessageAdapter;
import activitytest.example.com.myapplication.entity.Message;
import activitytest.example.com.myapplication.searchActivity;

public class FragmentAuth extends Fragment implements View.OnClickListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        Button botton = (Button)getActivity().findViewById(R.id.search_botton);
        Button botton2 = (Button)getActivity().findViewById(R.id.button2);
        Button botton3 = (Button)getActivity().findViewById(R.id.button3);
        Button botton4 = (Button)getActivity().findViewById(R.id.button4);
        Button botton5 = (Button)getActivity().findViewById(R.id.button5);
        Button botton6 = (Button)getActivity().findViewById(R.id.button6);
        botton.setOnClickListener(this);
        botton2.setOnClickListener(this);
        botton3.setOnClickListener(this);
        botton4.setOnClickListener(this);
        botton5.setOnClickListener(this);
        botton6.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_botton:
                Intent intent=new Intent(getActivity(),searchActivity.class);
                intent.putExtra("热搜","x");
                getActivity().startActivity(intent);
                break;
            case R.id.button2:
                Intent intent2=new Intent(getActivity(),searchActivity.class);
                intent2.putExtra("热搜","修家电");
                getActivity().startActivity(intent2);
                break;
            case R.id.button3:
                Intent intent3=new Intent(getActivity(),searchActivity.class);
                intent3.putExtra("热搜","办理手续");
                getActivity().startActivity(intent3);
                break;
            case R.id.button4:
                Intent intent4=new Intent(getActivity(),searchActivity.class);
                intent4.putExtra("热搜","Logo设计");
                getActivity().startActivity(intent4);
                break;
            case R.id.button5:
                Intent intent5=new Intent(getActivity(),searchActivity.class);
                intent5.putExtra("热搜","课程补习");
                getActivity().startActivity(intent5);
                break;
            case R.id.button6:
                Intent intent6=new Intent(getActivity(),searchActivity.class);
                intent6.putExtra("热搜","代做作业");
                getActivity().startActivity(intent6);
                break;
        }
    }
}
