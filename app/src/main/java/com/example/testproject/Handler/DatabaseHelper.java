package com.example.testproject.Handler;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.testproject.model.AlbumData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siddiquia4 on 9/13/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper mInstance;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "album_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private Context mCxt;

    public static DatabaseHelper getInstance(Context ctx) {

        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AlbumData.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AlbumData.TABLE_NAME);
        onCreate(db);
    }


    public long insertAlbumData(String albumData) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AlbumData.COLUMN_ALBUM_ID, albumData);
        values.put(AlbumData.COLUMN_TITLE, albumData);
        values.put(AlbumData.COLUMN_THUMBNAIL_URL, albumData);

        long id = db.insert(AlbumData.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public boolean saveAlbumData(AlbumData albumData) {
        ContentValues cv = new ContentValues();
        cv.put(AlbumData.COLUMN_ALBUM_ID, albumData.getAlbumId());
        cv.put(AlbumData.COLUMN_TITLE, albumData.getTitle());
        cv.put(AlbumData.COLUMN_THUMBNAIL_URL, albumData.getThumbnailUrl());

        long count = this.getWritableDatabase().insert(AlbumData.TABLE_NAME, null, cv);

        return count > 0;
    }


    public AlbumData getAlbumData(long id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(AlbumData.TABLE_NAME,
                new String[]{AlbumData.COLUMN_ID, AlbumData.COLUMN_ALBUM_ID, AlbumData.COLUMN_TITLE, AlbumData.COLUMN_THUMBNAIL_URL},
                AlbumData.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        AlbumData albumData = new AlbumData(
                cursor.getInt(cursor.getColumnIndex(AlbumData.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(AlbumData.COLUMN_ALBUM_ID)),
                cursor.getString(cursor.getColumnIndex(AlbumData.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(AlbumData.COLUMN_THUMBNAIL_URL)));

        cursor.close();

        return albumData;
    }


    public List<AlbumData> getAllAlbums() {
        List<AlbumData> dataList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + AlbumData.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                AlbumData data = new AlbumData();
                data.setId(cursor.getInt(cursor.getColumnIndex(AlbumData.COLUMN_ID)));
                data.setAlbumId(cursor.getInt(cursor.getColumnIndex(AlbumData.COLUMN_ALBUM_ID)));
                data.setTitle(cursor.getString(cursor.getColumnIndex(AlbumData.COLUMN_TITLE)));
                data.setThumbnailUrl(cursor.getString(cursor.getColumnIndex(AlbumData.COLUMN_THUMBNAIL_URL)));

                dataList.add(data);
            } while (cursor.moveToNext());
        }

        db.close();

        return dataList;

    }

    public int getAlbumCount() {
        String countQuery = "SELECT  * FROM " + AlbumData.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }


}
