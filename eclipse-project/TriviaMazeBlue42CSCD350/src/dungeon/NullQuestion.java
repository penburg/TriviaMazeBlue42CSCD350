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

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class NullQuestion extends Question {

	private final String message = "Lock or Open the door?";
	private final String[] options = {"Lock", "Open"};
	private int optionSelected = 0;

	
	
	@Override
	public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(imgX, imgY, x, y, offset, canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.save();
		gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.setLineWidth(lineWidth);
        gc.setFont(new Font(fontString, fontSize * offset));
        
		double tenPercent = offset * 0.1f;
        double fivePercent = offset * 0.05f;
        double thirtyFivePercent = offset * 0.35f;
        
		double listSpace = fivePercent;
		double listX = imgX + tenPercent;
        double listY = imgY + thirtyFivePercent + thirtyFivePercent + tenPercent;
		
        double messageTopX = imgX;
        double messageTopY = imgY + tenPercent + tenPercent;
        
        gc.fillText(message, messageTopX, messageTopY);
        gc.strokeText(message, messageTopX, messageTopY);
        
        
        for (int i = 0; i < options.length; i++) {
            gc.fillText(options[i], listX, listY + (i * listSpace));
            gc.strokeText(options[i], listX, listY + (i * listSpace));
            if (i == optionSelected) {
                gc.fillText("->", listX - fivePercent, listY + (i * listSpace));
                gc.strokeText("->", listX - fivePercent, listY + (i * listSpace));
            }
        }
        
        gc.restore();
	}

	@Override
	public void onUp() {
		optionSelected = (optionSelected - 1 + options.length) % options.length;
	}

	@Override
	public void onDown() {
		optionSelected = (optionSelected + 1 + options.length) % options.length;
	}

	@Override
	public void onEnter() {
		this.QuestionCorrect = (optionSelected == 1);
		this.QuestionSubmitted.set(true);
	}

}