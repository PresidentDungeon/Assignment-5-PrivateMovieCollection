/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.be.Rating;
import privatemoviecollection.gui.AppModel;

/**
 * FXML Controller class
 *
 * @author Bruger
 */
public class AddEditMovieController implements Initializable
{

    private final AppModel appModel = new AppModel();
    private int currentId;
    private int currentTimeInSeconds;
    @FXML
    private TextField titleString;
    @FXML
    private TextField timeInt;
    @FXML
    private TextField fileString;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField directorString;
    @FXML
    private TextField imdbString;
    @FXML
    private TextField releaseInt;
    @FXML
    private ListView<Category> categoryList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        categoryList.setItems(appModel.getCategoryList());
        categoryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void cancel(ActionEvent event)
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void save(ActionEvent event) throws NumberFormatException
    {
        try
        {

            String title = titleString.getText();
            String director = directorString.getText();
            int seconds = currentTimeInSeconds;
            int releaseYear = Integer.parseInt(releaseInt.getText());
            String filePath = fileString.getText();

            ObservableList<Category> selectedCategories = categoryList.getSelectionModel().getSelectedItems();
            ArrayList<Category> categories = new ArrayList<>();
            categories.addAll(selectedCategories);

            if (title.equals("") || director.equals("") || filePath.equals("") || categories.size() == 0)
            {
                openErrorBox();

            } else
            {

                //We somehow need to get the IMDB rating here, right now, it is just set to 0.
                Rating rating = new Rating(0);

                Movie movie = new Movie(title, director, seconds, releaseYear, filePath, rating, categories);
                movie.setId(currentId);

                System.out.println(movie);
            }
        } catch (NumberFormatException ex)
        {
            openErrorBox();
        }
    }

    @FXML
    private void chooseFile(ActionEvent event)
    {
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter mpeg4Filter = new FileNameExtensionFilter(".mpeg4 Files", "mpeg4");
        FileNameExtensionFilter mp4Filter = new FileNameExtensionFilter(".mp4 Files", "mp4");
        jfc.setFileFilter(mpeg4Filter);
        jfc.setFileFilter(mp4Filter);
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.setCurrentDirectory(new File("."));

        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = jfc.getSelectedFile();

            fileString.setText(selectedFile.getAbsolutePath());

            Media media = new Media(selectedFile.toURI().toString());

            MediaPlayer mediaplayer = new MediaPlayer(media);

            mediaplayer.setOnReady(() ->
            {
                int time = (int) media.getDuration().toSeconds();
                String title = (String) media.getMetadata().get("title");
                String directors = (String) media.getMetadata().get("directors");

                currentTimeInSeconds = time;
                timeInt.setText(formatSeconds(time));
//                titleString.setText(title);
//                directorString.setText(directors);
            });

        }
    }

    @FXML
    private void addCategory(ActionEvent event)
    {
    }

    @FXML
    private void removeCategory(ActionEvent event)
    {
    }

    /**
     * This methods runs if a movie is selected when the AddEditMovieView FXML is opened
     * by the "edit movie" button. It takes the selected movie and fills the textfields
     * and category listview in the AddEditMovieView with the information that has already
     * been given to the movie.
     *
     * @param movie
     */
    public void setText(Movie movie)
    {

        titleString.setText(movie.getTitle());
        directorString.setText(movie.getDirector());
        releaseInt.setText(movie.getYear() + "");
        timeInt.setText(movie.formatSeconds());
        currentTimeInSeconds = movie.getSeconds();
        fileString.setText(movie.getFilePath());

        //Right now the category matches if the name of two categories are identical. Maybe
        //this should be changed, so that it matches for ID instead?
        for (Category category : movie.getCategories())
        {
            categoryList.getSelectionModel().select(category);
        }

        currentId = movie.getId();
    }

    public void openErrorBox()
    {
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle("Error Dialog");
        errAlert.setHeaderText("ERROR");
        errAlert.setContentText(String.format("%s%n%s", "The movie failed to save or update.", "Please check that all information is entered correctly."));
        errAlert.showAndWait();
    }

    /**
     * Transforms the seconds int variable into a String.Example: 130 becomes "02:10"
     *
     * @param seconds the amount of seconds to be formated.
     * @return String with minutes/seconds in format ##.##
     */
    public String formatSeconds(int seconds)
    {
        Duration duration = Duration.seconds(seconds);
        String durationString = String.format("%02d:%02d:%02d",
                (int) duration.toHours() % 60,
                (int) duration.toMinutes() % 60,
                (int) duration.toSeconds() % 60);
        return durationString;
    }
}
