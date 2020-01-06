/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.dal.dbmanagers.mockmanagers;

import java.util.ArrayList;
import java.util.List;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.be.Rating;
import privatemoviecollection.dal.dbmanagers.facades.MovieDalFacade;

/**
 *
 * @author Bruger
 */
public class MockMovieManager implements MovieDalFacade
{

    ArrayList<Movie> movies = new ArrayList<>();

    public MockMovieManager()
    {
        Rating rating = new Rating(7);
        Category action = new Category("Action");
        Category drama = new Category("Drama");

        ArrayList<Category> batmanCategory = new ArrayList<>();
        batmanCategory.add(action);
        batmanCategory.add(drama);

        Movie m1 = new Movie("Batman the dark knight", 200, 1999, "test", rating, batmanCategory);

        movies.add(m1);

    }

    /**
     * adds a movie to a list of movies
     *
     * @param movie the movie that you want to add to the list
     * @return boolean to test if the movie is succesfully added
     */
    public boolean createMovie(Movie movie)
    {
        if (movie != null)
        {
            movies.add(movie);
            return true;
        }
        return false;
    }

    /**
     * returns the movies list
     *
     */
    public List<Movie> readAllMovies()
    {
        return movies;
    }

    /**
     * Attempts to find a movie in the list with similar id as the input movie, then sets
     * the other parameters of the movie in the list to that of the input movie
     *
     * @return true if a movie was updated, else false
     */
    public boolean updateMovie(Movie movie)
    {
        for (Movie m : movies)
        {
            if (m.getId() == movie.getId())
            {
                m.setId(movie.getId());
                m.setTitle(movie.getTitle());
                m.setLength(movie.getLength());
                m.setYear(movie.getYear());
                m.setFilePath(movie.getFilePath());
                m.setRating(movie.getRating());
                m.setCategories(movie.getCategories());
                return true;
            }

        }
        return false;
    }

    /**
     * Deletes the specified movie from the list of movies.
     *
     * @return true if the movie was removed, else false
     */
    public boolean deleteMovie(Movie movie)
    {
        return movies.remove(movie);
    }
}