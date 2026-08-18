package com.xyz.ridebooking.observer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RideBooking.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE rides (rideId TEXT PRIMARY KEY, riderName TEXT, pickup TEXT, destination TEXT, rideType TEXT, category TEXT, status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS rides");
        onCreate(db);
    }

    public void insertRide(String rideId, String riderName, String pickup, String destination, String rideType, String category, String status) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("rideId", rideId);
        values.put("riderName", riderName);
        values.put("pickup", pickup);
        values.put("destination", destination);
        values.put("rideType", rideType);
        values.put("category", category);
        values.put("status", status);

        db.insert("rides", null, values);
        db.close();
    }

    public boolean rideExists(String rideId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT rideId FROM rides WHERE rideId = ?", new String[]{rideId});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }
    

    public void updateStatus(String rideId, String status) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("status", status);

        db.update("rides", values, "rideId = ?", new String[]{rideId});
        db.close();
    }

    public void clearDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("rides", null, null);
        db.close();
    }

    public String getAllRides() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM rides", null);

        StringBuilder result = new StringBuilder();

        if(cursor.getCount() == 0) {
            result.append("Database is empty.");
        } else {
            while(cursor.moveToNext()) {
                result.append("Ride ID: ").append(cursor.getString(0)).append("\n");
                result.append("Rider: ").append(cursor.getString(1)).append("\n");
                result.append("Pickup: ").append(cursor.getString(2)).append("\n");
                result.append("Destination: ").append(cursor.getString(3)).append("\n");
                result.append("Ride Type: ").append(cursor.getString(4)).append("\n");
                result.append("Category: ").append(cursor.getString(5)).append("\n");
                result.append("Status: ").append(cursor.getString(6)).append("\n");
                result.append("-------------------------\n");
            }
        }

        cursor.close();
        db.close();

        return result.toString();
    }

    public String getRide(String rideId) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM rides WHERE rideId = ?", new String[]{rideId});

        String result = "";

        if(cursor.moveToFirst()) {
            result = "Ride ID: " + cursor.getString(0)
                    + "\nRider: " + cursor.getString(1)
                    + "\nPickup: " + cursor.getString(2)
                    + "\nDestination: " + cursor.getString(3)
                    + "\nRide Type: " + cursor.getString(4)
                    + "\nCategory: " + cursor.getString(5)
                    + "\nStatus: " + cursor.getString(6);
        }

        cursor.close();
        db.close();

        return result;
    }
}