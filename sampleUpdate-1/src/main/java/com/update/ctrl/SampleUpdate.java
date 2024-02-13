package com.update.ctrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;

//import org.apache.commons.httpclient.HttpStatus;
 
public class SampleUpdate {
	 
		private static String userName = "admin";
		private static String password = "admin123";
		private static String hostName = "localhost";
		private static String portNumber = "8206";
		private static String databaseName = "P6EPPM2";
		private static String Id = "LOB 1";
		private static String loginUrl = "http://" + hostName + ":" + portNumber + "/p6ws/restapi/login"
				+ "?DatabaseName="+ databaseName;
	private static String postLocationsURL = "http://" + hostName + ":" + portNumber + "/p6ws/restapi/eps?Fields=Name,ObjectId,ParentEPSName,ParentEPSId,ProjectObjectId,Id&Filter=Id :eq: " + Id;
 
	public static void main(String s[]) throws Exception {
		String responseJson = callRestURL(loginUrl, "POST");

		System.out.println("Response:- " + responseJson);
	}
 
	private static String callRestURL(String restUrl, String method) throws Exception {
		HttpURLConnection conn = null;
		try {
			
			String cookie = callLoginAPI(restUrl, method, conn);
			URL postUrl = new URL(postLocationsURL);
			conn = (HttpURLConnection) postUrl.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Cookie", cookie);
			String input = "[{\"Name\": \"Line of Business srilekha\", \"ObjectId\": \"3326\", \"ParentEPSId\": \"IT\",\r\n"
					+ "        \"ParentEPSName\": \"Information Technology\"}]";
			 
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes("UTF-8"));
            os.flush();
            if (conn.getResponseCode() != 200 && conn.getResponseCode() != 201 && conn.getResponseCode() != 204 && conn.getResponseCode() != 202
                    && conn.getResponseCode() != 203) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + " Error: " + readStreamData(conn.getErrorStream()));
            }
 
            return readStreamData(conn.getInputStream());
		} catch (Exception e) {
			if (conn != null) {
				conn.disconnect();
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return "";
		
	}
	
	private static String callLoginAPI(String restUrl, String method, HttpURLConnection conn) throws IOException {
		URL url = new URL(restUrl);
		conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Content-Type", "application/json");
		String userCredentials = userName + ":" + password;
		String base64Credentials = javax.xml.bind.DatatypeConverter.printBase64Binary(userCredentials.getBytes());
		String basicAuth = base64Credentials;
		conn.setRequestProperty("authToken", basicAuth);
		if (conn.getResponseCode() != HttpStatus.SC_OK) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + "Error: "
					+ readStreamData(conn.getErrorStream()));
		}
		return conn.getHeaderField("Set-Cookie");
	}
 
	private static String readStreamData(InputStream is) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String output;
			StringBuilder buff = new StringBuilder();
			while ((output = br.readLine()) != null) {
				buff.append(output);
			}
			return buff.toString();
		}
	}
 
	
}
