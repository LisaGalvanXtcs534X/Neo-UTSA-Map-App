package com.neoutsa.mapapp;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neoutsa.mapapp.controllers.MapController;
import com.neoutsa.mapapp.models.Building;
import com.neoutsa.mapapp.views.IndoorMapView;

/** ViewIndoorMap use case. */
public class IndoorMapActivity extends BaseNavActivity {

    private Building building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor_map);
        wireChrome();

        String shortCode = getIntent().getStringExtra("buildingShort");
        if (shortCode == null) shortCode = "SU"; // default to Student Union

        building = MapController.viewIndoorMap(lookupName(shortCode));
        if (building == null) {
            Toast.makeText(this, "Indoor map not available.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        TextView title = findViewById(R.id.tv_building_title);
        title.setText(building.getName().toUpperCase() + " - 2D MAP");

        IndoorMapView v = findViewById(R.id.indoor_map);
        v.setBuilding(building);

        EditText search = findViewById(R.id.in_search);
        search.setOnEditorActionListener((tv, actionId, ev) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String q = tv.getText().toString().trim();
                Building.IndoorRoom found = null;
                for (Building.IndoorRoom r : building.getRooms()) {
                    if (r.code.equalsIgnoreCase(q) || r.label.toLowerCase().contains(q.toLowerCase())) {
                        found = r; break;
                    }
                }
                if (found == null) {
                    Toast.makeText(this, "Location \"" + q + "\" not found.",
                            Toast.LENGTH_SHORT).show();
                    v.highlightRoom(null);
                } else {
                    v.highlightRoom(found.code);
                    Toast.makeText(this, "Found: " + found.label,
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });
    }

    private String lookupName(String shortCode) {
        switch (shortCode) {
            case "SU":  return "Student Union";
            case "MH":  return "McKinney Humanities Building";
            case "SSC": return "Student Success Center";
            case "NPB": return "North Paseo Building";
            default:    return shortCode;
        }
    }

    @Override
    protected boolean showBackArrow() { return true; }
}
