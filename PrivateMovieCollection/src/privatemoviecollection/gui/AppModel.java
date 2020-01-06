/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemoviecollection.be.Movie;
import privatemoviecollection.bll.MovieManager;

/**
 *
 * @author Bruger
 */
public class AppModel
{

    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private final MovieManager movieManager;

    public AppModel()
    {
        movieManager = new MovieManager();
        fetchMovies();
    }

    public void fetchMovies()
    {
        movies.clear();
        movies.addAll(movieManager.getAllMovies());

    }

    public ObservableList<Movie> getMovieList()
    {
        return movies;
    }

}
