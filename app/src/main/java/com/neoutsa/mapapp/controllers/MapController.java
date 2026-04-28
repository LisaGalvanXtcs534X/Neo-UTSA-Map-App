package com.neoutsa.mapapp.controllers;

import com.neoutsa.mapapp.data.DataStore;
import com.neoutsa.mapapp.models.Building;

import java.util.ArrayList;
import java.util.List;

/** MapController per UML class diagram. Handles indoor map + route lookups. */
public class MapController {

    /** Returns the building if found, otherwise null. */
    public static Building viewIndoorMap(String buildingName) {
        return DataStore.get().findBuilding(buildingName);
    }

    /** Generates 3 dummy routes to a destination. Each entry: [label, minutes]. */
    public static List<RouteOption> searchRoutes(String currentLocation, String destination) {
        List<RouteOption> out = new ArrayList<>();
        if (destination == null || destination.trim().isEmpty()) return out;

        int base = 18 + Math.abs(destination.hashCode() % 6); // pseudo distance
        out.add(new RouteOption("Route A (fastest)", base, true));
        out.add(new RouteOption("Route B", base + 1, false));
        out.add(new RouteOption("Route C", base + 3, false));
        return out;
    }

    public static class RouteOption {
        public final String label;
        public final int minutes;
        public final boolean fastest;
        public RouteOption(String label, int minutes, boolean fastest) {
            this.label = label;
            this.minutes = minutes;
            this.fastest = fastest;
        }
    }
}
