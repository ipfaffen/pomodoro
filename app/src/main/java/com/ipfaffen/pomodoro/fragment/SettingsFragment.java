package com.ipfaffen.pomodoro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ipfaffen.pomodoro.App;
import com.ipfaffen.pomodoro.R;
import com.securepreferences.SecurePreferences;

/**
 * @author Isaias Pfaffenseller
 */
public class SettingsFragment extends FragmentBase {

    private static final int WORK_TIME_MIN = 10;
    private static final int WORK_TIME_MAX = 50;
    private static final int WORK_TIME_STEP = 5;

    private SeekBar workTimeBar;
    private TextView workTimeText;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        workTimeText = (TextView) rootView.findViewById(R.id.work_time_text);
        workTimeBar = (SeekBar) rootView.findViewById(R.id.work_time_bar);
        workTimeBar.setMax((WORK_TIME_MAX - WORK_TIME_MIN) / WORK_TIME_STEP);
        workTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int time = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                time = (WORK_TIME_MIN + (progresValue * WORK_TIME_STEP));
                workTimeText.setText(String.format("%02d:00", time));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saveWorkTime(time);
            }
        });

        prepare();
        return rootView;
    }

    public void prepare() {
        int workTime = app.getSecurePrefs().getInt("workTime", App.DEFAULT_WORK_TIME);
        workTimeText.setText(String.format("%02d:00", workTime));
        workTimeBar.setProgress((workTime - WORK_TIME_MIN) / WORK_TIME_STEP);
    }

    private void saveWorkTime(int time) {
        SecurePreferences.Editor editor = app.getSecurePrefs().edit();
        editor.putInt("workTime", time);
        editor.commit();
    }
}