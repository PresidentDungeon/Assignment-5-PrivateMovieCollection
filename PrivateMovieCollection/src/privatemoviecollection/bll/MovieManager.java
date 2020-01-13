/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.bll;

import java.util.List;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.be.Rating;
import privatemoviecollection.dal.dbmanagers.dbdao.MovieDBDAO;
import privatemoviecollection.dal.dbmanagers.facades.MovieDalFacade;

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

    /**
     * Reads all movies from the database
     *
     * @return a List of movies
     */
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
    
    /**
     * Searches for movies with the same title or filepath as the specified movie
     * @param movie the movie to search for
     * @return true if there are identical movies, otherwise false
     */
    public boolean searchForExistingMovie(Movie movie)
    {
        return movieManager.searchForExistingMovie(movie);
    }
    
    /**
     * Searches the database for movies that contains the selected categories and meets the
     * minimum user and IMDb rating set in the textareas.The observable list containing
     * all the movies is then set to all the resulting movies.
     * @param categoryList the categories that are being searched for
     * @param isAllSelected boolean value signaling wether the "All" category is selected
     * @param rating the minimum rating that the movies must contain
     * @return a list containing all the matching movies
     */
    public List<Movie> sortByCategories(List<Category> categoryList, boolean isAllSelected, Rating rating)
    {
        String allCategories = "";
        int loopPosition = 1;
        if (!categoryList.isEmpty())
        {
            for (Category category : categoryList)
            {
                allCategories += category.getId();

                if (loopPosition != categoryList.size())
                {
                    allCategories += ", ";
                }
                loopPosition++;
            }
        } else
        {
            allCategories = "";
        }
        return movieManager.searchCategoryFilterResult(allCategories, isAllSelected, categoryList.size(), rating);
    }
    
}
