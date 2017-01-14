package com.example.marcin.clientservertutorial.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.marcin.clientservertutorial.MainActivity;
import com.example.marcin.clientservertutorial.R;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Marcin on 2016-12-06.
 */
public class Fragment2 extends Fragment {
    public Fragment2(){
    }

    TextView textViewPWM_FB;
    private static SeekBar seekBarPWMfrontback;
    private static SeekBar seekBarPWMrightleft;
    int PWM  = 1;
    byte[] buffer = new byte[1024];
    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        View rootView = inflater.inflate(R.layout.fragment2, container, false);
        textViewPWM_FB = (TextView) rootView.findViewById(R.id.response);
        seekBarPWMfrontback = (SeekBar) rootView.findViewById(R.id.seekBar);
        seekBarPWMrightleft = (SeekBar) rootView.findViewById(R.id.seekBar2);
//        seekbar.setText("PWM (front-back): 0");
        SetSeekBarPWM();
        return rootView;

    }


    public void SetSeekBarPWM()
    {
        textViewPWM_FB.setText("PWM (front-back): 0");
        textViewPWM_FB.setText("PWM (front-back): 0" + " / " + (seekBarPWMrightleft.getMax()/2));
        seekBarPWMfrontback.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    int progress_value = 0;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        textViewPWM_FB.setText("PWM (front-back):" + (progress_value-10) + " / " + (seekBarPWMrightleft.getMax()/2));
                        //                        Toast.makeText(MainActivity.this, "SeekBar in progress", Toast.LENGTH_LONG).show();
                        PWM = progress_value;
                        buffer[0] = (byte)PWM;
                         //tu bedzie wysylanie na server pwm

                            activity.send(buffer);
                            //socket.close(); // na koniec appki wypierdolic

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //                        Toast.makeText(MainActivity.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textViewPWM_FB.setText("PWM (front-back):" + (progress_value-10) + " / " + (seekBarPWMrightleft.getMax()/2));
                        //                        Toast.makeText(MainActivity.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}