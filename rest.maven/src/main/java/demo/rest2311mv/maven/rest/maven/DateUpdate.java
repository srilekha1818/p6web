import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateProjectDateServlet")
public class UpdateProjectDateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = "admin";
        String password = "admin123";

        // Call the login method to get the authentication cookie
        String authCookie = login(username, password);

        if (authCookie != null) {
            // If login successful, proceed with the update
            String projectId = request.getParameter("projectId");
            String newStartDate = request.getParameter("newStartDate");

            // Call your update method with the provided projectId, newStartDate, and authCookie
            updateP6ProjectDate(projectId, newStartDate, authCookie, response);
        } else {
            response.getWriter().println("Login failed");
        }
    }

    private String login(String username, String password) {
        try {
            // Set up your P6 REST API login URL
            String loginUrl = "http://localhost:8206/p6ws/restapi/login?DatabaseName=P6EPPM2";

            // Create URL object
            URL url = new URL(loginUrl);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable input/output streams
            connection.setDoOutput(true);

            // Create JSON payload for login
            String jsonInputString = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

            // Write JSON payload to the connection
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get authentication cookie from the response
            String authCookie = connection.getHeaderField("Set-Cookie");

            // Close the connection
            connection.disconnect();

            return authCookie;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateP6ProjectDate(String projectId, String newStartDate, String authCookie, HttpServletResponse response) {
        try {
            // Set up your P6 REST API URL
            String apiUrl = "http://localhost:8206/p6ws/restapi/project?Fields=Name,ObjectId,ParentEPSName,CreateDate,CheckOutDate,DataDate,PlannedStartDate";

            // Create URL object
            URL url = new URL(p6ApiUrl);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Cookie", authCookie);

            // Enable input/output streams
            connection.setDoOutput(true);

            // Create JSON payload
            String jsonPayload = "{\"ObjectId\": \"" + projectId + "\",\"plannedStartDate\": \"" + newStartDate + "\"}";

            // Write JSON payload to the connection
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();

            // Process the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Successful update
                response.getWriter().println("Project date updated successfully");
            } else {
                // Handle error
                response.getWriter().println("Error updating project date. Response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
