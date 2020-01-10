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

/**
 * FXML Controller class
 *
 * @author NLens
 */
public class PersonalRatingController implements Initializable
{

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

    @FXML
    private void oneStarRating(MouseEvent event) //TODO setting 1/10 rating on selected movie.
    {
        movie.getId();
        movie.getRating().setUserRating(1);
        System.out.println(movie.getRating().getUserRating());
        close();
    }

    @FXML
    private void twoStarRating(MouseEvent event) //TODO setting 2/10 rating on selected movie.
    {
        movie.getId();
        movie.getRating().setUserRating(2);
        System.out.println(movie.getRating().getUserRating());
        close();
    }

    @FXML
    private void threeStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(3);
        System.out.println(movie.getRating().getUserRating());
        close();
    }

    @FXML
    private void fourStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(4);
        System.out.println(movie.getRating().getUserRating());
        close();
    }

    @FXML
    private void fiveStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(5);
        System.out.println(movie.getRating().getUserRating());
        close();
    }
    
    @FXML
    private void sixStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(6);
        System.out.println(movie.getRating().getUserRating());
        close();
    }

    @FXML
    private void sevenStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(7);
        System.out.println(movie.getRating().getUserRating());
        close();
    }

    @FXML
    private void eightStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(8);
        System.out.println(movie.getRating().getUserRating());
        close();
    }

    @FXML
    private void nineStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(9);
        System.out.println(movie.getRating().getUserRating());
        close();
    }

    @FXML
    private void tenStarRating(MouseEvent event)
    {
        movie.getId();
        movie.getRating().setUserRating(10);
        System.out.println(movie.getRating().getUserRating());
        close();
    }

    
    public void setText(Movie movie)
    {
        this.movie = movie;
        headlineMovie.setText("What will you rate " + movie.getTitle() + "?");
    }
    
    private void close ()
    {
        Stage stage = (Stage) reviewView.getScene().getWindow();
        stage.close();
    }
    
    public static void main(String[] args)
    {
        
    }
}
