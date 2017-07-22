package com.ipfaffen.pomodoro.fragment;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ipfaffen.pomodoro.App;
import com.ipfaffen.pomodoro.R;
import com.ipfaffen.pomodoro.entity.DaoSession;
import com.ipfaffen.pomodoro.entity.Pomodoro;
import com.ipfaffen.pomodoro.entity.PomodoroDao;
import com.ipfaffen.pomodoro.type.PomodoroState;
import com.ipfaffen.pomodoro.type.PomodoroType;
import com.ipfaffen.pomodoro.util.PomodoroTimer;
import com.ipfaffen.pomodoro.util.Util;

import java.util.Date;

/**
 * @author Isaias Pfaffenseller
 */
public class TimerFragment extends FragmentBase {

    private static final int TRANSITION_DURATION = 500;

    private View stateView;
    private TextView timeLeftText;
    private TextView timerStateText;
    private FloatingActionButton startButton;
    private FloatingActionButton stopButton;
    private ValueAnimator textColorAnimation;
    private View.OnClickListener startClickListener;
    private View.OnClickListener stopClickListener;

    private int workTime;
    private long workTimeMillis;
    private long leftTimeMillis;
    private boolean timerRunning;
    private boolean prepareOnTimerEnd;

    private PomodoroDao pomodoroDao;
    private Pomodoro pomodoro;

    public static TimerFragment newInstance() {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaoSession daoSession = app.getDaoSession();
        pomodoroDao = daoSession.getPomodoroDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timer, container, false);

        stateView = rootView.findViewById(R.id.state_view);
        timeLeftText = (TextView) rootView.findViewById(R.id.time_left_text);
        timerStateText = (TextView) rootView.findViewById(R.id.timer_state_text);
        stopButton = (FloatingActionButton) rootView.findViewById(R.id.stop_button);
        startButton = (FloatingActionButton) rootView.findViewById(R.id.start_button);

        Integer colorFrom = ContextCompat.getColor(app, android.R.color.darker_gray);
        Integer colorTo = ContextCompat.getColor(app, android.R.color.white);
        textColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        textColorAnimation.setDuration(TRANSITION_DURATION);
        textColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                timeLeftText.setTextColor((Integer)animator.getAnimatedValue());
                timerStateText.setTextColor((Integer)animator.getAnimatedValue());
            }
        });

        prepare();
        return rootView;
    }

    public void prepare() {
        if(timerRunning) {
            prepareOnTimerEnd = true;
            return;
        }

        workTime = app.getSecurePrefs().getInt("workTime", App.DEFAULT_WORK_TIME);
        workTimeMillis = (workTime * 60 * 1000);
        leftTimeMillis = workTimeMillis;

        // Set initial tile left text.
        timeLeftText.setText(String.format("%02d:00", workTime));

        final PomodoroTimer pomodoroTimer = new PomodoroTimer(workTimeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                leftTimeMillis = millisUntilFinished;
                stepTimer(leftTimeMillis);
            }

            @Override
            public void onFinish(boolean stopped) {
                finishTimer(workTimeMillis, leftTimeMillis, stopped);
                timerRunning = false;
                if(prepareOnTimerEnd) {
                    prepareOnTimerEnd = false;
                    prepare();
                }
            }
        };
        startClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
                timerRunning = true;
                pomodoroTimer.start();
            }
        };
        stopClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pomodoroTimer.stop();
            }
        };
        stateView.setOnClickListener(startClickListener);
        startButton.setOnClickListener(startClickListener);
        stopButton.setOnClickListener(stopClickListener);
    }

    private void startTimer() {
        ((TransitionDrawable) stateView.getBackground()).startTransition(TRANSITION_DURATION);
        stopButton.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.GONE);
        textColorAnimation.start();
        timerStateText.setText(getString(R.string.timer_running));

        // Set stop click listener to state view while timer is running.
        stateView.setOnClickListener(stopClickListener);

        // Create the pomodoro record.
        insertPomodoro();
    }

    private void stepTimer(long millisUntilFinished) {
        // Set time left text.
        timeLeftText.setText(Util.formatToHourMinute(millisUntilFinished));
    }

    private void finishTimer(long workTimeMillis, long leftTimeMillis, boolean stopped) {
        ((TransitionDrawable) stateView.getBackground()).reverseTransition(TRANSITION_DURATION);
        stopButton.setVisibility(View.GONE);
        startButton.setVisibility(View.VISIBLE);
        textColorAnimation.reverse();
        timeLeftText.setText(String.format("%02d:00", workTime));
        timerStateText.setText(getString(R.string.timer_waiting));

        // Set start click listener to state view while timer is waiting.
        stateView.setOnClickListener(startClickListener);

        if(stopped) {
            // Update pomodoro record as stopped.
            updatePomodoro(new Date(),
                    (workTimeMillis - leftTimeMillis),
                    PomodoroState.STOPPED.code());
        }
        else {
            // Update pomodoro record as finished.
            updatePomodoro(new Date(),
                    workTimeMillis,
                    PomodoroState.FINISHED.code());
        }

        if(!stopped) {
            // Notify user that work time has finisehd.
            notifiy(R.string.notification_timer_finished_title, R.string.notification_timer_finished_text);
        }
    }

    private void insertPomodoro() {
        pomodoro = new Pomodoro();
        pomodoro.setStartDate(new Date());
        pomodoro.setType(PomodoroType.WORK.code());
        pomodoro.setState(PomodoroState.STARTED.code());
        pomodoroDao.insert(pomodoro);
    }

    private void updatePomodoro(Date endDate, long usedTimeMillis, String state) {
        pomodoro.setEndDate(endDate);
        pomodoro.setUsedTimeMillis(usedTimeMillis);
        pomodoro.setState(state);
        pomodoroDao.update(pomodoro);
    }
}