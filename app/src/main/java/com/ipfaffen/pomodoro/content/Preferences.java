package com.ipfaffen.pomodoro.content;

import android.content.Context;

import com.ipfaffen.pomodoro.App;
import com.securepreferences.SecurePreferences;

/**
 * @author Isaias Pfaffenseller
 */
public class Preferences {

    private SecurePreferences securePrefs;

    /**
     * @param context
     */
    public Preferences(Context context) {
        securePrefs = new SecurePreferences(context);
    }

    /**
     * @return
     */
    public int getWorkTime() {
        return securePrefs.getInt("workTime", App.DEFAULT_WORK_TIME_MINUTES);
    }

    /**
     * @param time
     */
    public void setWorkTime(int time) {
        SecurePreferences.Editor editor = securePrefs.edit();
        editor.putInt("workTime", time);
        editor.commit();
    }
}