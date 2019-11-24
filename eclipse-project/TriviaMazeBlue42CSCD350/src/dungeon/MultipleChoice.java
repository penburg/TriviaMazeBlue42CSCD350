package dungeon;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MultipleChoice extends Question
{
	private String[] options;
	private int optionSelected = 0;
	private int correctBtn;
	
	public MultipleChoice(String btn1, String btn2, String btn3, String btn4, int correctAnswer, String question, String explanation)
	{
		this.options = new String[4];
		this.question = question;
		this.explanation = explanation;
		this.options[0] = btn1;
		this.options[1] = btn2;
		this.options[2] = btn3;
		this.options[3] = btn4;
		this.correctBtn = correctAnswer - 1;
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
		this.QuestionCorrect = (optionSelected == correctBtn);
		this.QuestionSubmitted.set(true);
	}
	
	@Override
	public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
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
        
        gc.fillText(this.question, messageTopX, messageTopY);
        gc.strokeText(this.question, messageTopX, messageTopY);
        
        
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
	
}