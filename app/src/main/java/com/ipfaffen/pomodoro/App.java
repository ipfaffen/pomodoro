package com.ipfaffen.pomodoro;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.ipfaffen.pomodoro.content.Preferences;
import com.ipfaffen.pomodoro.db.DatabaseHelper;
import com.ipfaffen.pomodoro.entity.DaoMaster;
import com.ipfaffen.pomodoro.entity.DaoSession;

import org.greenrobot.greendao.database.Database;

import java.io.IOException;

/**
 * @author Isaias Pfaffenseller
 */
public class App extends Application {

    public static final int DEFAULT_WORK_TIME_MINUTES = 25;
    public static final int MIN_POMODORO_TIME_SECONDS = 10;

    private DaoSession daoSession;
    private Preferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new Preferences(getApplicationContext());
        try {
            DatabaseHelper helper = new DatabaseHelper(this, "pomodoro.db");
            Database db = helper.getWritableDb();
            daoSession = new DaoMaster(db).newSession();
        }
        catch(IOException i) {
            Log.e("DB", "Failed opening database.", i);
        }
    }

    /**
     * @return
     */
    public Context getContext() {
        return getApplicationContext();
    }

    /**
     * @return
     */
    public Preferences getPreferences() {
        return preferences;
    }

    /**
     * @return
     */
    public DaoSession getDaoSession() {
        return daoSession;
    }
}