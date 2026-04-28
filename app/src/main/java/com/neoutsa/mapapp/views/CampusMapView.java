package com.neoutsa.mapapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.neoutsa.mapapp.data.DataStore;
import com.neoutsa.mapapp.models.Building;

import java.util.Collection;

/**
 * 2D outdoor campus map. Draws roads, buildings, labels and a "you are here"
 * orange dot, matching the GUI mockup. Tap a building to fire a callback.
 */
public class CampusMapView extends View {

    public interface OnBuildingClickListener {
        void onBuildingClick(Building b);
    }

    private final Paint mapBg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint road = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint building = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint buildingStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint label = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint streetLabel = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint dot = new Paint(Paint.ANTI_ALIAS_FLAG);

    private OnBuildingClickListener listener;

    public CampusMapView(Context c) { super(c); init(); }
    public CampusMapView(Context c, @Nullable AttributeSet a) { super(c, a); init(); }

    private void init() {
        mapBg.setColor(Color.parseColor("#EAEEF1"));
        mapBg.setStyle(Paint.Style.FILL);

        road.setColor(Color.parseColor("#D5DCE2"));
        road.setStyle(Paint.Style.STROKE);
        road.setStrokeWidth(18f);
        road.setStrokeCap(Paint.Cap.ROUND);

        building.setColor(Color.parseColor("#8AA6BC"));
        building.setStyle(Paint.Style.FILL);

        buildingStroke.setColor(Color.parseColor("#6E8AA0"));
        buildingStroke.setStyle(Paint.Style.STROKE);
        buildingStroke.setStrokeWidth(2f);

        label.setColor(Color.parseColor("#0F2547"));
        label.setTextSize(28f);
        label.setFakeBoldText(true);

        streetLabel.setColor(Color.parseColor("#3A4A5E"));
        streetLabel.setTextSize(20f);

        dot.setColor(Color.parseColor("#F15A22"));
        dot.setStyle(Paint.Style.FILL);
    }

    public void setOnBuildingClickListener(OnBuildingClickListener l) {
        this.listener = l;
    }

    private Collection<Building> currentBuildings() {
        if ("Downtown".equals(DataStore.get().getCurrentCampus())) {
            return DataStore.get().getDowntownCampus().values();
        }
        return DataStore.get().getMainCampus().values();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();

        canvas.drawRect(0, 0, w, h, mapBg);

        // Decorative roads
        Path p = new Path();
        p.moveTo(w * 0.05f, h * 0.05f);
        p.lineTo(w * 0.95f, h * 0.07f);
        canvas.drawPath(p, road);
        canvas.drawText("Paseo Del Norte", w * 0.18f, h * 0.05f, streetLabel);

        Path p2 = new Path();
        p2.moveTo(w * 0.05f, h * 0.45f);
        p2.lineTo(w * 0.95f, h * 0.46f);
        canvas.drawPath(p2, road);
        canvas.drawText("Fred Cook Rd", w * 0.05f, h * 0.44f, streetLabel);
        canvas.drawText("Paseo Principal", w * 0.70f, h * 0.50f, streetLabel);

        Path p3 = new Path();
        p3.moveTo(w * 0.42f, h * 0.05f);
        p3.lineTo(w * 0.42f, h * 0.95f);
        canvas.drawPath(p3, road);
        canvas.drawText("Cocke Dr", w * 0.42f, h * 0.30f, streetLabel);

        // Buildings
        for (Building b : currentBuildings()) {
            RectF r = scale(b.getBounds(), w, h);
            canvas.drawRoundRect(r, 6f, 6f, building);
            canvas.drawRoundRect(r, 6f, 6f, buildingStroke);
            drawWrappedLabel(canvas, b.getName(), r);
        }

        // "You are here" dot - near Cocke/Fred Cook on main, otherwise center top
        float dotX, dotY;
        if ("Downtown".equals(DataStore.get().getCurrentCampus())) {
            dotX = w * 0.50f; dotY = h * 0.30f;
        } else {
            dotX = w * 0.42f; dotY = h * 0.45f;
        }
        canvas.drawCircle(dotX, dotY, 14f, dot);
    }

    private RectF scale(RectF norm, int w, int h) {
        return new RectF(norm.left * w, norm.top * h,
                norm.right * w, norm.bottom * h);
    }

    private void drawWrappedLabel(Canvas c, String text, RectF box) {
        String[] words = text.split(" ");
        float textY = box.top + 32f;
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            String trial = (line.length() == 0) ? word : line + " " + word;
            if (label.measureText(trial) > box.width() - 20f && line.length() > 0) {
                c.drawText(line.toString(), box.left + 10f, textY, label);
                line = new StringBuilder(word);
                textY += 32f;
            } else {
                line = new StringBuilder(trial);
            }
        }
        if (line.length() > 0) c.drawText(line.toString(), box.left + 10f, textY, label);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP && listener != null) {
            float x = ev.getX(), y = ev.getY();
            int w = getWidth(), h = getHeight();
            for (Building b : currentBuildings()) {
                RectF r = scale(b.getBounds(), w, h);
                if (r.contains(x, y)) {
                    listener.onBuildingClick(b);
                    performClick();
                    return true;
                }
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean performClick() { return super.performClick(); }
}
