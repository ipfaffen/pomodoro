package com.ipfaffen.pomodoro.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ipfaffen.pomodoro.App;

/**
 * @author Isaias Pfaffenseller
 */
public abstract class ActivityBase extends AppCompatActivity {

	protected App app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = ((App) getApplicationContext());
	}

	/**
	 * @param message
	 */
	protected void log(String message) {
		Log.v(getClass().getName(), message);
	}

	/**
	 * @param message
	 * @param args
	 */
	protected void log(String message, Object... args) {
		Log.v(getClass().getName(), String.format(message, args));
	}

	/**
	 * @param exception
	 */
	protected void log(Exception exception) {
		Log.e(getClass().getName(), exception.getMessage(), exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	protected void log(String message, Exception exception) {
		Log.e(getClass().getName(), message, exception);
	}
}