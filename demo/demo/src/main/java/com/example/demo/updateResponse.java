package com.example.demo;

public class updateResponse {

    public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	private String cookie;
    private String authToken;

    public updateResponse(String cookie, String authToken) {
        this.cookie = cookie;
        this.authToken = authToken;
    }

    // getters and setters
}

