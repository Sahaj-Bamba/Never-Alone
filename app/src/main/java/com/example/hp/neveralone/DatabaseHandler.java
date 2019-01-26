package com.example.hp.neveralone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;



//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import java.util.ArrayList;
//import java.util.List;


    public class DatabaseHandler extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "NeverAlone";
        private static final String TABLE = "TODO";
        private static final String KEY_ID = "msg";

        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            //3rd argument to be passed is CursorFactory instance
        }

        // Creating Tables
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE + "("
                    + KEY_ID + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }

        // Upgrading database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE);

            // Create tables again
            onCreate(db);
        }

        // code to add the new contact
        void adddata(sql_data data) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_ID, data.getMsg());
            // Inserting Row
            db.insert(TABLE, null, values);
            //2nd argument is String containing nullColumnHack
            db.close(); // Closing database connection
        }

        // code to get the single contact

        // code to get all contacts in a list view
        public List<String> getAlldata() {
            List<String> contactList = new ArrayList<String>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    sql_data contact = new sql_data();
                    contact.setMsg(cursor.getString(0));
                    // Adding contact to list
                    contactList.add(contact.getMsg());
                } while (cursor.moveToNext());
            }

            // return contact list
            return contactList;
        }

        // code to update the single contact
//        public int updateContact(Contact contact) {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            ContentValues values = new ContentValues();
//            values.put(KEY_NAME, contact.getName());
//            values.put(KEY_PH_NO, contact.getPhoneNumber());
//
//            // updating row
//            return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                    new String[]{String.valueOf(contact.getID())});
//        }

        // Deleting single contact
        public void deleteContact(sql_data contact) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE, KEY_ID + " = ?",
                    new String[]{String.valueOf(contact.getMsg())});
            db.close();
        }

        // Getting contacts Count
        public int getContactsCount() {
            String countQuery = "SELECT  * FROM " + TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();

            // return count
            return cursor.getCount();
        }

    }

