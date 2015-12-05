package com.parse.tutorials.pushnotifications;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class Application extends android.app.Application {

  public Application() {
  }

  @Override
  public void onCreate() {
    super.onCreate();

	// Initialize the Parse SDK.
	Parse.initialize(this, "fMB6piQyYMpDbCnkJFrlfPZVS5nihQfADGqycvTH", "48TtOueDGiyOlBmnJa2wwJeDz4cJIvNG6OyLaPqb");

	// Specify an Activity to handle all pushes by default.
  }
}
