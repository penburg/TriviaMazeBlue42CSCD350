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

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class Question implements Drawable{
	private final static Image bgImage = new Image("file:images/School_Board.jpg");
	protected boolean QuestionCorrect;
	protected BooleanProperty QuestionSubmitted;
    protected final double lineWidth = 1.0f;
    protected final String fontString = "System";
    protected final double fontSize = .05;
    protected String question, explanation;
    protected ArrayList<String> options;
    protected int optionSelected = 0;
	
	public Question() {
		this.QuestionCorrect = false;
		this.QuestionSubmitted = new SimpleBooleanProperty(false);
		this.question = "";
		this.explanation = "";
		this.options = new ArrayList<String>();
	}
	
	@Override
	public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.save();
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.setLineWidth(lineWidth);
        gc.setFont(new Font(fontString, fontSize * offset));
        gc.drawImage(bgImage, imgX, imgY, offset, offset);
        gc.restore();
	}
	
	public String getExplanation()
	{
		return this.explanation;
	}
	
	public abstract void onUp();
	public abstract void onDown();
	public abstract void onEnter();
	
	public boolean isQuestionCorrect() {
		return QuestionCorrect;
	}

	public BooleanProperty getQuestionSubmitted() {
		return QuestionSubmitted;
	}
	
	protected void drawMenu(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
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
        double listY = imgY + thirtyFivePercent + thirtyFivePercent;
        
        for (int i = 0; i < options.size(); i++) {
            gc.fillText(options.get(i), listX, listY + (i * listSpace));
            gc.strokeText(options.get(i), listX, listY + (i * listSpace));
            if (i == optionSelected) {
                gc.fillText("->", listX - fivePercent, listY + (i * listSpace));
                gc.strokeText("->", listX - fivePercent, listY + (i * listSpace));
            }
        }
        gc.restore();
	}
	
	protected void drawPrompt(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.save();
		gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.setLineWidth(lineWidth);
        gc.setFont(new Font(fontString, fontSize * offset));
		double tenPercent = offset * 0.1f;
        double fivePercent = offset * 0.05f; 		
        double messageTopX = imgX + tenPercent;
        double messageTopY = imgY + tenPercent + fivePercent;
        gc.fillText(this.question, messageTopX, messageTopY);
        gc.strokeText(this.question, messageTopX, messageTopY); 
        gc.restore();
	}
	
	/**
	 * Wraps a string at the last whitespace before 30 characters
	 * 
	 * @param s String to be wrapped
	 * @return a Wrapped String
	 */
	protected String wordWrap(String s) {
		int charCount = 0;
		String newPrompt = "";
		for(int i = 0; i < s.length(); i++) {
			if(charCount >= 30) {
				int lastSpace = newPrompt.lastIndexOf(' ');
				int lastNewLine = newPrompt.lastIndexOf('\n');
				if(lastSpace > 0 && lastSpace > lastNewLine) {
					String tmp = newPrompt.substring(lastSpace + 1);
					newPrompt = newPrompt.substring(0, lastSpace);
					newPrompt += "\n";
					newPrompt += tmp;
					charCount = tmp.length();
				}else {
					newPrompt += "\n";
					charCount = 0;
				}
			}
			if(s.charAt(i) == '\n') {
				charCount = 0;
			}
			newPrompt += s.charAt(i);
			charCount ++;
		}
		return newPrompt;
	}
	public void onKeyPress(KeyEvent keyEvent) {
		
	}
}