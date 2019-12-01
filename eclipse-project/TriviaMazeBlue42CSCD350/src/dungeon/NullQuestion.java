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


	/**
	 * 
	 */
	public NullQuestion() {
		this.question = "Lock or Open the door?";
		this.options.add("Lock");
		this.options.add("Open");
	}
	
	@Override
	public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(imgX, imgY, x, y, offset, canvas);
		drawPrompt(imgX, imgY, x, y, offset, canvas);
		drawMenu(imgX, imgY, x, y, offset, canvas);
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
		this.QuestionCorrect = (optionSelected == 1);
		this.QuestionSubmitted.set(true);
	}

}
