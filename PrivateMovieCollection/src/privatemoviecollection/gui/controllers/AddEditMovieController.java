/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    private MovieCollectionController controller;
    private MediaPlayer mediaplayer;
    private int currentId;
    private Rating currentRating = new Rating();
    private String currentSummaryText = "";
    private String currentPosterLink = "";
    private String currentIMDBLink = "";
    private int currentTimeInSeconds;
    private LocalDate currentDate;
    @FXML
    private TextField titleString;
    @FXML
    private TextField timeInt;
    @FXML
    private TextField fileString;
    @FXML
    private Button cancelButton;
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

    /**
     * Event handler for the save movie button. Creates a new movie object from the stored
     * data in the instance variables and the text in the textfields of the view. The
     * movie is then saved to the database. If the movie title is empty or a movie with
     * the same title or filepath already exists, the user is warned and the save method
     * is cancelled.
     *
     * @param event
     * @throws NumberFormatException
     */
    @FXML
    private void saveMovie(ActionEvent event) throws NumberFormatException
    {
        try
        {
            String title = titleString.getText().trim();
            int seconds = currentTimeInSeconds;
            int releaseYear = Integer.parseInt(releaseInt.getText());
            String filePath = fileString.getText();

            ArrayList<Category> categories = new ArrayList<>();
            categories.addAll(categoryList.getSelectionModel().getSelectedItems());

            if (title.equals("") || filePath.equals(""))
            {
                appModel.openErrorBox(String.format("%s%n%s", "The movie failed to save or update.",
                        "Please check that all information is entered correctly."));
            } else
            {
                Movie movie = new Movie(title, seconds, releaseYear, filePath);
                movie.setId(currentId);
                movie.setSeconds(currentTimeInSeconds);
                movie.setIMDbLink(currentIMDBLink);
                movie.setRating(currentRating);
                movie.setImageLink(currentPosterLink);
                movie.setSummaryText(currentSummaryText);
                movie.setLastView(currentDate);
                movie.setCategories(categories);
                
                if (!appModel.searchForExistingMovie(movie))
                {
                controller.getAppModel().saveMovie(movie);
                controller.clearMovie();
                controller.sortCategories();
                cancel(event);
                }

            }
        } catch (NumberFormatException ex)
        {
            appModel.openErrorBox(String.format("%s%n%s", "The movie failed to save or update.",
                    "Please check that all information is entered correctly."));
        }
    }

        @FXML
    private void cancel(ActionEvent event)
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Saves the category to the database. If the category's name is empty or a category
     * with the same name already exists, the user is warned and the save method is
     * cancelled.
     *
     * @param event
     */
    @FXML
    private void addCategory(ActionEvent event)
    {
        controller.getAppModel().saveCategory(null);
        categoryList.setItems(controller.getAppModel().getCategoryList());
    }

    /**
     * Updates the selected category from the listview. If a category with the same name
     * already exists or multiple categories are selected, the user is warned and the
     * action is cancelled. Otherwise updates the category in the database.
     *
     * @param event
     */
    @FXML
    private void updateCategory(ActionEvent event)
    {
        if (categoryList.getSelectionModel().getSelectedItems().size() > 1)
        {
            appModel.openErrorBox("Please only select one category when updating");
        } else
        {
            controller.getAppModel().saveCategory(categoryList.getSelectionModel().getSelectedItem());
            categoryList.setItems(controller.getAppModel().getCategoryList());
        }
    }

    /**
     * Event handler for the delete category button. A warned is opened when the user is
     * trying to delete the selected categories. If the user agrees, the categories are
     * removed from the database. All movies containing that category is updated to fit
     * the change.
     *
     * @param event
     */
    @FXML
    private void removeCategory(ActionEvent event)
    {
        controller.getAppModel().deleteCategory(categoryList.getSelectionModel().getSelectedItems());
        categoryList.setItems(controller.getAppModel().getCategoryList());
    }
    
    /**
     * Event handler for the choose button. Creates a JFileChooser that only accepts .mp4
     * and .mpeg4 extensions. If such an extension is selected, saves the absolute or
     * relative filepath depending on the location the fileString textfield. Also
     * retrieves metadata from the chosen file and adds the information to the appropriate
     * textfields.
     *
     * @param event
     */
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

            if (selectedFile.getAbsolutePath().contains("PrivateMovieCollection\\Movie"))
            {
                Path absolutePath = Paths.get(selectedFile.getAbsolutePath());
                Path pathToProject = Paths.get(System.getProperty("user.dir"));
                Path relativePath = pathToProject.relativize(absolutePath);
                fileString.setText(relativePath.toString());
            } else
            {
                fileString.setText(selectedFile.getAbsolutePath());
            }
            Media media = new Media(selectedFile.toURI().toString());
            mediaplayer = new MediaPlayer(media);
            mediaplayer.setOnReady(() ->
            {
                int time = (int) mediaplayer.getTotalDuration().toSeconds();
                currentTimeInSeconds = time;
                timeInt.setText(formatSeconds(time));
                currentDate = LocalDate.now();
            });
        }
    }

    /**
     * This methods runs when the AddEditMovieView FXML is opened by either the "new"
     * movie button or the "edit" movie button. If the "edit" movie button is pressed, the
     * selected movie from the listview in the AddEditMovieView is taken, and some of the
     * informtion stored in this movie object is displayed in the textfields and category
     * listview. The rest of the data is stored in the instance variables of this class.
     *
     * if the "new" movie button is pressed and a IMDB link is given, some of the movie
     * data will be scraped from the IMDb website. Some data will be stored in the
     * textfields and the rest in the instance variables of this class.
     *
     * @param IMDBLink
     * @param movie
     */
    public void setText(Movie movie, String IMDBLink) throws IOException
    {
        if (IMDBLink.equalsIgnoreCase(""))
        {
            titleString.setText(movie.getTitle());
            releaseInt.setText(movie.getYear() + "");
            timeInt.setText(movie.formatSeconds());
            fileString.setText(movie.getFilePath());

            if (!movie.getCategories().isEmpty())
            {
                for (Category category : movie.getCategories())
                {
                    categoryList.getSelectionModel().select(category);
                }
            }

            currentId = movie.getId();
            currentTimeInSeconds = movie.getSeconds();
            currentIMDBLink = movie.getIMDbLink();
            currentRating = movie.getRating();
            currentPosterLink = movie.getImageLink();
            currentSummaryText = movie.getSummaryText();
            currentDate = movie.getLastView();
        } else
        {
            try
            {
                String url = IMDBLink;
                Document document = Jsoup.connect(url).get();

                //get the title of the movie
                Element element = document.select("h1").first();
                titleString.setText(element.textNodes().get(0).text());
                //gets the rating from IMDb
                element = document.select("span[itemprop=ratingValue]").first();
                currentRating = new Rating();
                currentRating.setIMDBRating(Float.parseFloat(element.text()));
                //gets the movie description
                element = document.select("div.summary_text").first();
                currentSummaryText = element.text();
                //gets the movie posterLink
                Element poster = document.getElementsByClass("poster").first();
                Elements img = poster.getElementsByTag("img");
                currentPosterLink = img.attr("src");
                //sets the IMDb link
                currentIMDBLink = IMDBLink;
                //get the release year
                element = document.select("span#titleYear a").first();
                if (element.hasText())
                {
                    releaseInt.setText(element.text());
                }
            } catch (Exception ex)
            {
            }
        }
    }

    /**
     * This methods runs when the AddEditMovieView FXML is opened by either the "new"
     * movie button or the "edit" movie button. This method updates the
     * MusicPlayerController instance variable to that of the one in the parameter.
     *
     * @param controller
     */
    public void setController(MovieCollectionController controller)
    {
        this.controller = controller;
    }
    
    /**
     * Event handler for the cancel button. Closes the AddEditMovieView.
     *
     * @param event
     */

    /**
     * Transforms the seconds int variable into a String to better reflect the duration of
     * the movie.
     *
     * @param seconds the amount of seconds to be formated.
     * @return String containing the formatted duration
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
