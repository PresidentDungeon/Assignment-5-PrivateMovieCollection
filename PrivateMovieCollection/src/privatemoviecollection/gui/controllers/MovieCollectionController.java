/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.gui.AppModel;
import privatemoviecollection.gui.PrivateMovieCollection;

/**
 *
 * @author Bruger
 */
public class MovieCollectionController implements Initializable
{

    private final AppModel appModel = new AppModel();
    private Label label;
    private boolean mustRefresh = false;
    @FXML
    private TableView<Movie> tableMovies;
    @FXML
    private TableColumn<Movie, String> columnMovieTitle;
    @FXML
    private TableColumn<Movie, Integer> columnMovieReleaseYear;
    @FXML
    private TableColumn<Movie, String> columnMovieGenre;
    @FXML
    private ComboBox<Category> categoryComboBox;
    @FXML
    private Button rateMeButton;
    @FXML
    private Text personalShow;

    @FXML
    private ImageView playButton;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        columnMovieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnMovieReleaseYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        columnMovieGenre.setCellValueFactory((data) ->
        {
            Movie movie = data.getValue();
            ArrayList<Category> movieCategories = movie.getCategories();
            String allCategories = "";
            int loopPosition = 1;

            if (!movieCategories.isEmpty())
            {
                for (Category category : movieCategories)
                {
                    allCategories += category.getName();

                    if (loopPosition != movieCategories.size())
                    {
                        allCategories += ", ";
                    }

                    loopPosition++;
                }
            } else
            {
                allCategories = "";
            }

            return new SimpleStringProperty(allCategories);

        });

        tableMovies.setItems(appModel.getMovieList());
        categoryComboBox.setItems(appModel.getCategoryList());
                    Category c = new Category("All");
            categoryComboBox.getItems().add(0, c);

    }

    /**
     * Event handler for the new movie button. Opens a new FXML view enabling movies to be
     * added.
     *
     * @param event
     */
    @FXML
    private void addMovie(ActionEvent event)
    {
        openAddEditMovieController(null);

    }

    /**
     * Event handler for the edit movie button. Opens a new FXML view to edit the selected
     * movie.
     *
     * @param event
     */
    @FXML
    private void editMovie(ActionEvent event)
    {
        if (tableMovies.getSelectionModel().getSelectedItem() != null)
        {
            openAddEditMovieController(tableMovies.getSelectionModel().getSelectedItem());
        }

    }

    @FXML
    private void deleteMovie(ActionEvent event)
    {
        appModel.DeleteMovie(tableMovies.getSelectionModel().getSelectedItem());
    }

    /**
     * Loads the OpenAddEditMovie view as a window from a primary stage. If the "add" movie
     * button is pressed, a popup will ask the user if they want to enter the movies IMDB link
     * to store extra information. If the "edit" movie button is pressed, the FXMLLoader 
     * will load the stage and the selected movies data will be stored.
     *
     * @param movie the movie object selected on the tableColumn
     */
    public void openAddEditMovieController(Movie movie)
    {
        mustRefresh = true;
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AppModel.class.getResource("views/AddEditMovieView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PrivateMovieCollection.class.getResourceAsStream("views/image/multimedia.png")));

            if (movie != null)
            {
                AddEditMovieController controller = fxmlLoader.getController();
                controller.setText(movie, "");
            }

            stage.setTitle("New/Edit Movie");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableMovies.getScene().getWindow());
            stage.show();

            if (movie == null)
            {
                TextInputDialog td = new TextInputDialog();
                td.setTitle("Confirmation");
                td.setHeaderText(null);
                td.setContentText("Do you want to enter the IMDb link to store information?");
                Optional<String> IMDBLink = td.showAndWait();
                stage.getIcons().add(new Image(PrivateMovieCollection.class.getResourceAsStream("views/image/multimedia.png")));
                if (IMDBLink.isPresent())
                {
                    if (IMDBLink.get().equalsIgnoreCase(""))
                    {
                        appModel.openErrorBox("Category name is empty");
                    } else
                    {
                        AddEditMovieController controller = fxmlLoader.getController();
                        controller.setText(null, IMDBLink.get());
                    }
                }
            }
        } catch (IOException ex)
        {
        }
    }

    @FXML
    private void giveARating(ActionEvent event)
    {
        openWindow(null, "views/PersonalRatingView.fxml", "Give a personal rating");
    }

    @FXML
    private void playMovie(MouseEvent event)
    {

    }

    @FXML
    private void updateAll(MouseEvent event)
    {
        if (mustRefresh)
        {
            appModel.fetchCategories();
            appModel.fetchMovies();
            Category c = new Category("All");
            categoryComboBox.getItems().add(0, c);
            categoryComboBox.getSelectionModel().select(0);
            mustRefresh = false;
        }

    }

}
