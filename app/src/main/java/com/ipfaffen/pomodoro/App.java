package com.ipfaffen.pomodoro;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.ipfaffen.pomodoro.db.DataBaseHelper;
import com.ipfaffen.pomodoro.entity.DaoMaster;
import com.ipfaffen.pomodoro.entity.DaoSession;
import com.securepreferences.SecurePreferences;

import org.greenrobot.greendao.database.Database;

import java.io.IOException;

/**
 * @author Isaias Pfaffenseller
 */
public class App extends Application {

    public static final int DEFAULT_WORK_TIME = 25;

    private DaoSession daoSession;
    private SecurePreferences securePrefs;
    private String username;
    private String deviceId;

    @Override
    public void onCreate() {
        super.onCreate();

        // Setup secure preferences.
        securePrefs = new SecurePreferences(getApplicationContext());

        try {
            DataBaseHelper helper = new DataBaseHelper(this, "pomodoro.db");
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
    public SecurePreferences getSecurePrefs() {
        return securePrefs;
    }

    /**
     * @return
     */
    public DaoSession getDaoSession() {
        return daoSession;
    }
}