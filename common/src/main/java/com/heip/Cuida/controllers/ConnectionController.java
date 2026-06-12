package com.heip.Cuida.controllers;

import com.codename1.ui.Dialog;
import com.codename1.ui.layouts.BorderLayout;
import com.heip.Cuida.models.AuthCallback;
import com.heip.Cuida.models.AuthService;
import com.heip.Cuida.views.LoginView;
import com.heip.Cuida.views.SignupView;

public class ConnectionController {
  private LoginView loginView;
  private SignupView signupView;
  private AuthService service;

  public ConnectionController() {
    service = new AuthService();
  }
  public ConnectionController(LoginView view) {
    super();
    this.loginView = view;
  }
  public ConnectionController(SignupView view) {
    super();
    this.signupView = view;
  }
  public ConnectionController(SignupView signupView, LoginView loginView) {
    super();
    this.loginView = loginView;
    this.signupView = signupView;
  }

  private void initListener() {
    loginView.getLogin().addActionListener(event -> {
      String username = loginView.getUsername().getText().trim();
      String password = loginView.getPassword().getText().trim();

      if (username.isEmpty() || password.isEmpty() || username.equals(null) || password.equals(null)) {
        Dialog.show("Thông báo", "Nhập thiếu username hoặc password.");
        return;
      }

      Dialog progress = new Dialog();
      progress.showPacked(BorderLayout.CENTER, false);

      service.login(username, password, new AuthCallback() {
        @Override
        public void onSuccess(String token) {
          progress.dispose();
          
          //TODO: Opening home scene.
        }

        @Override
        public void onFailure(String error) {
          progress.dispose();
          Dialog.show("Lỗi đăng nhập", error, "Thử lại.", null);
        }
      });
    });

    signupView.getSignup().addActionListener(even -> {
      String username = signupView.getUsername().getText().trim();
      String phone = signupView.getPhone().getText().trim();
      String email = signupView.getEmail().getText().trim();
      String password = signupView.getPassword().getText().trim();
      String confirmPassword = signupView.getConfirmPassword().getText().trim();

      if (username.isEmpty() || password.isEmpty() || username.equals(null) || password.equals(null)) {
        Dialog.show("Thông báo", "Nhập thiếu username hoặc password.");
        return;
      }

      if (!password.equals(confirmPassword)) {
        Dialog.show("Thông báo", "Xác nhận mật khẩu không khớp mật khẩu.");
        return;
      }

      Dialog progress = new Dialog();
      progress.showPacked(BorderLayout.CENTER, false);

      service.signup(username, phone, email, confirmPassword, new AuthCallback() {
        @Override
        public void onSuccess(String token) {
          progress.dispose();

          //TODO: Open login Scene
        }
        @Override
        public void onFailure(String error) {
          progress.dispose();
          Dialog.show("Lỗi đăng ký", error, "Thử lại.", null);
        }
      });
    });
  }
}
