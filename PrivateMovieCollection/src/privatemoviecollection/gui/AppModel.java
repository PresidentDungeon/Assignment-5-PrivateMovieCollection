/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.bll.CategoryManager;
import privatemoviecollection.bll.MovieManager;

/**
 *
 * @author Bruger
 */
public class AppModel
{

    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private final MovieManager movieManager;
    private final CategoryManager categoryManager;

    public AppModel()
    {
        movieManager = new MovieManager();
        categoryManager = new CategoryManager();
        fetchMovies();
        fetchCategories();
    }

    public void fetchMovies()
    {
        movies.clear();
        movies.addAll(movieManager.getAllMovies());

    }
    
    public void fetchCategories()
    {
        categories.clear();
        categories.addAll(categoryManager.getAllCategories());

    }

    public ObservableList<Movie> getMovieList()
    {
        return movies;
    }
    
    public ObservableList<Category> getCategoryList()
    {
        return categories;
    }

}
