package com.heip.Cuida.core.user;

import java.util.Map;

public class UserMapper {
  public static User mapToUser(Map<String, Object> json) {
    String id = (String) json.get("id");
    String username = "unknown";
    Map<String, Object> metaData = (Map) json.get("raw_user_meta_data");
    if (metaData != null) {
      username = (String) metaData.get("username");
    }
    String email = (String) json.get("email");
    String phone = (String) json.get("phone");
    String avatarUrl = (String) json.get("avatar");
    String birthdate = (String) json.get("birthdate");

    User user = new User(username, email, phone, birthdate, avatarUrl);
    user.setId(id);

    return user;
  }
}
