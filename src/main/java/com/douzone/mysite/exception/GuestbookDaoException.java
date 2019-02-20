package com.douzone.mysite.exception;

public class GuestbookDaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GuestbookDaoException() {
		super("GuestbookDao 예외");
	}
	
	public GuestbookDaoException(String message) {
		super(message);
	}

}
