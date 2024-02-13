package demo.rest2311mv.maven.rest.maven;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Scanner;



import org.apache.http.HttpStatus;

 
public class SampleGET {
	

 
	 static String userName = "admin";
	  static String password = "admin123";
     static String hostName = "localhost";
	 static String portNumber = "8206";
	  static String databaseName = "P6EPPM2";
	  static String loginUrl = "http://" + hostName + ":" + portNumber + "/p6ws/restapi/login"
			+ "?DatabaseName="+ databaseName;

	  public static void main(String s[]) throws Exception {
		
			String responseJson = callRestURL(loginUrl, "POST");
			 Gson gson = new GsonBuilder().setPrettyPrinting().create();
		        JsonElement jsonElement = JsonParser.parseString(responseJson);
		        String prettyJson = gson.toJson(jsonElement);
	
			//System.out.println(json);
			System.out.println("Response:- " + prettyJson);
		}	 

	static String callRestURL(String restUrl, String method) throws Exception {
		System.out.print("please enter input: ");
		Scanner input = new Scanner(System.in);
		  String objectId = input.nextLine(); 
		  String loadActivitiesURL = "http://" + hostName + ":" + portNumber + "/p6ws/restapi/project?Fields=Name,ObjectId,ParentEPSName,Id&Filter=Id LIKE '%"+objectId+"%'";
		HttpURLConnection conn = null;
		try {
	
			String cookie = callLoginAPI(restUrl, method, conn);
			URL loadUrl = new URL(loadActivitiesURL);
			conn = (HttpURLConnection) loadUrl.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Cookie", cookie);
			if (conn.getResponseCode() != HttpStatus.SC_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + "Error: "
						+ readStreamData(conn.getErrorStream()));
			}
			return readStreamData(conn.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return "";
		
	}
	//login
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

	




