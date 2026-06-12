package com.heip.Cuida.models;

public interface AuthCallback {
  void onSuccess(String token);
  void onFailure(String error);
}
