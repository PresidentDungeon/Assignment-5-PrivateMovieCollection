/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.bll;

import java.util.List;
import privatemoviecollection.be.Movie;
import privatemoviecollection.dal.dbmanagers.dbdao.MovieDBDAO;
import privatemoviecollection.dal.dbmanagers.facades.MovieDalFacade;
import privatemoviecollection.dal.dbmanagers.mockmanagers.MockMovieManager;

/**
 *
 * @author Bruger
 */
public class MovieManager
{

    private final MovieDalFacade movieManager;

    public MovieManager()
    {
        movieManager = new MovieDBDAO();
    }

    public List<Movie> getAllMovies()
    {
        return movieManager.readAllMovies();
    }

    /**
     * Checks the movies Id.If the movie has an Id of zero, a new movie will be added to
     * the database. If the movie already exists in the database and has an Id, the movies
     * instance variables will be updated in the database.
     *
     * @param movie the movie to be added or updated.
     * @return true if the movie was added or updated
     */
    public boolean saveMovie(Movie movie)
    {

        if (movie.getId() == 0)
        {
            return movieManager.createMovie(movie);
        } else
        {
            return movieManager.updateMovie(movie);
        }

    }

    /**
     * Deletes the selected movie from the database
     *
     * @param movie The movie to be deleted
     * @return true if the movie was deleted
     */
    public boolean deleteMovie(Movie movie)
    {
        return movieManager.deleteMovie(movie);
    }
    
    public boolean searchForExistingMovie(Movie movie)
    {
        return movieManager.searchForExistingMovie(movie);
    }

}
