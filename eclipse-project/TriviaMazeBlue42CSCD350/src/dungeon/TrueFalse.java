/*
 * Course:	CSCD350
 * Term:	Fall 2019
 * Team:	Blue42
 *
 */


/**
 * @author Team Blue42
 */

package dungeon;

import javafx.scene.canvas.Canvas;

public class TrueFalse extends Question
{	
	private int correctBtn;
	
	public TrueFalse(int correctAnswer, String question, String explanation)
	{
		this.question = wordWrap(question);
		this.explanation = explanation;
		this.correctBtn = correctAnswer - 1;
		this.options.add("True");
		this.options.add("False");
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
		this.QuestionCorrect = (optionSelected == correctBtn);
		this.QuestionSubmitted.set(true);
	}
	
	@Override
	public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
		super.draw(imgX, imgY, x, y, offset, canvas);
		drawPrompt(imgX, imgY, x, y, offset, canvas);
		drawMenu(imgX, imgY, x, y, offset, canvas);
	}
}