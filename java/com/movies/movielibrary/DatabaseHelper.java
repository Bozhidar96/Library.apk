package com.movies.movielibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = "DBException";

    private SQLiteDatabase db;

    public static final String DB_NAME = "movies.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_MOVIES = "players";
    public static final String MOVIE_ID = "_id";
    public static final String MOVIE_NAME = "movie_name";
    public static final String MOVIE_DESCRIPTION = "movie_description";
    public static final String MOVIE_ARTISTS = "movie_artists";
    public static final String MOVIE_GENRE = "movie_genre";

    public static final String CREATE_TABLE_MOVIES =
            "CREATE TABLE '" + TABLE_MOVIES + "'(" +
                    "'" + MOVIE_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "'" + MOVIE_NAME + "' TEXT NOT NULL, " +
                    "'" + MOVIE_DESCRIPTION + "' TEXT, " +
                    "'" + MOVIE_ARTISTS + "' TEXT NOT NULL, " +
                    "'" + MOVIE_GENRE + "' TEXT NOT NULL)";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(sqLiteDatabase);
    }

    public void insertMovie(Movie movie) {
        try {
            db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();

            cv.put(MOVIE_NAME, movie.getName());
            cv.put(MOVIE_DESCRIPTION, movie.getDescription());
            cv.put(MOVIE_GENRE, movie.getGenre());
            cv.put(MOVIE_ARTISTS, movie.getArtists());

            db.insertOrThrow(TABLE_MOVIES, null, cv);
        } catch (SQLException e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (db != null)
                db.close();
        }
    }

    public Cursor getMovies() {
        try {
            db = this.getReadableDatabase();

            String query = "SELECT * FROM " + TABLE_MOVIES;

            return db.rawQuery(query, null);
        } catch (SQLException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return null;
    }

    public Cursor getFilteredMovies(String filterBy) {
        try {
            db = this.getReadableDatabase();

            String query = "SELECT * FROM " + TABLE_MOVIES + " WHERE " + MOVIE_GENRE + " LIKE " + "'%" + filterBy + "%'";

            return db.rawQuery(query, null);
        } catch (SQLException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return null;
    }

    public void deleteMovie(int id) {
        db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, MOVIE_ID + " = ?", new String[]{String.valueOf(id)});

        db.close();
    }


    public Movie getMovie(int id) {
        Movie movie = null;

        db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_MOVIES
                + " WHERE " + MOVIE_ID + " = " + id;

        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            movie = new Movie();

            movie.setId(id);

            movie.setName(c.getString
                    (c.getColumnIndex(MOVIE_NAME)));
            movie.setDescription(c.getString
                    (c.getColumnIndex(MOVIE_DESCRIPTION)));
            movie.setArtists(c.getString
                    (c.getColumnIndex(MOVIE_ARTISTS)));
            movie.setGenre(c.getString
                    (c.getColumnIndex(MOVIE_GENRE)));
        }
        return movie;
    }

    public void updateMovie(Movie movie) {
        try {
            db = getWritableDatabase();

            ContentValues cv = new ContentValues();

            cv.put(MOVIE_NAME, movie.getName());
            cv.put(MOVIE_DESCRIPTION, movie.getDescription());
            cv.put(MOVIE_GENRE, movie.getGenre());
            cv.put(MOVIE_ARTISTS, movie.getArtists());

            db.update(TABLE_MOVIES, cv, MOVIE_ID + " = " + movie.getId(), null);

        } catch (SQLException e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (db != null)
                db.close();
        }

    }
}