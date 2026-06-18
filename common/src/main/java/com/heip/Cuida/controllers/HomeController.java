package com.heip.Cuida.controllers;

import com.codename1.capture.Capture;
import com.codename1.ui.Dialog;
import com.heip.Cuida.views.HomeView;

public class HomeController {
  private HomeView view;
  private int startX;

  public HomeController(HomeView view) {
    this.view = view;
    initListener();
    initSwipe();
  }

  private void initListener() {
    view.getUpload().addActionListener(event -> {
      String videoPath = Capture.captureVideo();

      if (!videoPath.equals(null) && !videoPath.isEmpty()) {
        handleUpload(videoPath);
      } else {
        Dialog.show("Lỗi", "Không thể truy cập camera", "OK", null);
      }
    });
  }

  private void initSwipe() {
    view.addPointerPressedListener(event -> {
      startX = event.getX();
    });

    view.addPointerReleasedListener(event -> {
      int endX = event.getX();

      int diff = endX - startX;

      if (diff >= 100) {
        loadVideo(1);
      }
      else if (diff <= -100) {
        loadVideo(-1);
      }
    });
  }

  private void handleUpload(String videoPath) {
    //TODO: handle the video
  }

  private void loadVideo(int index) {
    //TODO: handle load
  }
}
