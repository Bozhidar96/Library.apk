package com.movies.movielibrary;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MoviesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addMovieButton;
    private Button filterMoviesButton;

    private EditText filterEditText;

    private ListView moviesListView;

    private DatabaseHelper dbHelper = new DatabaseHelper(this);

    private MoviesCursorAdapter adapter;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        addMovieButton = (Button) findViewById(R.id.addButton);
        filterMoviesButton = (Button) findViewById(R.id.filterButton);

        filterEditText = (EditText) findViewById(R.id.filterEditText);

        moviesListView = (ListView) findViewById(R.id.moviesListView);

        cursor = dbHelper.getMovies();

        adapter = new MoviesCursorAdapter(this, cursor);
        moviesListView.setAdapter(adapter);

        addMovieButton.setOnClickListener(this);
        filterMoviesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addButton) {
            startActivity(new Intent(MoviesActivity.this, AddEditMovieActivity.class));
        } else if (view.getId() == R.id.filterButton) {
            String filterBy = filterEditText.getText().toString().trim();

            if (filterBy.length() > 0) {
                cursor = dbHelper.getFilteredMovies(filterBy);
            } else {
                cursor = dbHelper.getMovies();
            }

            adapter.changeCursor(cursor);
        }
    }
}
