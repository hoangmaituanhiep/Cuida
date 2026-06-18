package com.heip.Cuida.models;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.io.Preferences;
import com.codename1.ui.CN;
import com.heip.Cuida.core.APIConfig;
import com.heip.Cuida.core.user.User;
import com.heip.Cuida.core.user.UserMapper;
import com.heip.Cuida.core.user.UserSession;

public class AuthService {
  private String url;
  private String key;

  private final String TOKEN_KEY = "jwt_session_token";

  public AuthService() {
    this.key = APIConfig.getPublishableKey();
    this.url = APIConfig.getProjectUrl();
  }

  public void login(String username, String password, AuthCallback callback) {
    if (url.isEmpty() || url.equals(null)) {
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
        Map<String, Object> userMap = (Map<String, Object>) response.get("user");

        if (accessToken != null && userMap != null) {
          String userId = (String) userMap.get("id");

          Preferences.set(TOKEN_KEY, accessToken);
          Preferences.set("current_user_id", userId);

          callback.onSuccess(accessToken);
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

  public void fecthProfile(String userId, AuthCallback callback) {
    String link = "https://" + getHost() + "/rest/v1/users?id=eq." + userId;

    ConnectionRequest request = new ConnectionRequest() {
      @Override
      public void readResponse(InputStream input) throws IOException {
        JSONParser parser = new JSONParser();

        Map<String, Object> response = parser.parseJSON(new InputStreamReader(input, "UTF-8"));
        List<Map<String, Object>> users = (List) response.get("root");

        if (users != null && !users.isEmpty()) {
          User user = UserMapper.mapToUser(users.get(0));
          UserSession.setUser(user);
          callback.onSuccess("Profile Loaded");
        }
        else {
          callback.onFailure("Missing token or userId");
        }
      }

      @Override
      public void handleErrorResponseCode(int code, String message) {
        callback.onFailure("Server error: " + code + ": " + message);
      }

      @Override
      public void handleException(Exception err) {
        callback.onFailure("Network failure: " + err.getMessage());
      }
    };

    request.setUrl(link);
    request.addRequestHeader("apikey", key);
    request.addRequestHeader("Authorization", "Bearer " + Preferences.get(TOKEN_KEY, ""));
    NetworkManager.getInstance().addToQueue(request);
  }

  private String getHost() {
    if (url.startsWith("https://")) {
      return url.substring(8);
    } else if (url.startsWith("http://")) {
      return url.substring(7);
    }
    return url;
  }
}
