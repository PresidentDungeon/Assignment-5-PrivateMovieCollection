/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.dal.dbmanagers.facades;

import java.util.List;
import privatemoviecollection.be.Movie;
import privatemoviecollection.be.Rating;

/**
 *
 * @author Bruger
 */
public interface MovieDalFacade
{

    /**
     * creates a new movie and adds it to the database
     *
     * @param movie the movie to be added
     * @return true if the movie was added
     */
    public boolean createMovie(Movie movie);

    /**
     * Reads all movies from the database
     *
     * @return a List of movies
     */
    public List<Movie> readAllMovies();

    /**
     * updates a movie object in the database.
     *
     * @param movie the movie to be updated
     * @return true if the movie was updated
     */
    public boolean updateMovie(Movie movie);

    /**
     * Deletes a movie from the database
     *
     * @param movie the movie to be deleted
     * @return true if the movie was deleted
     */
    public boolean deleteMovie(Movie movie);

    /**
     *Searches for movies that contains the selected categories and meets the
     * minimum user and IMDb rating.
     * @param allSelectedCategory the categories that are being searched for
     * @param isAllSelected boolean value signaling wether the "All" category is selected
     * @param listSize the amount of categories in the category list
     * @param rating the minimum rating that the movies must contain
     * @return a list of all the matching movies
     */
    public List<Movie> searchCategoryFilterResult(String allSelectedCategory,
            boolean isAllSelected, int listSize, Rating rating);

    /**
     * Searches for movies with the same title or filepath as the specified movie
     * @param movie the movie to search for
     * @return true if there are identical movies, otherwise false
     */
    public boolean searchForExistingMovie(Movie movie);

}
