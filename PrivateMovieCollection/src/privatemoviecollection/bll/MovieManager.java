/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.bll;

import java.util.List;
import privatemoviecollection.be.Movie;
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
        movieManager = new MockMovieManager();
    }

    public List<Movie> getAllMovies()
    {
        return movieManager.readAllMovies();
    }
    
}
