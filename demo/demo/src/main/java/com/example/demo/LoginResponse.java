package com.example.demo;

public class LoginResponse {

    public static String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public static String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	private static String cookie;
    private static String authToken;

    public LoginResponse(String cookie, String authToken) {
        this.cookie = cookie;
        this.authToken = authToken;
    }

	

    // getters and setters
}

