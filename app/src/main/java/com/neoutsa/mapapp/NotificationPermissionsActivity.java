package com.neoutsa.mapapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.neoutsa.mapapp.controllers.NotificationController;
import com.neoutsa.mapapp.data.DataStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** ModifyNotifications use case. */
public class NotificationPermissionsActivity extends BaseNavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_permissions);
        wireChrome();

        Switch sAllow = findViewById(R.id.switch_allow);
        Switch sSound = findViewById(R.id.switch_sound);
        Button btnTest = findViewById(R.id.btn_test_alert);

        sAllow.setChecked(DataStore.get().isNotificationsEnabled());
        sSound.setChecked(DataStore.get().isNotificationSoundEnabled());

        sAllow.setOnCheckedChangeListener((b, on) ->
                NotificationController.modifyNotifications(on, sSound.isChecked()));
        sSound.setOnCheckedChangeListener((b, on) ->
                NotificationController.modifyNotifications(sAllow.isChecked(), on));

        btnTest.setOnClickListener(v -> {
            String now = new SimpleDateFormat("MMM d  h:mm a", Locale.US).format(new Date());
            NotificationController.sendNotification(
                    "Test Alert", "This is a test notification from the app.",
                    "Info", now);
            Toast.makeText(this, "Test alert sent.", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected boolean showBackArrow() { return true; }
}
