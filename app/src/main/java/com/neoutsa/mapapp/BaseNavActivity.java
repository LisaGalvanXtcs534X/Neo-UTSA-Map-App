package com.neoutsa.mapapp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

/** Wires the shared header (settings gear) + bottom nav for any subclass. */
public abstract class BaseNavActivity extends AppCompatActivity {

    /** Subclasses call after setContentView() to attach common listeners. */
    protected void wireChrome() {
        ImageButton settings = findViewById(R.id.header_settings);
        if (settings != null) settings.setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));

        bind(R.id.nav_map, MapActivity.class);
        bind(R.id.nav_pin, RoutesActivity.class);
        bind(R.id.nav_search, ScheduleActivity.class);
        bind(R.id.nav_mail, NotificationsActivity.class);

        ImageButton back = findViewById(R.id.nav_back);
        if (back != null) {
            back.setVisibility(showBackArrow() ? View.VISIBLE : View.GONE);
            back.setOnClickListener(v -> finish());
        }
    }

    private void bind(int id, Class<?> dest) {
        ImageButton b = findViewById(id);
        if (b == null) return;
        b.setOnClickListener(v -> {
            Intent i = new Intent(this, dest);
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(i);
        });
    }

    /** Override to hide the back-arrow on top-level screens. */
    protected boolean showBackArrow() { return false; }
}
