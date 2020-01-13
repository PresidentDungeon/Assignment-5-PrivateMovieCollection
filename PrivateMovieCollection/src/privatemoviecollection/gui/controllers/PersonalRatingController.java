/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import privatemoviecollection.be.Movie;
import privatemoviecollection.be.Rating;
import privatemoviecollection.gui.AppModel;

/**
 * FXML Controller class
 *
 * @author NLens
 */
public class PersonalRatingController implements Initializable
{

    private AppModel appModel = new AppModel();
    private MovieCollectionController controller;
    @FXML
    private ImageView oneStar;
    @FXML
    private ImageView twoStar;
    @FXML
    private ImageView threeStar;
    @FXML
    private ImageView fourStar;
    @FXML
    private ImageView fiveStar;
    @FXML
    private ImageView sixStar;
    @FXML
    private ImageView sevenStar;
    @FXML
    private ImageView eightStar;
    @FXML
    private ImageView nineStar;
    @FXML
    private ImageView tenStar;

    private Movie movie;
    private Rating rating;
    @FXML
    private AnchorPane reviewView;
    @FXML
    private Text headlineMovie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    /**
     * Event handler for the oneStarRating button. Gives the selected movie a personal
     * rating of one, updates the movie and closes the window.
     *
     * @param event
     */
    @FXML
    private void oneStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(1);
        controller.updateRating(movie);
        close();
    }

    /**
     * Event handler for the twoStarRating button. Gives the selected movie a personal
     * rating of two, updates the movie and closes the window.
     *
     * @param event
     */
    @FXML
    private void twoStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(2);
        controller.updateRating(movie);
        close();
    }

    /**
     * Event handler for the threeStarRating button. Gives the selected movie a personal
     * rating of three, updates the movie and closes the window.
     *
     * @param event
     */
    @FXML
    private void threeStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(3);
        controller.updateRating(movie);
        close();
    }

    /**
     * Event handler for the fourStarRating button. Gives the selected movie a personal
     * rating of four, updates the movie and closes the window.
     *
     * @param event
     */
    @FXML
    private void fourStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(4);
        controller.updateRating(movie);
        close();
    }

    /**
     * Event handler for the fiveStarRating button. Gives the selected movie a personal
     * rating of five, updates the movie and closes the window.
     *
     * @param event
     */
    @FXML
    private void fiveStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(5);
        controller.updateRating(movie);
        close();
    }

    /**
     * Event handler for the sixStarRating button. Gives the selected movie a personal
     * rating of six, updates the movie and closes the window.
     *
     * @param event
     */
    @FXML
    private void sixStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(6);
        controller.updateRating(movie);
        close();
    }

    /**
     * Event handler for the sevenStarRating button. Gives the selected movie a personal
     * rating of seven, updates the movie and closes the window.
     *
     * @param event
     */
    @FXML
    private void sevenStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(7);
        controller.updateRating(movie);
        close();
    }

    /**
     * Event handler for the eightStarRating button. Gives the selected movie a personal
     * rating of eight, updates the movie and closes the window.
     *
     * @param event
     */
    @FXML
    private void eightStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(8);
        controller.updateRating(movie);
        close();
    }

    /**
     * Event handler for the nineStarRating button. Gives the selected movie a personal
     * rating of nine, updates the movie and closes the window.
     *
     * @param event
     */
    @FXML
    private void nineStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(9);
        controller.updateRating(movie);
        close();
    }

    /**
     * Event handler for the tenStarRating button. Gives the selected movie a personal
     * rating of ten, updates the movie and closes the window.
     *
     * @param event
     */
    @FXML
    private void tenStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(10);
        controller.updateRating(movie);
        close();
    }

    /**
     * This methods runs when the PersonalRatingView FXML is opened by either the "give a
     * personal rating" movie button. This method updates the movie instance variable to
     * that of the one in the parameter. Also updated the headline text based on the
     * movies title.
     *
     * @param movie
     */
    public void setText(Movie movie)
    {
        this.movie = movie;
        headlineMovie.setText("What will you rate " + movie.getTitle() + "?");
    }

    /**
     * This methods runs when the PersonalRatingView FXML is opened by either the "give a
     * personal rating" movie button. This method updates the MusicPlayerController
     * instance variable to that of the one in the parameter.
     *
     * @param controller
     */
    public void setController(MovieCollectionController controller)
    {
        this.controller = controller;
    }

    /**
     * Event handler for the cancel button. Closes the PersonalRatingView.
     *
     * @param event
     */
    private void close()
    {
        Stage stage = (Stage) reviewView.getScene().getWindow();
        stage.close();
    }

}
