package com.ipfaffen.pomodoro.fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.ipfaffen.pomodoro.App;
import com.ipfaffen.pomodoro.R;
import com.ipfaffen.pomodoro.activity.MainActivity;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * @author Isaias Pfaffenseller
 */
public abstract class FragmentBase extends Fragment {

    private PrettyTime prettyTime;
	protected App app;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = ((App) getActivity().getApplicationContext());
	}

	public abstract void prepare();

    /**
     * @param titleResId
     * @param textResId
     */
    protected void notifiy(int titleResId, int textResId) {
        notifiy(getString(titleResId), getString(textResId));
    }

    /**
     * @param title
     * @param text
     */
    protected void notifiy(String title, String text) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.ic_timer_light_24dp)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                        .setContentTitle(title)
                        .setContentText(text)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        Intent resultIntent = new Intent(getActivity(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addParentStack(getActivity().getClass());
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =(NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    /**
     * @param stringResId
     * @param params
     */
    protected void showWarning(int stringResId, Object... params) {
        showWarning(getString(stringResId), params);
    }

    /**
     * @param message
     * @param params
     */
    protected void showWarning(final String message, final Object... params) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity().getBaseContext(), String.format(message, params), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * @param name
     * @return
     */
    protected String getStringByName(String name) {
        int resId = getResources().getIdentifier(name, "string", getActivity().getPackageName());
        if(resId != 0) {
            return getString(resId);
        }
        return name;
    }

    /**
     * @return
     */
    protected PrettyTime getPrettyTime() {
        if(prettyTime == null) {
            prettyTime = new PrettyTime();
        }
        return prettyTime;
    }
}