/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private boolean mustRefreshRating = false;
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
    @FXML
    private TextField txt_search;
    @FXML
    private Hyperlink imdbLink;
    @FXML
    private ImageView posterImage;
    @FXML
    private TextArea imdbRating;
    @FXML
    private TextArea personalRating;
    @FXML
    private TextArea movieTitle;
    @FXML
    private TextArea movieTime;
    @FXML
    private TextArea movieCategories;
    @FXML
    private TextArea movieRelease;
    @FXML
    private TextArea movieDescription;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        columnMovieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnMovieReleaseYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        columnMovieGenre.setCellValueFactory((data) ->
        {
            Movie movie = data.getValue();
            return new SimpleStringProperty(movie.formatCategories());

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
        if (tableMovies.getSelectionModel().isEmpty() != true)
        {
            appModel.DeleteMovie(tableMovies.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Loads the OpenAddEditMovie view as a window from a primary stage. If the "add"
     * movie button is pressed, a popup will ask the user if they want to enter the movies
     * IMDB link to store extra information. If the "edit" movie button is pressed, the
     * FXMLLoader will load the stage and the selected movies data will be stored.
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
    private void giveARating(ActionEvent event) throws IOException
    {
        Movie movie = tableMovies.getSelectionModel().getSelectedItem();
        mustRefreshRating = true;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppModel.class.getResource("views/PersonalRatingView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        PersonalRatingController controller = fxmlLoader.getController();
        controller.setText(movie);
        stage.setTitle("Give a personal rating.");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(tableMovies.getScene().getWindow());
        stage.show();
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
            clearMovie();
        }
        else if (mustRefreshRating)
        {
        personalRating.setText(tableMovies.getSelectionModel().getSelectedItem().getRating().getUserRating() + "");
        mustRefreshRating = false;
        }

    }

    @FXML
    private void handleSearchMovie(ActionEvent event)
    {

    }

    @FXML
    private void playMovie(MouseEvent event) throws IOException
    {
        if (!tableMovies.getSelectionModel().isEmpty())
        {
        Movie selectedMovie = tableMovies.getSelectionModel().getSelectedItem();
        selectedMovie.setLastView(LocalDate.now());
        appModel.saveMovie(selectedMovie);
        File file = new File(selectedMovie.getFilePath());
        Desktop.getDesktop().open(file);
        }
    }

    @FXML
    private void updateMovie(MouseEvent event)
    {
        if (!tableMovies.getSelectionModel().isEmpty())
        {
            Movie selectedMovie = tableMovies.getSelectionModel().getSelectedItem();
            movieTitle.setText(selectedMovie.getTitle());
            movieTime.setText(selectedMovie.formatSeconds());
            movieCategories.setText(selectedMovie.formatCategories());
            movieRelease.setText(selectedMovie.getYear() + "");
            personalRating.setText(selectedMovie.getRating().getUserRating() + "");
            imdbRating.setText(selectedMovie.getRating().getIMDBRating() + "");
            movieDescription.setText(selectedMovie.getSummaryText());
            if (!selectedMovie.getImageLink().equalsIgnoreCase(""))
            {
                Image poster = new Image(selectedMovie.getImageLink());
                posterImage.setImage(poster);
            }
        }
    }

    private void clearMovie()
    {
    movieTitle.setText("Title");
    personalRating.clear();
    imdbRating.clear();
    movieTime.setText("time");
    movieCategories.setText("category");
    movieRelease.setText("release");
    posterImage.setImage(null);
    movieDescription.clear();
    }
    
    @FXML
    private void openBrowser(ActionEvent event)
    {
        try
        {
            if (tableMovies.getSelectionModel().isEmpty() != true)
            {
            Desktop.getDesktop().browse(new URI(tableMovies.getSelectionModel().getSelectedItem().getIMDbLink()));
            }
        } catch (URISyntaxException | IOException ex)
        {
            Logger.getLogger(MovieCollectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
