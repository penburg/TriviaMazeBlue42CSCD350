package dungeon;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ShortAnswer extends Question
{
	private String correctAnswer;
	private String shortAnswer;

	public ShortAnswer(String shortAnswer, String question, String explanation)
	{
		this.question = wordWrap(question);
		this.explanation = explanation;
		this.correctAnswer = shortAnswer;
		this.shortAnswer = "";
	}

	@Override
	public void onUp() {
		
	}
	
	@Override
	public void onDown() {
		
	}

	@Override
	public void onEnter() {
		this.QuestionCorrect = this.correctAnswer.equalsIgnoreCase(this.shortAnswer);
		this.QuestionSubmitted.set(true);
	}

	@Override
	public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
		super.draw(imgX, imgY, x, y, offset, canvas);
		drawPrompt(imgX, imgY, x, y, offset, canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.save();
		gc.setStroke(Color.BLACK);
		gc.setFill(Color.WHITE);
		gc.setLineWidth(lineWidth);
		gc.setFont(new Font(fontString, fontSize * offset));
		double tenPercent = offset * 0.1f;
		double thirtyFivePercent = offset * 0.35f;
		double listX = imgX + tenPercent;
		double listY = imgY + thirtyFivePercent + thirtyFivePercent;
		gc.fillText(this.shortAnswer, listX, listY);
        gc.strokeText(this.shortAnswer, listX, listY);
		gc.restore();
	}

	@Override
	public void onKeyPress(KeyEvent keyEvent) {
		if(!keyEvent.isAltDown() && !keyEvent.isControlDown() && !keyEvent.isMetaDown() && !keyEvent.isShortcutDown()) {
			if(keyEvent.getCode().getName().length() == 1) {
				this.shortAnswer += keyEvent.getCode().getName();
			}else if(keyEvent.getCode() == KeyCode.BACK_SPACE && !this.shortAnswer.isEmpty()) {
				this.shortAnswer = this.shortAnswer.substring(0, this.shortAnswer.length() - 1);
			}
		}
	}
}



