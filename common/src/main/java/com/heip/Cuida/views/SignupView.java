package com.heip.Cuida.views;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;

public class SignupView {
  private Form signupForm;
  private Button signup;
  private Button login;
  private TextField username;
  private TextField password;
  private TextField confirmPassword;
  private TextField email;
  private TextField phone;

  public SignupView() {
    signupForm = new Form("Hãy đăng ký!", BoxLayout.y());
    signupForm.getToolbar().hideToolbar();

    signup = new Button("Đăng ký");
    login = new Button("Đăng nhập nếu bạn có tài khoản.");

    username = new TextField("", "Tên đăng nhập", 20, TextField.ANY);
    email = new TextField("", "Email (nếu có)", 20, TextField.ANY);
    phone = new TextField("", "Số điện thoại", 20, TextField.ANY);
    password = new TextField("", "Mật khẩu", 20, TextField.PASSWORD);
    confirmPassword = new TextField("", "Xác nhận mật khẩu", 20, TextField.PASSWORD);

    login.setUIID("To-do: Set up a link to sign in");

    signupForm.add(new Label("ĐĂNG KÝ", "HeaderLabel"));
    signupForm.add(username);
    signupForm.add(email);
    signupForm.add(phone);
    signupForm.add(password);
    signupForm.add(confirmPassword);
    signupForm.add(signup);
    signupForm.add(FlowLayout.encloseCenter(login));
  }

  public void show() {
    signupForm.show();
  }

  public Button getLogin() {return login;}
  public Button getSignup() {return signup;}
}
