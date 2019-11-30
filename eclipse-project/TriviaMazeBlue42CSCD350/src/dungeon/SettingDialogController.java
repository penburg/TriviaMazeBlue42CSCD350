/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author patrick
 */
public class SettingDialogController extends Dialog<ButtonType> implements Initializable {

    @FXML
    private Button randomButton;
    @FXML
    private Spinner<String> speciesSpinner;
    @FXML
    private Spinner<String> genderSpinner;
    @FXML
    private TextField nameField;

    private ButtonType mOK;
    private Dungeon mGame;
    
    public final ObservableList<String> species = FXCollections.observableArrayList("Warrior");
    public final ObservableList<String> genders = FXCollections.observableArrayList("Male");

    public SettingDialogController(Dungeon game) throws IOException {
        super();
        mGame = game;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingDialog.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        getDialogPane().setContent(root);

        mOK = new ButtonType("OK", ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().addAll(mOK, cancel);

        getDialogPane().lookupButton(mOK).addEventFilter(ActionEvent.ACTION, eh -> onOK(eh));
        
        randomButton.setOnMouseClicked(notUsed -> onRandomClick());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        nameField.setText(prefs.get("Name", "Jack"));
        SpinnerValueFactory speciesFactory =  new SpinnerValueFactory.ListSpinnerValueFactory(species);
        speciesFactory.setValue(prefs.get("Species", "Warrior"));
        speciesSpinner.setValueFactory(speciesFactory);
        
        SpinnerValueFactory genderFactory =  new SpinnerValueFactory.ListSpinnerValueFactory(genders);
        genderFactory.setValue(prefs.get("Gender", "Male"));
        genderSpinner.setValueFactory(genderFactory);
    }

    private void onOK(ActionEvent eh) {
       
       Preferences prefs = Preferences.userNodeForPackage(getClass());
       prefs.put("Name", nameField.getText());
       prefs.put("Species", speciesSpinner.getValueFactory().getValue());
       prefs.put("Gender", genderSpinner.getValueFactory().getValue());
       
       mGame.newGame();
       
    }

    private void onRandomClick(){
        Random rand = new Random();
        speciesSpinner.getValueFactory().setValue(species.get(rand.nextInt(species.size())));
        genderSpinner.getValueFactory().setValue(genders.get(rand.nextInt(genders.size())));
    }
}
