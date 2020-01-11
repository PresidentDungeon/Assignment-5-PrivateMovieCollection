/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui;

import java.util.ArrayList;
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
     * Deletes the movie from the database.
     *
     * @param movie the movie to be removed
     * @return true if removed, otherwise false
     */
    public boolean DeleteMovie(Movie movie)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Confirm delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete movie "
                + movie.getTitle() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            movieManager.deleteMovie(movie);
            fetchMovies();
            return true;
        } else
        {
            alert.close();
            return false;
        }

    }

    public void openErrorBox(String contentText)
    {
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle("Error Dialog");
        errAlert.setHeaderText("ERROR");
        errAlert.setContentText(contentText);
        errAlert.showAndWait();
    }

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
    
    public boolean searchForExistingMovie(Movie movie)
    {
        if (movieManager.searchForExistingMovie(movie))
        {
            openErrorBox("Following movie already exists");
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void sortByCategories(List<Category> categoryList, boolean isAllSelected, double minimumRating)
    {
        movies.setAll(movieManager.sortByCategories(categoryList, isAllSelected, minimumRating));
    }

}

//Hvis man tilføjer en fil - søge efter navn og eller filePath - skal have 0 results
//Hvis man redigerer en fil - søge efter navn og eller filePath - skal have 1 results
//open error baseret på dette