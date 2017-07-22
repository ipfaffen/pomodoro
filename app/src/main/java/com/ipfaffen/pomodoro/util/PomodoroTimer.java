package com.ipfaffen.pomodoro.util;

import android.os.CountDownTimer;

/**
 * @author Isaias Pfaffenseller
 */
public abstract class PomodoroTimer extends CountDownTimer {

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public PomodoroTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onFinish() {
        onFinish(false);
    }

    /**
     * @param stopped
     */
    public abstract void onFinish(boolean stopped);

    public void onStop() {
        onFinish(true);
    }

    public void stop() {
        cancel();
        onStop();
    }
}
