package com.ipfaffen.pomodoro.listener;

import android.widget.SeekBar;

/**
 * @author Isaias Pfaffenseller
 */
public abstract class OnSeekBarChangeSimpleListener implements SeekBar.OnSeekBarChangeListener {

    @Override
    public abstract void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser);

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public abstract void onStopTrackingTouch(SeekBar seekBar);
}