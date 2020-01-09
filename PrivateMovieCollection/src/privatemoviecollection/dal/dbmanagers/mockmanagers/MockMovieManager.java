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
        Rating rating = new Rating();
        Category action = new Category("Action");
        Category drama = new Category("Drama");

        ArrayList<Category> batmanCategory = new ArrayList<>();
        batmanCategory.add(action);
        batmanCategory.add(drama);

        Movie m1 = new Movie("Batman the dark knight",200,"test");
        m1.setId(4);
        m1.getCategories().add(drama);
        m1.getCategories().add(action);

        movies.add(m1);

    }

    /**
     * adds a movie to a list of movies
     *
     * @param movie the movie that you want to add to the list
     * @return boolean to test if the movie was succesfully added
     */
    @Override
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
    @Override
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
    @Override
    public boolean updateMovie(Movie movie)
    {
        for (Movie m : movies)
        {
            if (m.getId() == movie.getId())
            {
                m.setTitle(movie.getTitle());
                m.setSeconds(movie.getSeconds());
                m.setYear(movie.getYear());
                m.setFilePath(movie.getFilePath());
                m.setRating(movie.getRating());
                m.setCategories(movie.getCategories());
                m.setImageLink(movie.getImageLink());
                m.setIMDbLink(movie.getIMDbLink());
                m.setSummaryText(movie.getSummaryText());
                        
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
    @Override
    public boolean deleteMovie(Movie movie)
    {
        return movies.remove(movie);
    }

    /**
     * Returns a list of movies with title or fully or partially matching search term.
     *
     * @param searchTerm
     * @return list of matching songs
     */
    @Override
    public List<Movie> getSearchResult(String searchTerm)
    {
        List<Movie> searchMatches = new ArrayList<>();
        for (Movie movie : movies)
        {
            if (movie.getTitle().contains(searchTerm))
            {
                searchMatches.add(movie);
            }
        }
        return searchMatches;
    }

}
