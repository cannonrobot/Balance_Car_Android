package com.lb.cannonrobot;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

public class FragmentTab1 extends Fragment {

    private RockerView Rocker;
    static public Switch remoteSwitch;
    static public Switch turnmodeSwitch;
    static public byte RockerValue[]={1,0,0,0,0,1};
    static public boolean isRemoteSwitch;
    static public boolean isTurnmodeSwitch;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1,
                container, false);

        WindowManager wm1 = getActivity().getWindowManager();
        int width = wm1.getDefaultDisplay().getWidth();
        Rocker=  (RockerView)view.findViewById(R.id.rockerView1);
        remoteSwitch=(Switch)view.findViewById(R.id.switch_Remote);
        turnmodeSwitch=(Switch)view.findViewById(R.id.switch_Turnmode);
        turnmodeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   isTurnmodeSwitch=true;
                }
                else {
                  isTurnmodeSwitch=false;
                }
            }
        });

        remoteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isRemoteSwitch=true;
                    MainActivity.handler.postDelayed(MainActivity.runnable, 110);//开启周期性的发送遥控数据
                }
                else {
                    isRemoteSwitch=false;
                    MainActivity.handler.removeCallbacks(MainActivity.runnable);//关闭遥控数据发送
                }
            }
        });

        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) Rocker.getLayoutParams();
        linearParams.width = width;
        linearParams.height = width;

        Rocker.setLayoutParams(linearParams);

        Rocker.setRockerChangeListener(new RockerView.RockerChangeListener() {
            @Override
            public void report(float x, float y) {

                RockerValue[0] = (byte) (y / Rocker.getR() * 100);
                RockerValue[1] = (byte) (x / Rocker.getR() * 100);
                // System.out.println(RockerValue[0]);

            }
        });
        return view;
    }

}

