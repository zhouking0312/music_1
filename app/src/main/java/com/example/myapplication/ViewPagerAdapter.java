package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

//import com.example.myapplication.Bean.MusicData_Activity;
import com.example.myapplication.day2.BlankFragment1;
import com.example.myapplication.day2.BlankFragment2;
import com.example.myapplication.day2.BlankFragment3;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
//                MusicData_Activity musicData = musicList.get(position);musicData
                return new BlankFragment1();
            case 1:
                return new BlankFragment2();
            default:
                return new BlankFragment2();
        }
    }
}

