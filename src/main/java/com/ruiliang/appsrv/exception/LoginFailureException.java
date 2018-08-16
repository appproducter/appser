package com.ruiliang.appsrv.exception;

public class LoginFailureException extends Exception {

	private static final long serialVersionUID = 3854573851197569290L;

	public LoginFailureException() {

	}

	public LoginFailureException(String msg) {
		super(msg);
	}
}
