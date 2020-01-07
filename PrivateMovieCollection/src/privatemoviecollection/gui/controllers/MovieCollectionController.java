/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.gui.AppModel;

/**
 *
 * @author Bruger
 */
public class MovieCollectionController implements Initializable
{

    private final AppModel appModel = new AppModel();
    private Label label;
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

            for (Category category : movieCategories)
            {
                allCategories += category.getName();

                if (loopPosition != movieCategories.size())
                {
                    allCategories += ", ";
                }

                loopPosition++;
            }

            return new SimpleStringProperty(allCategories);

        });

        tableMovies.setItems(appModel.getMovieList());
        updateCategoryBox();

    }

    @FXML
    private void addMovie(ActionEvent event) throws IOException
    {
        openWindow(null, "views/AddEditMovieView.fxml", "New Movie");

    }

    @FXML
    private void editMovie(ActionEvent event)
    {
        if (tableMovies.getSelectionModel().getSelectedItem() != null)
        {
            openWindow(tableMovies.getSelectionModel().getSelectedItem(), "views/AddEditMovieView.fxml", "New/Edit Movie");
        }

    }

    /**
     * Loads a new FXML view as a window from a primary stage
     *
     * @param movie the movie object selected on the tableColumn
     * @param viewFXML the FXML file that should be viewed as a window
     * @param windowMessage the title of the window
     */
    public void openWindow(Movie movie, String viewFXML, String windowMessage)
    {

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AppModel.class.getResource(viewFXML));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            if (movie != null)
            {
                AddEditMovieController controller = fxmlLoader.getController();
                controller.setText(movie);
            }

            stage.setTitle(windowMessage);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableMovies.getScene().getWindow());
            stage.show();

        } catch (IOException ex)
        {

        }
    }

    private void updateCategoryBox()
    {
        categoryComboBox.getItems().clear();
//        Category allCategory = new Category("All");
        categoryComboBox.setItems(appModel.getCategoryList());
//        categoryComboBox.getItems().add(allCategory);

    }
}
