package com.neoutsa.mapapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neoutsa.mapapp.controllers.ScheduleController;
import com.neoutsa.mapapp.data.DataStore;
import com.neoutsa.mapapp.models.Course;
import com.neoutsa.mapapp.models.Student;

/** SubmitSchedule use case implementation. */
public class SelectClassesActivity extends BaseNavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_classes);
        wireChrome();

        refreshCurrent();

        EditText eC = findViewById(R.id.in_course);
        EditText eB = findViewById(R.id.in_building);
        EditText eR = findViewById(R.id.in_room);
        EditText eT = findViewById(R.id.in_time);
        EditText eP = findViewById(R.id.in_prof);
        EditText eCa = findViewById(R.id.in_campus);
        Button btn = findViewById(R.id.btn_add);

        btn.setOnClickListener(v -> {
            Student s = DataStore.get().getCurrentStudent();
            if (s == null) {
                Toast.makeText(this, "No active session.", Toast.LENGTH_SHORT).show();
                return;
            }
            String cn = eC.getText().toString().trim();
            String bn = eB.getText().toString().trim();
            if (cn.isEmpty() || bn.isEmpty()) {
                Toast.makeText(this, "Course # and Building are required.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            Course c = new Course(cn, eT.getText().toString().trim(),
                    eR.getText().toString().trim(), bn,
                    eCa.getText().toString().trim(), eP.getText().toString().trim());
            if (ScheduleController.submitSchedule(s, c)) {
                Toast.makeText(this, "Schedule saved.", Toast.LENGTH_SHORT).show();
                eC.setText(""); eB.setText(""); eR.setText("");
                eT.setText(""); eP.setText("");
                refreshCurrent();
            } else {
                Toast.makeText(this, "Could not save schedule. Try again later.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void refreshCurrent() {
        LinearLayout list = findViewById(R.id.current_classes);
        list.removeAllViews();
        Student s = DataStore.get().getCurrentStudent();
        if (s == null) return;
        for (Course c : s.getSemesterSchedule().getCourses()) {
            list.addView(makeRow(c));
        }
        if (s.getSemesterSchedule().getCourses().isEmpty()) {
            TextView t = new TextView(this);
            t.setText("(no classes added yet)");
            t.setTextColor(Color.GRAY);
            list.addView(t);
        }
    }

    private View makeRow(Course c) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(0, 8, 0, 8);

        TextView t = new TextView(this);
        t.setText("• " + c.getClassName() + "  (" + c.getBuildingName() +
                " " + c.getRoomNumber() + ")");
        t.setTextColor(Color.parseColor("#0F2547"));
        t.setTextSize(15f);
        t.setLayoutParams(new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView del = new TextView(this);
        del.setText("Remove");
        del.setTextColor(Color.parseColor("#E53935"));
        del.setGravity(Gravity.END);
        del.setOnClickListener(v -> {
            ScheduleController.removeFromSchedule(
                    DataStore.get().getCurrentStudent(), c);
            refreshCurrent();
        });

        row.addView(t);
        row.addView(del);
        return row;
    }

    @Override
    protected boolean showBackArrow() { return true; }
}
