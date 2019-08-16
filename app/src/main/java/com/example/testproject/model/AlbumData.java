package com.example.testproject.model;

import org.json.JSONObject;

public class AlbumData {

    public static final String TABLE_NAME = "album";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ALBUM_ID = "albumId";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_THUMBNAIL_URL = "thumbnailUrl";

    public int id;
    public int albumId;
    public String title;
    public String thumbnailUrl;


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ALBUM_ID + " INTEGER,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_THUMBNAIL_URL + " TEXT"
                    + ")";


    public AlbumData() {
    }

    public AlbumData(int id, int albumId, String title, String thumbnailUrl) {
        this.id = id;
        this.albumId = albumId;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }

    public AlbumData(JSONObject jsonObject) {
        this.albumId = jsonObject.optInt("albumId", 0);
        this.title = jsonObject.optString("title");
        this.thumbnailUrl = jsonObject.optString("thumbnailUrl");
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
