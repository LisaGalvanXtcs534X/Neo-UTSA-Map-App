package com.neoutsa.mapapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neoutsa.mapapp.controllers.MapController;
import com.neoutsa.mapapp.data.DataStore;

import java.util.List;

/** SearchRoutes use case. */
public class RoutesActivity extends BaseNavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        wireChrome();

        String dest = getIntent().getStringExtra("destination");
        if (dest == null || dest.isEmpty()) dest = "UTSA Downtown Campus";
        showRoutes(dest);
        EditText input = findViewById(R.id.in_destination);
        input.setText(dest);
        input.setOnEditorActionListener((tv, actionId, ev) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                String typed = tv.getText().toString().trim();
                if (!DataStore.get().isGpsEnabled()) {
                    Toast.makeText(this,
                            "GPS/location services are disabled.",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                showRoutes(typed);
                return true;
            }
            return false;
        });
    }

    private void showRoutes(String dest) {
        List<MapController.RouteOption> opts =
                MapController.searchRoutes("Devonshire Condominiums", dest);
        LinearLayout pills = findViewById(R.id.route_pills);
        pills.removeAllViews();
        if (opts.isEmpty()) {
            Toast.makeText(this, "Destination not found.", Toast.LENGTH_SHORT).show();
            return;
        }
        for (MapController.RouteOption r : opts) pills.addView(makePill(r));
    }

    private View makePill(MapController.RouteOption r) {
        TextView t = new TextView(this);
        t.setText(r.minutes + " minutes");
        t.setTextColor(r.fastest ? Color.WHITE : Color.parseColor("#0F2547"));
        t.setBackgroundResource(r.fastest
                ? R.drawable.bg_route_pill_active : R.drawable.bg_route_pill);
        t.setGravity(Gravity.CENTER);
        t.setPadding(28, 12, 28, 12);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 8, 0, 8);
        t.setLayoutParams(lp);
        return t;
    }
}
