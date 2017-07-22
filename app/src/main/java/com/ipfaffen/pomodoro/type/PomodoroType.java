package com.ipfaffen.pomodoro.type;

/**
 * @author Isaias Pfaffenseller
 */
public enum PomodoroType {

	WORK,
	BREAK;

	/**
	 * @return
	 */
	public String code() {
		return name().substring(0, 3);
	}

	/**
	 * @param code
	 * @return
	 */
	public boolean is(String code) {
		return code().equalsIgnoreCase(code);
	}
}