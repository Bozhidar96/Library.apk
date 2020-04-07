package com.movies.movielibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEditMovieActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText artistsEditText;
    private EditText genreEditText;

    private Button okButton;
    private Button cancelButton;

    private DatabaseHelper dbHelper = new DatabaseHelper(this);

    private Movie movie = null;

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_movie);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        artistsEditText = (EditText) findViewById(R.id.artistsEditText);
        genreEditText = (EditText) findViewById(R.id.genreEditText);

        okButton = (Button) findViewById(R.id.okButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        id = getIntent().getIntExtra("id", -1);

        if (id > 0) {
            movie = dbHelper.getMovie(id);

            nameEditText.setText(movie.getName());
            descriptionEditText.setText(movie.getDescription());
            artistsEditText.setText(movie.getArtists());
            genreEditText.setText(movie.getGenre());
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.okButton) {
            String name = nameEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String artists = artistsEditText.getText().toString();
            String genre = genreEditText.getText().toString();

            if (movie == null) {
                movie = new Movie(id, name, description, artists, genre);
            } else {
                movie.setName(name);
                movie.setDescription(description);
                movie.setGenre(genre);
                movie.setArtists(artists);
                movie.setDuree(temps);
            }

            if (id > 0) {
                dbHelper.updateMovie(movie);
            } else {
                dbHelper.insertMovie(movie);
            }

            startActivity(new Intent(this, MoviesActivity.class));
        } else if (view.getId() == R.id.cancelButton) {
            startActivity(new Intent(this, MoviesActivity.class));
        }
    }
}
