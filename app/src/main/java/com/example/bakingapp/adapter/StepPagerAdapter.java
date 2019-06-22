package com.example.bakingapp.adapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bakingapp.model.step;
import com.example.bakingapp.videoPlayer;

import java.util.ArrayList;


public class StepPagerAdapter extends FragmentPagerAdapter {

    ArrayList<step> mStepList;
    Bundle stepsBundle = new Bundle();

    public StepPagerAdapter(FragmentManager fm,  ArrayList<step> stepList) {
        super(fm);
        this.mStepList = stepList;
    }

    @Override
    public Fragment getItem(int position) {
        videoPlayer videoPlayerFragment = new videoPlayer();
        stepsBundle.putParcelableArrayList("steps", mStepList);
        stepsBundle.putInt("page",position+1);
        stepsBundle.putBoolean("isLastPage",position == getCount() - 1);
        videoPlayerFragment.setArguments(stepsBundle);

        return videoPlayerFragment;
    }

    @Override
    public int getCount() {
        return mStepList.size();
    }
}