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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import privatemoviecollection.be.Category;

/**
 * FXML Controller class
 *
 * @author Bruger
 */
public class AddEditMovieController implements Initializable
{

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
        // TODO
    }

    @FXML
    private void cancel(ActionEvent event)
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void save(ActionEvent event)
    {
    }

    @FXML
    private void chooseFile(ActionEvent event)
    {
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter mp4Filter = new FileNameExtensionFilter(".mp4 Files", "mp4");
        FileNameExtensionFilter mpegFilter = new FileNameExtensionFilter(".mpeg Files", "mpeg");
        jfc.setFileFilter(mp4Filter);
        jfc.setFileFilter(mpegFilter);
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

                timeInt.setText(time + "");
                titleString.setText(title);
                directorString.setText(directors);
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

}
