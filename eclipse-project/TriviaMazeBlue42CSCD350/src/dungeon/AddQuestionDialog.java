package dungeon;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;

/*
 * Course:	CSCD350
 * Term:	Fall 2019
 * Team:	Blue42
 * 
 */

/**
 * @author Patrick Enburg <patrick@enburg.info>
 */

public class AddQuestionDialog extends Dialog<ButtonType> implements Initializable {

    private ButtonType mOK;
    private Dungeon mGame;
	
    public AddQuestionDialog(Dungeon game) throws IOException {
        super();
        mGame = game;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddQuestionDialog.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        getDialogPane().setContent(root);

        mOK = new ButtonType("OK", ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().addAll(mOK, cancel);

        getDialogPane().lookupButton(mOK).addEventFilter(ActionEvent.ACTION, eh -> onOK(eh));
        
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	
    private void onOK(ActionEvent eh) {
        
//stub
        
     }

}
