/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.dal.dbmanagers.dbdao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.dal.DBAccess.DBSettings;
import privatemoviecollection.dal.dbmanagers.facades.MovieDalFacade;

/**
 *
 * @author Bruger
 */
public class MovieDBDAO implements MovieDalFacade
{

    private DBSettings dbs;

    public MovieDBDAO()
    {
        try
        {
            dbs = new DBSettings();
        } catch (IOException e)
        {

        }
    }

    /**
     * Creates the specified movie in the database.
     *
     * @param movie the movie to be added
     * @return True if creation performed, else false
     */
    @Override
    public boolean createMovie(Movie movie)
    {
        try ( Connection con = dbs.getConnection())
        {
            String sql = "INSERT INTO Movies (Title, Seconds, Year, FilePath, IMDBLink, "
                    + "LastView, PersonalRating, IMDBRating, SummaryText, imageLink) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, movie.getTitle());
            stmt.setInt(2, movie.getSeconds());
            stmt.setInt(3, movie.getYear());
            stmt.setString(4, movie.getFilePath());
            stmt.setString(5, movie.getIMDbLink());
            stmt.setString(6, movie.getLastView().toString());
            stmt.setInt(7, movie.getRating().getUserRating());
            stmt.setFloat(8, movie.getRating().getIMDBRating());
            stmt.setString(9, movie.getSummaryText());
            stmt.setString(10, movie.getImageLink());

            int updatedRows = stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next())
            {
                movie.setId(generatedKeys.getInt(1));
            }

            addCategoriesToMovie(movie);

            return updatedRows > 0;

        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Retrieves all movies from the database and puts them in a list.
     *
     * @return List of all movies
     */
    @Override
    public List<Movie> readAllMovies()
    {
        try ( Connection con = dbs.getConnection())
        {
            String sql = "SELECT * FROM Movies;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Movie> movies = new ArrayList<>();
            while (rs.next())
            {

                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                int seconds = rs.getInt("Seconds");
                int year = rs.getInt("Year");
                String filePath = rs.getString("FilePath");
                String IMDBLink = rs.getString("IMDBLink");
                String lastView = rs.getString("LastView");
                int personalRating = rs.getInt("PersonalRating");
                float IMDBRating = rs.getFloat("IMDBRating");
                String summaryText = rs.getString("SummaryText");
                String imageLink = rs.getString("ImageLink");

                Movie m = new Movie(title, seconds, year, filePath);
                m.setId(id);
                m.setIMDbLink(IMDBLink);
                m.setLastView(LocalDate.parse(lastView));
                m.getRating().setUserRating(personalRating);
                m.getRating().setIMDBRating(IMDBRating);
                m.setSummaryText(summaryText);
                m.setImageLink(imageLink);
                m.setCategories(readAllMovieCategories(m));
                movies.add(m);
            }
            return movies;
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Updates a movie with matching id in the database.
     *
     * @param movie
     * @return True if update performed, else false
     */
    @Override
    public boolean updateMovie(Movie movie)
    {
        try ( Connection con = dbs.getConnection())
        {
            String sql = "UPDATE Movies SET Title = ?, Seconds = ?, Year = ?, FilePath = ?, "
                    + "IMDBLink = ?, LastView = ?, PersonalRating = ?, IMDBRating = ?,"
                    + "SummaryText = ?, ImageLink = ? WHERE id = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, movie.getTitle());
            stmt.setInt(2, movie.getSeconds());
            stmt.setInt(3, movie.getYear());
            stmt.setString(4, movie.getFilePath());
            stmt.setString(5, movie.getIMDbLink());
            stmt.setString(6, movie.getLastView().toString());
            stmt.setInt(7, movie.getRating().getUserRating());
            stmt.setFloat(8, movie.getRating().getIMDBRating());
            stmt.setString(9, movie.getSummaryText());
            stmt.setString(10, movie.getImageLink());
            stmt.setInt(11, movie.getId());
            int updatedRows = stmt.executeUpdate();

            removeCategoriesFromMovie(movie);
            addCategoriesToMovie(movie);

            return updatedRows > 0;
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Deletes a movie with matching id from the database.
     *
     * @param movie
     * @return True if deletion performed, else false
     */
    @Override
    public boolean deleteMovie(Movie movie)
    {
        try ( Connection con = dbs.getConnection())
        {
            String sql = "DELETE FROM Movies WHERE id = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, movie.getId());
            int updatedRows = stmt.executeUpdate();
            removeCategoriesFromMovie(movie);

            return updatedRows > 0;
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Adds the movie categories to the database
     *
     * @param movie the movie the category should be added to
     * @return true if category was added, else false
     */
    public boolean addCategoriesToMovie(Movie movie)
    {

        try ( Connection con = dbs.getConnection())
        {

            for (Category category : movie.getCategories())
            {
                String sql = "INSERT INTO CatMovies (MovieID, CategoryID) VALUES (?,?);";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, movie.getId());
                stmt.setInt(2, category.getId());
                stmt.executeUpdate();
            }

            return true;

        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Removes all categories from the movie.
     *
     * @param movie the movie that the categories should be removed from
     * @return true if song was removed, else false
     */
    public boolean removeCategoriesFromMovie(Movie movie)
    {

        try ( Connection con = dbs.getConnection())
        {
            String sql = "DELETE FROM CatMovies WHERE MovieID = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, movie.getId());

            int updatedRows = stmt.executeUpdate();

            return updatedRows > 0;

        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Retrieves all the categories in the specified movie and puts them in a list
     *
     * @param movie the movie the categories should be read from
     * @return a list of all the categories in the specified movie
     */
    public ArrayList<Category> readAllMovieCategories(Movie movie)
    {
        try ( Connection con = dbs.getConnection())
        {
            String sql = "SELECT * FROM CatMovies FULL OUTER JOIN Categories ON "
                    + "CatMovies.CategoryID = Categories.Id WHERE MovieID = ? "
                    + "ORDER BY Categories.Name;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, movie.getId());
            ResultSet rs = stmt.executeQuery();
            ArrayList<Category> categories = new ArrayList<>();
            while (rs.next())
            {
                int Id = rs.getInt("Id");
                String name = rs.getString("Name");
                Category c = new Category(name);
                c.setId(Id);

                categories.add(c);
                categories.sort((c1, c2) ->
                {
                    return c1.getName().compareToIgnoreCase(c2.getName());
                });
            }
            return categories;
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean searchForExistingMovie(Movie movie)
    {
        try ( Connection con = dbs.getConnection())
        {

            if (movie.getId() == 0)
            {
                String sql = "SELECT * FROM Movies WHERE Title = ? OR FilePath = ?;";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, movie.getTitle());
                stmt.setString(2, movie.getFilePath());
                ResultSet rs = stmt.executeQuery();
                return rs.next();
            } else
            {
                String sql = "SELECT * FROM Movies WHERE Title = ? OR FilePath = ?;";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, movie.getTitle());
                stmt.setString(2, movie.getFilePath());
                ResultSet rs = stmt.executeQuery();

                if (rs.next())
                {
                    int Id = rs.getInt("Id");
                    if (Id == movie.getId())
                    {
                        return rs.next();
                    } else
                    {
                        return true;
                    }
                } else
                {
                    return false;
                }
            }
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public List<Movie> getSearchResult(String searchTerm)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
