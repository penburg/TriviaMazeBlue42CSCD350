/*
 * Course:	CSCD350
 * Term:	Fall 2019
 * Team:	Blue42
 *
 */

/**
 * @author Patrick Enburg <patrick@enburg.info
 */

package dungeon;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebView;

public class VideoQuestion extends Question {
	private boolean isVideoShown = false;
	private int optionCorrect;

	private String url;
	
	public VideoQuestion(String url, ArrayList<String> options, int correctOption, String explanation) {
		optionSelected = 0;
		this.url = url;
		this.options = options;
		this.optionCorrect = correctOption;
		this.explanation = explanation;
		
	}

	@Override
	public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(imgX, imgY, x, y, offset, canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.save();

		if(!isVideoShown) {
			WebView webView = new WebView();
			//https://youtu.be/rvtRuzKbPfY
			webView.getEngine().load(this.url);
			webView.setContextMenuEnabled(false);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("WebView");
			alert.setHeaderText("WebView");

			alert.getDialogPane().setContent(webView);
			alert.showAndWait();
			isVideoShown = true;
		}
		
		drawPrompt(imgX, imgY, x, y, offset, canvas);
		drawMenu(imgX, imgY, x, y, offset, canvas);
        

		gc.restore();
	}

	@Override
	public void onUp() {
		optionSelected = (optionSelected - 1 + options.size()) % options.size();
	}

	@Override
	public void onDown() {
		optionSelected = (optionSelected + 1 + options.size()) % options.size();
	}

	@Override
	public void onEnter() {
		this.QuestionCorrect = (optionSelected == optionCorrect);
		this.QuestionSubmitted.set(true);
	}

}
