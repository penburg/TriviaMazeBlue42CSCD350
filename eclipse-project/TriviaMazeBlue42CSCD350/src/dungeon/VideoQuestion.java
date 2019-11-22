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
	private int optionSelected, optionCorrect;

	private ArrayList<String> options;
	private String url;
	
	public VideoQuestion(String url, ArrayList<String> options, int correctOption) {
		optionSelected = 0;
		this.url = url;
		this.options = options;
		this.optionCorrect = correctOption;
		
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
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("WebView");
			alert.setHeaderText("WebView");

			alert.getDialogPane().setContent(webView);
			alert.showAndWait();
			isVideoShown = true;
		}
		
		double tenPercent = offset * 0.1f;
        double fivePercent = offset * 0.05f;
        double thirtyFivePercent = offset * 0.35f;
        
		double listSpace = fivePercent;
		double listX = imgX + tenPercent;
        double listY = imgY + thirtyFivePercent + thirtyFivePercent + tenPercent;
		
        double messageTopX = imgX;
        double messageTopY = imgY + tenPercent + tenPercent;
        
        //gc.fillText(message, messageTopX, messageTopY);
        //gc.strokeText(message, messageTopX, messageTopY);
        
        
        for (int i = 0; i < options.size(); i++) {
            //gc.fillText(options[i], listX, listY + (i * listSpace));
           // gc.strokeText(options[i], listX, listY + (i * listSpace));
            if (i == optionSelected) {
                gc.fillText("->", listX - fivePercent, listY + (i * listSpace));
                gc.strokeText("->", listX - fivePercent, listY + (i * listSpace));
            }
        }
        

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
