package com.movies.movielibrary;

public class Movie {

    public int id;
    public String name;
    public String description;
    public String artists;
    public String genre;

    public Movie() {
    }

    public Movie(int id, String name, String description, String artists, String genre) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.artists = artists;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
