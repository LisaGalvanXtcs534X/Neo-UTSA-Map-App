package com.neoutsa.mapapp.models;

import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a campus building drawn on the 2D outdoor map.
 * Coordinates are normalized (0..1) so the view can scale.
 */
public class Building {
    private final String name;
    private final String shortCode;       // e.g. "NPB", "SU"
    private final String campus;          // "Main" / "Downtown"
    private final RectF bounds;           // normalized 0..1
    private final List<IndoorRoom> rooms = new ArrayList<>();

    public Building(String name, String shortCode, String campus,
                    float left, float top, float right, float bottom) {
        this.name = name;
        this.shortCode = shortCode;
        this.campus = campus;
        this.bounds = new RectF(left, top, right, bottom);
    }

    public String getName() { return name; }
    public String getShortCode() { return shortCode; }
    public String getCampus() { return campus; }
    public RectF getBounds() { return bounds; }
    public List<IndoorRoom> getRooms() { return rooms; }

    public void addRoom(IndoorRoom r) { rooms.add(r); }

    public static class IndoorRoom {
        public final String code;     // "1.02.02"
        public final String label;    // "Bookstore"
        public final RectF bounds;    // normalized 0..1 inside building view
        public IndoorRoom(String code, String label,
                          float left, float top, float right, float bottom) {
            this.code = code;
            this.label = label;
            this.bounds = new RectF(left, top, right, bottom);
        }
    }
}
