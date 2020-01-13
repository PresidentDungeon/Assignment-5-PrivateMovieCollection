/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui;

import java.awt.Desktop;
import java.io.File;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.be.Rating;
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

    /**
     * Fetches the movies from the database and adds them to the ObservableList.
     */
    public void fetchMovies()
    {
        movies.clear();
        movies.addAll(movieManager.getAllMovies());
    }

    /**
     * Fetches the categories from the database and adds them to the ObservableList.
     */
    public void fetchCategories()
    {
        categories.clear();
        categories.addAll(categoryManager.getAllCategories());
    }

    /**
     * Returns the Movie observablelist.
     *
     * @return the Movie observablelist
     */
    public ObservableList<Movie> getMovieList()
    {
        return movies;
    }

    /**
     * Returns the Category observablelist.
     *
     * @return the Category observablelist
     */
    public ObservableList<Category> getCategoryList()
    {
        return categories;
    }

    /**
     * Saves the movie to the database.
     *
     * @param movie the movie to be saved
     * @return true if saved, otherwise false
     */
    public boolean saveMovie(Movie movie)
    {
        movieManager.saveMovie(movie);
        fetchMovies();
        return true;
    }

    /**
     * Deletes the movie from the database. A confirmation alert is shown to the user, 
     * asking if they really want to delete the specified movies. If the user agrees and
     * the movie is deleted, the original movie file also gets deleted.
     *
     * @param movie the movie to be deleted
     * @return true if deleted, otherwise false
     */
    public boolean DeleteMovie(Movie movie)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Confirm delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete "
                + movie.getTitle() + " (" + movie.getYear() + ")?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            //Following is uncommented, as it deletes the movie file
//            File file = new File(movie.getFilePath());
//            Desktop.getDesktop().moveToTrash(file);
            movieManager.deleteMovie(movie);
            fetchMovies();
            return true;
        } else
        {
            alert.close();
            return false;
        }
    }

    /**
     * Saves the category to the database. If the category's name is empty or a category
     * with the same name already exists, the user is warned and the save method is
     * cancelled.
     *
     * @param category the category to be saved
     * @return true if saved, otherwise false
     */
    public boolean saveCategory(Category category)
    {
        boolean canCreateCategory = true;
        TextInputDialog td = new TextInputDialog();
        td.setTitle("Confirmation");
        td.setHeaderText(null);
        td.setContentText("Please enter the category name");
        Optional<String> categoryName = td.showAndWait();
        if (categoryName.isPresent())
        {
            if (categoryName.get().equalsIgnoreCase(""))
            {
                openErrorBox("Category name is empty");
                canCreateCategory = false;
            } else
            {
                for (Category c : getCategoryList())
                {
                    if (categoryName.get().trim().equalsIgnoreCase(c.getName()))
                    {
                        openErrorBox("Category with that name already ");
                        canCreateCategory = false;
                        break;
                    }
                }
            }
            if (canCreateCategory == true)
            {
                if (category == null)
                {
                    Category cat = new Category(categoryName.get());
                    categoryManager.saveCategory(cat);
                    fetchCategories();
                } else
                {
                    category.setName(categoryName.get());
                    categoryManager.saveCategory(category);
                    fetchCategories();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes the selected categories in the category list. A confirmation box is shown,
     * asking if the user really wants to delete the selected categories. If yes, removes
     * the categories from the database, otherwise cancels the action.
     * @param categories the list of categories to be deleted
     * @return true if saved, otherwise false
     */
    public boolean deleteCategory(List<Category> categories)
    {
        String categoriesForDeletion = "";
        int loopPosition = 1;

        for (Category category : categories)
        {
            categoriesForDeletion += category.getName();
            if (loopPosition != categories.size())
            {
                categoriesForDeletion += ", ";
            }
            loopPosition++;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Confirm delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete category "
                + categoriesForDeletion + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            for (Category category : categories)
            {
                categoryManager.deleteCategory(category);
            }
            fetchCategories();
            return true;
        } else
        {
            alert.close();
            return false;
        }
    }

    /**
     * Searches for movies with the same title or release year in the observableList.
     * @param input the title or release year of the movie being searched for.
     * @return a list containing all movies that mathces the input string.
     */
    public ObservableList<Movie> searchMovies(String input)
    {
        ObservableList<Movie> searchedMovieList = FXCollections.observableArrayList();

        for (Movie movie : getMovieList())
        {
            String movieYear = movie.getYear() + "";
            if (movie.getTitle().toLowerCase().contains(input) || movieYear.contains(input))
            {
                searchedMovieList.add(movie);
            }
        }
        return searchedMovieList;
    }

    /**
     * Searches the database for movies with the same title og filePath. If such a movie is
     * found, an error box is displayed informing the user, that the following movie already
     * exists in the database.
     * @param movie the movie to search for
     * @return true if the movie already exists in the database, otherwise false
     */
    public boolean searchForExistingMovie(Movie movie)
    {
        if (movieManager.searchForExistingMovie(movie))
        {
            openErrorBox("Following movie already exists");
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * Searches the database for movies that contains the selected categories and meets the
     * minimum user and IMDb rating set in the textareas. The observable list containing
     * all the movies is then set to all the resulting movies.
     * @param categoryList the categories that are being searched for
     * @param isAllSelected boolean value signaling wether the "All" category is selected
     * @param rating the minimum rating that the movies must contain
     */
    public void sortByCategories(List<Category> categoryList, boolean isAllSelected, Rating rating)
    {
        movies.setAll(movieManager.sortByCategories(categoryList, isAllSelected, rating));
    }
    
    /**
     * Opens a error box to be displayed.
     * @param contentText the message that the error box should display.
     */
    public void openErrorBox(String contentText)
    {
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle("Error Dialog");
        errAlert.setHeaderText("ERROR");
        errAlert.setContentText(contentText);
        errAlert.showAndWait();
    }

}
