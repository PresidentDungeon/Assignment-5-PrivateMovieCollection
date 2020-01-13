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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.be.Rating;
import privatemoviecollection.gui.AppModel;
import privatemoviecollection.gui.PrivateMovieCollection;

/**
 *
 * @author Bruger
 */
public class MovieCollectionController implements Initializable {

    private final AppModel appModel = new AppModel();
    private Movie selectedMovie;
    private double selectedIMDBRating = 0;
    private int selectedUserRating = 0;
    @FXML
    private TableView<Movie> tableMovies;
    @FXML
    private TableColumn<Movie, String> columnMovieTitle;
    @FXML
    private TableColumn<Movie, Integer> columnMovieReleaseYear;
    @FXML
    private TableColumn<Movie, String> columnMovieGenre;
    @FXML
    private TableColumn<Movie, Integer> columnUserRating;
    @FXML
    private TableColumn<Movie, Double> ColumnIMDBRating;
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
    @FXML
    private ListView<Category> catListView;
    @FXML
    private ImageView personPic;
    @FXML
    private TextArea minIMDBRating;
    @FXML
    private TextArea minUserRating;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        columnMovieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnMovieReleaseYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        columnMovieGenre.setCellValueFactory((data)
                -> {
            Movie movie = data.getValue();
            return new SimpleStringProperty(movie.formatCategories());

        });
        columnUserRating.setCellValueFactory((data)
                -> {
            int userRating = data.getValue().getRating().getUserRating();
            return new SimpleIntegerProperty(userRating).asObject();

        });
        ColumnIMDBRating.setCellValueFactory((data)
                -> {
            double IMDBRating = data.getValue().getRating().getIMDBRating();
            return new SimpleDoubleProperty(IMDBRating).asObject();

        });

