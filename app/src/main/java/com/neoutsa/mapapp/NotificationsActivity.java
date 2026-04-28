package com.neoutsa.mapapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neoutsa.mapapp.controllers.NotificationController;
import com.neoutsa.mapapp.models.Notification;

import java.util.List;

/** ModifyNotifications use case — viewPastNotifications. Matches the mockup. */
public class NotificationsActivity extends BaseNavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        wireChrome();
    }

    @Override
    protected void onResume() {
        super.onResume();
        render();
    }

    private void render() {
        LinearLayout list = findViewById(R.id.notif_list);
        list.removeAllViews();
        List<Notification> all = NotificationController.viewPastNotifications();
        if (all.isEmpty()) {
            TextView t = new TextView(this);
            t.setText("No notifications.");
            t.setGravity(Gravity.CENTER);
            t.setPadding(20, 40, 20, 40);
            t.setTextColor(Color.GRAY);
            list.addView(t);
            return;
        }
        for (Notification n : all) list.addView(makeRow(n));
    }

    private View makeRow(Notification n) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setBackgroundResource(R.drawable.bg_notification_row);
        row.setPadding(16, 16, 16, 16);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.ic_mail);
        icon.setColorFilter(Color.parseColor(severityColor(n.getSeverity())));
        LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(
                dp(32), dp(32));
        iconLp.rightMargin = dp(12);
        icon.setLayoutParams(iconLp);
        row.addView(icon);

        LinearLayout col = new LinearLayout(this);
        col.setOrientation(LinearLayout.VERTICAL);
        col.setLayoutParams(new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView title = new TextView(this);
        title.setText(n.getTitle());
        title.setTextColor(Color.parseColor("#0F2547"));
        title.setTextSize(15f);
        title.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView msg = new TextView(this);
        msg.setText(n.getMessage());
        msg.setTextColor(Color.parseColor("#333333"));
        msg.setTextSize(13f);
        msg.setMaxLines(2);
        msg.setEllipsize(android.text.TextUtils.TruncateAt.END);

        TextView time = new TextView(this);
        time.setText(n.getTime() + "  ·  " + n.getSeverity());
        time.setTextColor(Color.parseColor("#888888"));
        time.setTextSize(11f);

        col.addView(title);
        col.addView(msg);
        col.addView(time);
        row.addView(col);

        row.setOnClickListener(v -> Toast.makeText(this,
                n.getTitle() + "\n" + n.getMessage(),
                Toast.LENGTH_LONG).show());
        return row;
    }

    private String severityColor(String severity) {
        if ("Critical".equalsIgnoreCase(severity)) return "#E53935";
        if ("Warning".equalsIgnoreCase(severity)) return "#F15A22";
        return "#0F2547";
    }

    private int dp(int v) {
        return (int) (v * getResources().getDisplayMetrics().density);
    }
}
