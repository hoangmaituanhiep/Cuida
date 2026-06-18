package com.heip.Cuida.views;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;

public class HomeView extends Form{
  private Container videoContainer;
  private Button upload;

  public HomeView() {
    super(new BorderLayout());
    setUIID("HomeForm");
    initUI();
  }

  private void initUI() {
    Label header = new Label("C U I D A");
    header.setUIID("HeaderLabel");
    this.add(BorderLayout.NORTH, header);

    videoContainer = new Container(new BorderLayout());
    videoContainer.setUIID("VideoRect");

    Container videoLayer = new Container(new LayeredLayout());
    videoLayer.add(new SpanLabel("OVERLAY TEXT"));
    videoLayer.add(new Label("%battery - location"));

    videoContainer.add(BorderLayout.CENTER, videoLayer);
    this.add(BorderLayout.CENTER, videoContainer);

    Container footer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

    Container userInfo = new Container(new FlowLayout(CENTER));
    userInfo.add(new Label("avatar"));
    userInfo.add(new Label("username"));
    footer.add(userInfo);

    upload = new Button();
    upload.setUIID("Upload");
    footer.add(FlowLayout.encloseCenter(upload));
    footer.add(new Label("Send new note."));
    
    this.add(BorderLayout.SOUTH, footer);
  }

  public Button getUpload() {return upload;}
}