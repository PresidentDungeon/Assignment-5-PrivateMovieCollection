/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    }

}
