/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.dal.dbmanagers.facades;

import java.util.List;
import privatemoviecollection.be.Movie;

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
     * updates a movie variable
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
     * Returns a list of movies with title or fully or partially matching search term.
     *
     * @param searchTerm
     * @return list of matching songs
     */
    public List<Movie> getSearchResult(String searchTerm);
    
    public boolean searchForExistingMovie(Movie movie);
}
