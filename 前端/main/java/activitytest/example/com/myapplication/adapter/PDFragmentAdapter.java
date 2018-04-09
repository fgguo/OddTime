package activitytest.example.com.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import activitytest.example.com.myapplication.Fragment.Fragmenthistory1;
import activitytest.example.com.myapplication.Fragment.Fragmenthistory2;

/**
 * Created by lawrence on 2017/4/16.
 */

public class PDFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    public PDFragmentAdapter(FragmentManager fm,List<Fragment> mfragmentlist){
        super(fm);

        this.fragments=mfragmentlist;
    }
    @Override
    public Fragment getItem(int paramInt){

        return fragments.get(paramInt);
    }
    @Override
    public int getCount(){
        return fragments.size();
    }
//    @Override
//    public CharSequence getPageTitle(int position) {
//        if (mTitles!=null){
//            return mTitles[position];
//        }
//
//        return "";
//    }

}
