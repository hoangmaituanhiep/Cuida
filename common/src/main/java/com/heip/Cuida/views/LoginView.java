package com.heip.Cuida.views;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;

public class LoginView {
  private Form loginForm;
  private TextField username;
  private TextField password;
  private Button login;
  private Button signup;

  public LoginView() {
    loginForm = new Form("Welcome!", BoxLayout.y());
    loginForm.getToolbar().hideToolbar();

    username = new TextField("", "Tên đăng nhập / Email", 20, TextField.ANY);
    password = new TextField("", "Mật khẩu", 20, TextField.PASSWORD);

    login = new Button("Đăng nhập");
    signup = new Button("Đăng kí nếu bạn chưa có tài khoản.");

    signup.setUIID("To-do: Set up a link to signup page");

    loginForm.add(new Label("ĐĂNG NHẬP", "HeaderLabel"));
    loginForm.add(username);
    loginForm.add(password);
    loginForm.add(login);
    loginForm.add(FlowLayout.encloseCenter(signup));
  }

  public void show() {
    loginForm.show();
  }

  public Button getLogin() {return login;}
  public Button getSignup() {return signup;}
  public TextField getUsername() {return username;}
  public TextField getPassword() {return password;}
}
