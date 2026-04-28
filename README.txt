NEO UTSA MAP APP - MVP (Java / Android Studio)
==============================================
Authors: Lisa Galvan & Sebastian Miranda

HOW TO IMPORT:
1. Open Android Studio -> File -> New -> Import Project (or "Open")
2. Select the NeoUtsaMapApp folder
3. Let Gradle sync (downloads dependencies)
4. Run on emulator or device (min SDK 24 / Android 7.0)

DEMO LOGIN:
   Email:    lisa.galvan@my.utsa.edu
   Password: utsa1234
(Any *@my.utsa.edu email + password >= 6 chars also works.)

USE CASES IMPLEMENTED:
1. SubmitSchedule  - Settings -> Select Classes -> add/remove courses
2. ViewSchedule    - Magnifying glass icon -> tap a class for details
3. ViewIndoorMap   - Map icon -> tap a building -> indoor 2D floor map
4. SearchRoutes    - Locator icon -> enter destination -> shows routes & ETAs
5. ModifyNotifications - Settings -> Notification Permissions; mail icon shows past alerts

PROJECT STRUCTURE:
- app/src/main/java/com/neoutsa/mapapp/
    - activities (one per screen, matches GUI mockups)
    - models (User, Student, Course, Schedule, Notification, Building)
    - controllers (Auth, Schedule, Map, Notification)
    - data/DataStore.java (in-memory singleton seeded with demo data)
    - views (CampusMapView, IndoorMapView - custom drawn 2D maps)
- app/src/main/res/layout/  (one XML per activity)
- app/src/main/res/values/  (colors, strings, themes)
- app/src/main/res/drawable/ (vector icons + backgrounds matching mockups)
