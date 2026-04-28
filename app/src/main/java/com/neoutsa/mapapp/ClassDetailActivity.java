package com.neoutsa.mapapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neoutsa.mapapp.controllers.MapController;
import com.neoutsa.mapapp.models.Building;
import com.neoutsa.mapapp.models.Course;

/** Course details (extension of ViewSchedule). */
public class ClassDetailActivity extends BaseNavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);
        wireChrome();

        Course c;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            c = getIntent().getSerializableExtra("course", Course.class);
        } else {
            c = (Course) getIntent().getSerializableExtra("course");
        }
        if (c == null) { finish(); return; }

        ((TextView) findViewById(R.id.tv_course_name)).setText(c.getClassName());
        ((TextView) findViewById(R.id.tv_building)).setText("Building: " + c.getBuildingName());
        ((TextView) findViewById(R.id.tv_room)).setText("Room: " + c.getRoomNumber());
        ((TextView) findViewById(R.id.tv_time)).setText("Time: " + c.getTeachingHour());
        ((TextView) findViewById(R.id.tv_professor)).setText("Professor: " + c.getProfessor());
        ((TextView) findViewById(R.id.tv_campus)).setText("Campus: " + c.getCampusName());

        Button route = findViewById(R.id.btn_route_to_class);
        route.setOnClickListener(v -> {
            Intent i = new Intent(this, RoutesActivity.class);
            i.putExtra("destination", c.getBuildingName());
            startActivity(i);
        });

        Button indoor = findViewById(R.id.btn_view_indoor);
        indoor.setOnClickListener(v -> {
            Building b = MapController.viewIndoorMap(fullName(c.getBuildingName()));
            if (b == null) {
                Toast.makeText(this, "Indoor map not available for "
                        + c.getBuildingName(), Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(this, IndoorMapActivity.class);
            i.putExtra("buildingShort", b.getShortCode());
            startActivity(i);
        });
    }

    private String fullName(String shortCode) {
        switch (shortCode == null ? "" : shortCode.toUpperCase()) {
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