package com.neoutsa.mapapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.neoutsa.mapapp.views.CampusMapView;

/** Outdoor 2D map screen — tap a building to open its indoor map (ViewIndoorMap UC). */
public class MapActivity extends BaseNavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        wireChrome();

        CampusMapView v = findViewById(R.id.campus_map);
        v.setOnBuildingClickListener(b -> {
            if (b.getRooms().isEmpty()) {
                Toast.makeText(this,
                        "Indoor map for " + b.getName() + " is not yet available.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(this, IndoorMapActivity.class);
            i.putExtra("buildingShort", b.getShortCode());
            startActivity(i);
        });
    }
}
