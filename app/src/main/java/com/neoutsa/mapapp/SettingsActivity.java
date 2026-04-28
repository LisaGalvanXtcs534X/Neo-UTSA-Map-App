package com.neoutsa.mapapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neoutsa.mapapp.controllers.AuthController;

public class SettingsActivity extends BaseNavActivity {

    private static final String[] OPTIONS = {
            "Display Settings", "Visual Settings", "Language Settings",
            "Select Classes", "Notification Permissions", "GPS Settings",
            "Contact University", "Terms of Service", "UTSA Privacy Policy",
            "User Profile", "Reset Default Settings", "Log Out"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        wireChrome();

        LinearLayout list = findViewById(R.id.settings_list);
        for (String label : OPTIONS) list.addView(makeRow(label));
    }

    private View makeRow(String label) {
        TextView t = new TextView(this);
        t.setText("• " + label);
        t.setTextColor(Color.parseColor("#0F2547"));
        t.setTextSize(17f);
        t.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        t.setPadding(0, 18, 0, 18);
        t.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        t.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        t.setOnClickListener(v -> handleClick(label));
        return t;
    }

    private void handleClick(String label) {
        switch (label) {
            case "Select Classes":
                startActivity(new Intent(this, SelectClassesActivity.class));
                break;
            case "Notification Permissions":
                startActivity(new Intent(this, NotificationPermissionsActivity.class));
                break;
            case "Log Out":
                AuthController.logout();
                Intent i = new Intent(this, SignInActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                break;
            default:
                Toast.makeText(this, label + " (coming soon)", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected boolean showBackArrow() { return true; }
}
