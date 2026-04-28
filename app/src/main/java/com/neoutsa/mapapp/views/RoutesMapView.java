package com.neoutsa.mapapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/** Draws the 3-route visualization seen in the GUI mockup. */
public class RoutesMapView extends View {

    private final Paint bg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint road = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint routeAlt = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint routeMain = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pinOrange = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pinDot = new Paint(Paint.ANTI_ALIAS_FLAG);

    public RoutesMapView(Context c) { super(c); init(); }
    public RoutesMapView(Context c, @Nullable AttributeSet a) { super(c, a); init(); }

    private void init() {
        bg.setColor(Color.parseColor("#F2F4F6"));
        road.setColor(Color.parseColor("#C8CFD6"));
        road.setStyle(Paint.Style.STROKE);
        road.setStrokeWidth(14f);
        road.setStrokeCap(Paint.Cap.ROUND);

        routeAlt.setColor(Color.parseColor("#7FB3F0"));
        routeAlt.setStyle(Paint.Style.STROKE);
        routeAlt.setStrokeWidth(16f);
        routeAlt.setStrokeCap(Paint.Cap.ROUND);

        routeMain.setColor(Color.parseColor("#2E80F0"));
        routeMain.setStyle(Paint.Style.STROKE);
        routeMain.setStrokeWidth(20f);
        routeMain.setStrokeCap(Paint.Cap.ROUND);

        pinOrange.setColor(Color.parseColor("#F15A22"));
        pinOrange.setStyle(Paint.Style.FILL);

        pinDot.setColor(Color.parseColor("#F15A22"));
        pinDot.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth(), h = getHeight();
        canvas.drawRect(0, 0, w, h, bg);

        float startX = w * 0.40f, startY = h * 0.10f;
        float endX = w * 0.55f, endY = h * 0.85f;

        // Background road grid
        Path grid = new Path();
        grid.moveTo(w * 0.10f, h * 0.30f); grid.lineTo(w * 0.90f, h * 0.30f);
        grid.moveTo(w * 0.10f, h * 0.60f); grid.lineTo(w * 0.90f, h * 0.60f);
        grid.moveTo(w * 0.30f, h * 0.05f); grid.lineTo(w * 0.30f, h * 0.95f);
        grid.moveTo(w * 0.70f, h * 0.05f); grid.lineTo(w * 0.70f, h * 0.95f);
        canvas.drawPath(grid, road);

        // Route C (alt right)
        Path c = new Path();
        c.moveTo(startX, startY);
        c.lineTo(w * 0.70f, startY);
        c.lineTo(w * 0.70f, h * 0.60f);
        c.lineTo(endX, endY);
        canvas.drawPath(c, routeAlt);

        // Route A (alt left)
        Path a = new Path();
        a.moveTo(startX, startY);
        a.lineTo(w * 0.20f, startY);
        a.lineTo(w * 0.20f, h * 0.45f);
        a.lineTo(w * 0.30f, h * 0.60f);
        a.lineTo(endX, endY);
        canvas.drawPath(a, routeAlt);

        // Route B (main, fastest, dark blue)
        Path b = new Path();
        b.moveTo(startX, startY);
        b.cubicTo(w * 0.30f, h * 0.30f, w * 0.30f, h * 0.55f, w * 0.40f, h * 0.55f);
        b.cubicTo(w * 0.50f, h * 0.55f, w * 0.55f, h * 0.75f, endX, endY);
        canvas.drawPath(b, routeMain);

        // Start point (orange dot)
        canvas.drawCircle(startX, startY, 14f, pinDot);

        // End pin
        Path pin = new Path();
        pin.moveTo(endX, endY);
        pin.lineTo(endX - 18f, endY - 28f);
        pin.lineTo(endX + 18f, endY - 28f);
        pin.close();
        canvas.drawPath(pin, pinOrange);
        canvas.drawCircle(endX, endY - 36f, 18f, pinOrange);
    }
}
