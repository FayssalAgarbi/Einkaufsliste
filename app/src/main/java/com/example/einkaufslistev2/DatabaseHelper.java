package com.example.einkaufslistev2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;



public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "einkaufsliste";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";
    private static final String COL3 = "color";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT, " + COL3 + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.d(TAG, "dropTable: Dropping the table "+TABLE_NAME+ " and recreating it");
    }
    public boolean addData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        contentValues.put(COL3, 0);
        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        return result != -1;
    }

    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, "getData: querying the entire Table");
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param name
     * @return
     */
    // vulnerable to SQL injections, needs to change!
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, "getItemID: Getting the ItemID of "+ name);
        return data;
    }
    public Cursor getColorState(int position){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL3 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + position + "'";
        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, "getColorState: Getting the Current Color ID of the view at position "+position);
        return data;

    }
    /**
     * This is what makes changing the colors work
     * It increments the Color ID such that the Adapter will show a different color
     * However there is probably quite some  room for improvement
     * @param oldColorID
     * @param id
      */

    public void incrementColorID(int oldColorID, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        if(oldColorID != 3) {
            int newColorID = oldColorID+1;
            query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                    " = '" + newColorID + "' WHERE " + COL1 + " = '" + id + "'" +
                    " AND " + COL3 + " = '" + oldColorID + "'";
        }
        else{
            query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                    " = '" + 0 + "' WHERE " + COL1 + " = '" + id + "'" +
                    " AND " + COL3 + " = '" + oldColorID + "'";
        }
        Log.d(TAG, "incrementColorID: incrementing the Color with the ID "+ oldColorID+ " at the item ID "+id);
        db.execSQL(query);
    }
    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */
    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    /**
     * Delete from database
     * @param id
     * @param name
     */
    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
}
























