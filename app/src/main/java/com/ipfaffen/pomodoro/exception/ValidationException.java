package com.ipfaffen.pomodoro.exception;

/**
 * @author Isaias Pfaffenseller
 */
public class ValidationException extends Exception {

	/**
	 * @param message
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
