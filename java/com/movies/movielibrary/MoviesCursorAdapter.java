package com.movies.movielibrary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MoviesCursorAdapter extends CursorAdapter {

    private DatabaseHelper dbHelper;

    private Cursor cursor;

    public MoviesCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);

        this.cursor = cursor;
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.movie_list_row, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView artistsTextView = (TextView) view.findViewById(R.id.artistsTextView);
        TextView genreTextView = (TextView) view.findViewById(R.id.genreTextView);

        nameTextView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MOVIE_NAME)));
        descriptionTextView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MOVIE_DESCRIPTION)));
        artistsTextView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MOVIE_ARTISTS)));
        genreTextView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MOVIE_GENRE)));

        ImageView editImage = (ImageView) view.findViewById(R.id.editImageView);
        ImageView deleteImage = (ImageView) view.findViewById(R.id.deleteImageView);

        final int position = cursor.getPosition();

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCursor().moveToPosition(position);

                Intent intent = new Intent(context, AddEditMovieActivity.class);
                intent.putExtra("id", getCursor().getInt(getCursor().getColumnIndex(DatabaseHelper.MOVIE_ID)));

                context.startActivity(intent);
            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCursor().moveToPosition(position);

                dbHelper.deleteMovie(getCursor().getInt(getCursor().getColumnIndex(DatabaseHelper.MOVIE_ID)));

                setCursor(dbHelper.getMovies());
            }
        });
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        changeCursor(cursor);
    }
}

