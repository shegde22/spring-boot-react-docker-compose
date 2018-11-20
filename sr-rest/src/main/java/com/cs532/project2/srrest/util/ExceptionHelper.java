package com.cs532.project2.srrest.util;

public class ExceptionHelper {
	public static String getRootCauseMessage(Throwable e) {
		Throwable cause = null;
		Throwable result = e;
		while (null != (cause = result.getCause()) && (result != cause))
			result = cause;
		return result.getMessage();
	}
}
