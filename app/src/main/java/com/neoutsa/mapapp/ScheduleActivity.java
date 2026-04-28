package com.neoutsa.mapapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neoutsa.mapapp.controllers.ScheduleController;
import com.neoutsa.mapapp.data.DataStore;
import com.neoutsa.mapapp.models.Course;
import com.neoutsa.mapapp.models.Student;

import java.util.List;

/** ViewSchedule use case — pill list of courses, tap one for details. */
public class ScheduleActivity extends BaseNavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        wireChrome();
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderClasses();
    }

    private void renderClasses() {
        LinearLayout pills = findViewById(R.id.class_pills);
        TextView more = findViewById(R.id.tv_more_info);
        pills.removeAllViews();

        Student s = DataStore.get().getCurrentStudent();
        if (s == null) {
            Toast.makeText(this, "Please sign in.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Course> courses = ScheduleController.viewSchedule(s);
        if (courses.isEmpty()) {
            TextView t = new TextView(this);
            t.setText("(no schedule submitted yet)");
            t.setTextColor(Color.WHITE);
            t.setGravity(Gravity.CENTER);
            t.setPadding(0, 12, 0, 12);
            pills.addView(t);
            more.setText("Open Settings -> Select Classes to add courses.");
            return;
        }

        more.setText(R.string.click_for_more);
        for (Course c : courses) pills.addView(makePill(c));
    }

    private View makePill(Course c) {
        TextView t = new TextView(this);
        t.setText(c.getClassName() + " ...");
        t.setTextColor(Color.parseColor("#0F2547"));
        t.setTextSize(16f);
        t.setTypeface(null, android.graphics.Typeface.BOLD);
        t.setBackgroundResource(R.drawable.bg_class_pill);
        t.setGravity(Gravity.CENTER);
        t.setPadding(40, 16, 40, 16);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 8, 0, 0);
        t.setLayoutParams(lp);

        t.setOnClickListener(v -> {
            Intent i = new Intent(this, ClassDetailActivity.class);
            i.putExtra("course", c);
            startActivity(i);
        });
        return t;
    }
}
