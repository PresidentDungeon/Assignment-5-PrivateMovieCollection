/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    private TextField artistString;
    @FXML
    private TextField timeInt;
    @FXML
    private TextField fileString;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<?> category;

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
    }

    @FXML
    private void save(ActionEvent event)
    {
    }

    @FXML
    private void chooseFile(ActionEvent event)
    {
    }
    
}
