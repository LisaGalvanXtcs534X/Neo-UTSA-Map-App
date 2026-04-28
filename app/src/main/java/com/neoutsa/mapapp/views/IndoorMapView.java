package com.neoutsa.mapapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.neoutsa.mapapp.models.Building;

/** 2D indoor floor plan, gray theme matching the GUI mockup. */
public class IndoorMapView extends View {

    private Building building;
    private String highlightCode; // optional - searched room

    private final Paint floor = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint room = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint roomStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint roomHighlight = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint code = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint label = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint dot = new Paint(Paint.ANTI_ALIAS_FLAG);

    public IndoorMapView(Context c) { super(c); init(); }
    public IndoorMapView(Context c, @Nullable AttributeSet a) { super(c, a); init(); }

    private void init() {
        floor.setColor(Color.parseColor("#C5C8CB"));
        floor.setStyle(Paint.Style.FILL);

        room.setColor(Color.parseColor("#B3B8BD"));
        room.setStyle(Paint.Style.FILL);

        roomStroke.setColor(Color.parseColor("#8E939A"));
        roomStroke.setStyle(Paint.Style.STROKE);
        roomStroke.setStrokeWidth(2f);

        roomHighlight.setColor(Color.parseColor("#FFD7B5"));
        roomHighlight.setStyle(Paint.Style.FILL);

        code.setColor(Color.parseColor("#0F2547"));
        code.setTextSize(22f);
        code.setFakeBoldText(true);

        label.setColor(Color.parseColor("#0F2547"));
        label.setTextSize(20f);

        dot.setColor(Color.parseColor("#F15A22"));
        dot.setStyle(Paint.Style.FILL);
    }

    public void setBuilding(Building b) {
        this.building = b;
        invalidate();
    }

    public void highlightRoom(String code) {
        this.highlightCode = code;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth(), h = getHeight();
        canvas.drawRect(0, 0, w, h, floor);

        if (building == null) return;

        for (Building.IndoorRoom ir : building.getRooms()) {
            RectF r = new RectF(ir.bounds.left * w, ir.bounds.top * h,
                    ir.bounds.right * w, ir.bounds.bottom * h);
            boolean hl = highlightCode != null && highlightCode.equalsIgnoreCase(ir.code);
            canvas.drawRect(r, hl ? roomHighlight : room);
            canvas.drawRect(r, roomStroke);
            canvas.drawText("(" + ir.code + ")", r.left + 8f, r.top + 28f, code);
            // wrapped label
            drawWrapped(canvas, ir.label, r.left + 8f, r.top + 54f, r.width() - 16f);
        }

        // user dot near top-left of biggest room (Bookstore in mockup)
        if (!building.getRooms().isEmpty()) {
            Building.IndoorRoom main = building.getRooms().get(0);
            float cx = (main.bounds.left + 0.10f) * w;
            float cy = (main.bounds.top + 0.10f) * h;
            canvas.drawCircle(cx, cy, 12f, dot);
        }
    }

    private void drawWrapped(Canvas c, String text, float x, float y, float maxW) {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        float curY = y;
        for (String word : words) {
            String trial = (line.length() == 0) ? word : line + " " + word;
            if (label.measureText(trial) > maxW && line.length() > 0) {
                c.drawText(line.toString(), x, curY, label);
                line = new StringBuilder(word);
                curY += 22f;
            } else {
                line = new StringBuilder(trial);
            }
        }
        if (line.length() > 0) c.drawText(line.toString(), x, curY, label);
    }
}
