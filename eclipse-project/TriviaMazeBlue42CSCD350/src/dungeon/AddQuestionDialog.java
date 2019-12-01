package dungeon;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
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

	@FXML	private Tab TF_Tab;
	@FXML	private TextArea TF_Prompt;
	@FXML	private RadioButton TF_True;
	@FXML	private TextField TF_Explanation;

	@FXML	private Tab MC_Tab;
	@FXML	private TextArea MC_Prompt;
	@FXML	private RadioButton MC_A_Radio;
	@FXML	private RadioButton MC_B_Radio;
	@FXML	private RadioButton MC_C_Radio;
	@FXML	private RadioButton MC_D_Radio;
	@FXML	private TextField MC_A_Field;
	@FXML	private TextField MC_B_Field;
	@FXML	private TextField MC_C_Field;
	@FXML	private TextField MC_D_Field;
	@FXML	private TextField MC_Explanation;
	
	@FXML	private Tab SA_Tab;
	@FXML	private TextArea SA_Prompt;
	@FXML	private TextField SA_Answer;
	@FXML	private TextField SA_Explanation;
	
	@FXML	private Tab YT_Tab;
	@FXML	private TextArea YT_Prompt;
	@FXML	private TextField YT_URL;
	@FXML	private CheckBox YT_AutoPlay;
	@FXML	private Spinner<Integer> YT_Spinner;
	@FXML	private RadioButton YT_A_Radio;
	@FXML	private RadioButton YT_B_Radio;
	@FXML	private RadioButton YT_C_Radio;
	@FXML	private RadioButton YT_D_Radio;
	@FXML	private TextField YT_A_Field;
	@FXML	private TextField YT_B_Field;
	@FXML	private TextField YT_C_Field;
	@FXML	private TextField YT_D_Field; 
	@FXML	private Button YT_Test;
	@FXML	private TextField YT_Explanation;

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
		
		YT_Spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 1));
		YT_Spinner.getValueFactory().setValue(0);
	}

	private void onOK(ActionEvent eh) {
		String prompt;
		String explanation;
		int correctChoice;
		ArrayList<String> options = new ArrayList<String>();
		if(TF_Tab.isSelected()) {
			prompt = TF_Prompt.getText();
			options.add("True");
			options.add("False");
			correctChoice = TF_True.isSelected() ? 1 : 2;
			explanation = TF_Explanation.getText();
		}
		else if(MC_Tab.isSelected()) {
			prompt = MC_Prompt.getText();
			explanation = MC_Explanation.getText();
			options.add(MC_A_Field.getText());
			options.add(MC_B_Field.getText());
			options.add(MC_C_Field.getText());
			options.add(MC_D_Field.getText());
			if(MC_A_Radio.isSelected()) {
				correctChoice = 1;
			}
			else if(MC_B_Radio.isSelected()) {
				correctChoice = 2;
			}
			else if(MC_C_Radio.isSelected()) {
				correctChoice = 3;
			}
			else if(MC_D_Radio.isSelected()) {
				correctChoice = 4;
			}
		}
		else if(SA_Tab.isSelected()) {
			prompt = SA_Prompt.getText();
			explanation = SA_Explanation.getText();
			String saAnswer = SA_Answer.getText();
		}
		else if(YT_Tab.isSelected()) {
			prompt = YT_Prompt.getText();
			explanation = YT_Explanation.getText();
			String URL = YT_URL.getText();
			int startPos = YT_Spinner.getValue();
			boolean autoPlay = YT_AutoPlay.isSelected();
			options.add(YT_A_Field.getText());
			options.add(YT_B_Field.getText());
			options.add(YT_C_Field.getText());
			options.add(YT_D_Field.getText());
			if(YT_A_Radio.isSelected()) {
				correctChoice = 1;
			}
			else if(YT_B_Radio.isSelected()) {
				correctChoice = 2;
			}
			else if(YT_C_Radio.isSelected()) {
				correctChoice = 3;
			}
			else if(YT_D_Radio.isSelected()) {
				correctChoice = 4;
			}
		}


	}

}
