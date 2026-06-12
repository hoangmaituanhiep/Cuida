package com.heip.Cuida.models;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.io.Preferences;
import com.codename1.ui.CN;

public class AuthService {
  private String url;
  private String key;

  private final String TOKEN_KEY = "jwt_session_token";

  public AuthService() {
    try (InputStream inp = CN.getResourceAsStream("/config.properties")) {
      if (inp != null) {
        Properties properties = new Properties();
        properties.load(inp);

        this.url = properties.getProperty("project_url");
        this.key = properties.getProperty("ublishable_key");
      }
      else {
        Log.p("Error cannot find config file.", Log.WARNING);
      }
    }
    catch (IOException e) {
      Log.e(e);
    }
  }

  public void login(String username, String password, AuthCallback callback) {
    if (url.isEmpty() && url.equals(null)) {
      callback.onFailure("Error: Cannot connect to server via url");
      return;
    }
    String link = "https://" + getHost() + "/auth/v1/token?grant_type=password";
    String json = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
    sendAuthRequest(link, json, callback);
  }

  public void signup(String username, String phone, String email, String password, AuthCallback callback) {
    if (url.isEmpty() && url.equals(null)) {
      callback.onFailure("Error: Cannot connect to server via url");
      return;
    }
    String link = "https://" + getHost() + "/auth/v1/signup";
     String json = "{"
                + "\"email\":\"" + email + "\","
                + "\"password\":\"" + password + "\","
                + "\"options\":{"
                    + "\"data\":{"
                        + "\"username\":\"" + username + "\","
                        + "\"phone\":\"" + phone + "\""
                    + "}"
                + "}"
            + "}";
            sendAuthRequest(link, json, callback);
  }

  private void sendAuthRequest(String link, String json, AuthCallback callback) {
    ConnectionRequest request = new ConnectionRequest() {
      String error = "Error: lol i dont know";

      @Override
      protected void readResponse(InputStream input) throws IOException {
        JSONParser parser = new JSONParser();
        Map<String, Object> response = parser.parseJSON(new InputStreamReader(input, "UTF-8"));

        String accessToken = (String) response.get("access_token");
        if (!accessToken.isEmpty() && accessToken.equals(null)) {
          Preferences.set(TOKEN_KEY, accessToken);
        }
        else {
          callback.onFailure("Error: Missing session token");
        }
      }

      @Override
      protected void handleErrorResponseCode(int code, String message) {
        if (code == 404) {
          error = "Email or password is wrong or existed";
        }
        callback.onFailure(error);
      }
    };

    request.setUrl(link);
    request.setPost(true);
    request.setContentType("application/json");
    request.addRequestHeader("apikey", key);
    request.setRequestBody(json);
    NetworkManager.getInstance().addToQueue(request);
  }

  private String getHost() {
    return url.substring(url.indexOf("//")+2, url.indexOf("/rest/v1/"));
  }
}
