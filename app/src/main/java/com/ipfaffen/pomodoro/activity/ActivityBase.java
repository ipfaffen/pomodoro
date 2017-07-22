package com.ipfaffen.pomodoro.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.ipfaffen.pomodoro.App;
import com.ipfaffen.pomodoro.R;
import com.ipfaffen.pomodoro.exception.ValidationException;

/**
 * @author Isaias Pfaffenseller
 */
public class ActivityBase extends AppCompatActivity {

	private AlertDialog dialog;
	protected App app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = ((App) getApplicationContext());
	}

	/**
	 * @param message
	 */
	protected void alert(String message) {
		if(dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		dialog = new AlertDialog.Builder(this)
				.setMessage(message)
				.show();
	}

	/**
	 * @param editText
	 * @return
	 */
	protected String extractText(EditText editText) {
		if(isBlank(editText)) {
			return null;
		}
		return editText.getText().toString();
	}

    /**
     * @param edit
     * @return
     */
    protected boolean isBlank(EditText edit) {
        return edit.getText().toString().trim().equalsIgnoreCase("");
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

	/**
	 * @param exception
	 */
	protected void showException(Throwable exception) {
		final String message;
		Log.e(getClass().getName(), exception.getMessage(), exception);
		if(exception instanceof ValidationException) {
			message = getStringByName(exception.getMessage());
		}
		else {
			message = getString(R.string.generic);
		}
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
			}
		});
	}

	/**
	 * @param name
	 * @return
	 */
	protected String getStringByName(String name) {
		int resId = getResources().getIdentifier(name, "string", getPackageName());
		if(resId != 0) {
			return getString(resId);
		}
		return name;
	}

	/**
	 * @param message
	 */
	protected void showSuccess(final String message) {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * @param message
	 */
	protected void showError(final String message) {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
			}
		});
	}
}