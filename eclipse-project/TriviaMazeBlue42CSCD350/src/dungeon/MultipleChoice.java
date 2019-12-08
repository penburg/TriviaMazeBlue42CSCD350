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

public class MultipleChoice extends Question
{
	private int correctBtn;
	
	public MultipleChoice(String btn1, String btn2, String btn3, String btn4, int correctAnswer, String question, String explanation)
	{
		this.question = wordWrap(question);
		this.explanation = explanation;
		if(!btn1.isEmpty()) {
			options.add(btn1);
		}
		if(!btn2.isEmpty()) {
			options.add(btn2);		
		}
		if(!btn3.isEmpty()) {
			options.add(btn3);
		}
		if(!btn4.isEmpty()) {
			options.add(btn4);
		}
		this.correctBtn = correctAnswer - 1;
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