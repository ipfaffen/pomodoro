package com.ipfaffen.pomodoro.util;

import java.util.concurrent.TimeUnit;

/**
 * @author Isaias Pfaffenseller
 */
public class Util {

    /**
     * @param timeMillis
     * @return
     */
    public static String formatToHourMinute(Long timeMillis) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(timeMillis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(timeMillis) % TimeUnit.MINUTES.toSeconds(1));
    }
}
