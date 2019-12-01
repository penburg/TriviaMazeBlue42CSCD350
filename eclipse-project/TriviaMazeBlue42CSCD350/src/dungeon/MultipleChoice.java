package dungeon;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MultipleChoice extends Question
{
	
	private int correctBtn;
	
	public MultipleChoice(String btn1, String btn2, String btn3, String btn4, int correctAnswer, String question, String explanation)
	{
		this.question = question;
		this.explanation = explanation;
		this.options.add(btn1);
		this.options.add(btn2);
		this.options.add(btn3);
		this.options.add(btn4);
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