        tableMovies.setItems(appModel.getMovieList());
        catListView.setItems(appModel.getCategoryList());
        catListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Category c = new Category("All");
        catListView.getItems().add(0, c);
        catListView.getSelectionModel().select(0);
        checkForLastView();

    }

    /**
     * Event handler for the new movie button. Opens a new FXML view enabling movies to be added.
     *
     * @param event
     */
    @FXML
    private void addMovie(ActionEvent event) {
        openAddEditMovieController(null);

    }

    /**
     * Event handler for the edit movie button. Opens a new FXML view to edit the selected movie.
     *
     * @param event
     */
    @FXML
    private void editMovie(ActionEvent event) {
        if (selectedMovie != null) {
            openAddEditMovieController(selectedMovie);
        }

    }

    @FXML
    private void deleteMovie(ActionEvent event) {
        if (selectedMovie != null) {
            appModel.DeleteMovie(selectedMovie);
            searchMovie(txt_search.getText());
            clearMovie();
        }
    }

    /**
     * Loads the OpenAddEditMovie view as a window from a primary stage. If the "add" movie button is pressed, a popup will ask the user if they want to enter the movies IMDB link to store extra information. If the "edit" movie button is pressed, the FXMLLoader will load the stage and the selected movies data will be stored.
     *
     * @param movie the movie object selected on the tableColumn
     */
    public void openAddEditMovieController(Movie movie) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AppModel.class.getResource("views/AddEditMovieView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.getIcons().add(new Image(PrivateMovieCollection.class.getResourceAsStream("views/image/multimedia.png")));
            
            AddEditMovieController controller = fxmlLoader.getController();
            MovieCollectionController c = this;
            controller.setController(c);
 
            if (movie != null) {
                controller.setText(movie, "");
            }

            stage.setTitle("New/Edit Movie");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableMovies.getScene().getWindow());
            stage.show();

            if (movie == null) {
                TextInputDialog td = new TextInputDialog();
                td.setTitle("Confirmation");
                td.setHeaderText(null);
                td.setContentText("Do you want to enter the IMDb link to store information?");
                Optional<String> IMDBLink = td.showAndWait();
                stage.getIcons().add(new Image(PrivateMovieCollection.class.getResourceAsStream("views/image/multimedia.png")));
                if (IMDBLink.isPresent()) {
                    if (IMDBLink.get().equalsIgnoreCase("")) {
                        appModel.openErrorBox("Category name is empty");
                    } else {
                        controller.setText(null, IMDBLink.get());
                    }
                }
            }
        } catch (IOException ex) {
        }
    }

    @FXML
    private void playMovie(MouseEvent event) throws IOException {
        if (selectedMovie != null) {
            selectedMovie.setLastView(LocalDate.now());
            appModel.saveMovie(selectedMovie);
            File file = new File(selectedMovie.getFilePath());
            Desktop.getDesktop().open(file);
        }
    }

    @FXML
    private void giveRating(ActionEvent event) throws IOException {
        if (selectedMovie != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AppModel.class.getResource("views/PersonalRatingView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            PersonalRatingController controller = fxmlLoader.getController();
            MovieCollectionController c = this;
            controller.setController(c);
            controller.setText(selectedMovie);
            stage.setTitle("Give a personal rating.");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableMovies.getScene().getWindow());
            stage.show();
        }

    }

    @FXML
    private void updateMovie(MouseEvent event) {
        if (!tableMovies.getSelectionModel().isEmpty()) {
            selectedMovie = tableMovies.getSelectionModel().getSelectedItem();
            movieTitle.setText(selectedMovie.getTitle());
            movieTime.setText(selectedMovie.formatSeconds());
            movieCategories.setText(selectedMovie.formatCategories());
            movieRelease.setText(selectedMovie.getYear() + "");
            personalRating.setText(selectedMovie.getRating().getUserRating() + "");
            imdbRating.setText(selectedMovie.getRating().getIMDBRating() + "");
            movieDescription.setText(selectedMovie.getSummaryText());
            if (!selectedMovie.getImageLink().equalsIgnoreCase("")) {
                Image poster = new Image(selectedMovie.getImageLink());
                posterImage.setImage(poster);
            }
        }
    }

    public void clearMovie() {
        movieTitle.setText("Title");
        personalRating.clear();
        imdbRating.clear();
        movieTime.setText("Time");
        movieCategories.setText("Category");
        movieRelease.setText("Release");
        posterImage.setImage(null);
        movieDescription.clear();
    }

    @FXML
    private void openBrowser(ActionEvent event) {
        try {
            if (selectedMovie != null) {
                Desktop.getDesktop().browse(new URI(tableMovies.getSelectionModel().getSelectedItem().getIMDbLink()));
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(MovieCollectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void searchMovie(String searchString) {

        if (searchString.equalsIgnoreCase("")) {
            tableMovies.setItems(appModel.getMovieList());
        } else {
            tableMovies.setItems(appModel.searchMovies(searchString));
        }
    }

    @FXML
    private void handleSearchMovie(KeyEvent event) {
        searchMovie(txt_search.getText().trim().toLowerCase());

    }

    public void checkForLastView() {
        List<Movie> oldMovies = new ArrayList();
        String allOldMovies = "";

        for (Movie movie : appModel.getMovieList()) {
            if (movie.getRating().getUserRating() < 6) //rating
            {
                if (movie.compareLastView() > 730) //number of days
                {
                    oldMovies.add(movie);
                    allOldMovies += movie.toString() + "\n";
                }
            }
        }
        if (!oldMovies.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Old (low rated) movies");
            alert.setHeaderText(null);
            alert.setContentText("You haven't watched these movies in a while: \n"
                    + allOldMovies + "Do you want to delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                for (Movie m : oldMovies) {
                    appModel.DeleteMovie(m);
                }
            } else {
                alert.close();
            }
        }

    }

    public void sortCategories() {
            Rating rating = new Rating();
            rating.setIMDBRating(selectedIMDBRating);
            rating.setUserRating(selectedUserRating);
            
        if (catListView.getSelectionModel().isEmpty() || catListView.getSelectionModel().isSelected(0)) {
            List<Category> emptyList = new ArrayList();
            appModel.sortByCategories(emptyList, true, rating);
            searchMovie(txt_search.getText());
        } else {
            appModel.sortByCategories(catListView.getSelectionModel().getSelectedItems(), false, rating);
            searchMovie(txt_search.getText());
        }

    }
    
    public void updateRating(Movie movie)
    {
        appModel.saveMovie(movie);
        sortCategories();
        tableMovies.getSelectionModel().select(movie);
        selectedMovie = movie;
        personalRating.setText(movie.getRating().getUserRating() + "");
    }

    @FXML
    private void handleUpdateList(MouseEvent event) {
        sortCategories();
    }

    @FXML
    private void handleIMDBScoreUpdate(KeyEvent event) {
        
        String rating = minIMDBRating.getText();
        
        if (rating.isEmpty() || rating.equalsIgnoreCase(""))
        {
            selectedIMDBRating = 0;
        }
        else
        {
            try
            {
                selectedIMDBRating = Double.parseDouble(rating);
            }
            catch
                    (NumberFormatException ex)
            {
                appModel.openErrorBox("IMDb must be between 0-10");
                minIMDBRating.setText("0");
                selectedIMDBRating = 0;
            }
        }
        if (selectedIMDBRating < 0 || selectedIMDBRating > 10)
        {
            appModel.openErrorBox("IMDb Rating must be between 0-10");
                minIMDBRating.setText("0");
                selectedIMDBRating = 0;
        }
        sortCategories(); 
    }
    
    @FXML
    private void handleUserScoreUpdate(KeyEvent event) {
        
        String rating = minUserRating.getText();
        
        if (rating.isEmpty() || rating.equalsIgnoreCase(""))
        {
            selectedUserRating = 0;
        }
        else
        {
            try
            {
                selectedUserRating = Integer.parseInt(rating);
            }
            catch
                    (NumberFormatException ex)
            {
                appModel.openErrorBox("User rating must be a whole number between 0-10");
                minUserRating.setText("0");
                selectedUserRating = 0;
            }
        }
        if (selectedUserRating < 0 || selectedUserRating > 10)
        {
            appModel.openErrorBox("User rating must be a whole number between 0-10");
                minUserRating.setText("0");
                selectedUserRating = 0;
        }
        sortCategories(); 
    }

        public AppModel getAppModel()
    {
        return appModel;
    }

 
}
