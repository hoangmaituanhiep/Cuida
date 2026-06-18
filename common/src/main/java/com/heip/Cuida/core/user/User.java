package com.heip.Cuida.core.user;

public class User {
  private String id;
  private String username;
  private String email;
  private String phone;
  private String birthdate;
  private String avatarUrl;
  private int battery = -1;
  public void setId(String id) {
    this.id = id;
  }
  public String getId() {
    return id;
  }
  public String getUsername() {
    return username;
  }
  public String getEmail() {
    return email;
  }
  public String getPhone() {
    return phone;
  }
  public String getBirthdate() {
    return birthdate;
  }
  public String getAvatarUrl() {
    return avatarUrl;
  }
  public User(String username, String email, String phone, String birthdate, String avatarUrl) {
    this.username = username;
    this.email = email;
    this.phone = phone;
    this.birthdate = birthdate;
    this.avatarUrl = avatarUrl;
  }
  public User(){
    //
  }
}
