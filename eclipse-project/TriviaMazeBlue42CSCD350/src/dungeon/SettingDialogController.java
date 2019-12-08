/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.CheckBox;
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
    private Button presetLoadButton;
    @FXML
    private Spinner<String> presetSpinner;
    @FXML
    private TextField nameField;
    @FXML
    private CheckBox TF_Checkbox;
    @FXML
    private CheckBox MC_Checkbox;
    @FXML
    private CheckBox SA_Checkbox;
    @FXML
    private CheckBox YT_Checkbox;
    private ButtonType mOK;
    private Dungeon mGame;  
    public final ObservableList<String> presets = FXCollections.observableArrayList("Easy", "Medium", "Hard", "Custom");
   
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
        presetLoadButton.setOnMouseClicked(notUsed -> onLoadPresetClick());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        nameField.setText(prefs.get("Name", "Jack"));
        SpinnerValueFactory<String> presetFactory =  new SpinnerValueFactory.ListSpinnerValueFactory<String>(presets);
        presetFactory.setValue(prefs.get("Preset", "Custom"));
        presetSpinner.setValueFactory(presetFactory);
        TF_Checkbox.setSelected(prefs.getBoolean("TrueFalse", true));
        MC_Checkbox.setSelected(prefs.getBoolean("MultipleChoice", true));
        SA_Checkbox.setSelected(prefs.getBoolean("ShortAnswer", true));
        YT_Checkbox.setSelected(prefs.getBoolean("Video", true));
    }

    private void onOK(ActionEvent eh) { 
       Preferences prefs = Preferences.userNodeForPackage(getClass());
       prefs.put("Name", nameField.getText());
       prefs.putBoolean("TrueFalse", TF_Checkbox.isSelected());
       prefs.putBoolean("MultipleChoice", MC_Checkbox.isSelected());
       prefs.putBoolean("ShortAnswer", SA_Checkbox.isSelected());
       prefs.putBoolean("Video", YT_Checkbox.isSelected());
       prefs.put("Preset",  presetSpinner.getValue());
       mGame.newGame();      
    }

    private void onLoadPresetClick(){
        switch(presetSpinner.getValue()) {
        case "Easy":
        	TF_Checkbox.setSelected(true);
            MC_Checkbox.setSelected(true);
            SA_Checkbox.setSelected(false);
            YT_Checkbox.setSelected(false);
        	break;
        case "Medium":
        	TF_Checkbox.setSelected(true);
            MC_Checkbox.setSelected(true);
            SA_Checkbox.setSelected(true);
            YT_Checkbox.setSelected(false);
        	break;
        case "Hard":
        	TF_Checkbox.setSelected(false);
            MC_Checkbox.setSelected(false);
            SA_Checkbox.setSelected(true);
            YT_Checkbox.setSelected(true);
        	break;
        case "Custom":
        	TF_Checkbox.setSelected(true);
            MC_Checkbox.setSelected(true);
            SA_Checkbox.setSelected(true);
            YT_Checkbox.setSelected(true);
        	break;
        }
    }
}
