/*
 * Course:	CSCD350
 * Term:	Fall 2019
 * Team:	Blue42
 * 
 */

/**
 * @author Patrick Enburg <patrick@enburg.info>
 */

package dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author Patrick Enburg <patrick@enburg.info
 */
public abstract class Question implements Drawable{
	protected boolean QuestionCorrect;
	protected BooleanProperty QuestionSubmitted;
    protected final double lineWidth = 1.0f;
    protected final String fontString = "System";
    protected final double fontSize = .05;
	
	public Question() {
		this.QuestionCorrect = false;
		this.QuestionSubmitted = new SimpleBooleanProperty(false);
	}


	@Override
	public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //save gc fills and strokes
        gc.save();
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.setLineWidth(lineWidth);
        gc.setFont(new Font(fontString, fontSize * offset));
        //Set background color
        gc.setFill(Color.FUCHSIA);
        //Fill background
        gc.fillRect(imgX + (x * offset), imgY + (y * offset), offset, offset);
        
        //Draw a black ? somewhere near the middle
        gc.setStroke(Color.BLACK);
        gc.fillText("?", imgX + (x * offset) + (offset / 2), imgY + (y * offset) + (offset / 2));
        gc.strokeText("?", imgX + (x * offset) + (offset / 2), imgY + (y * offset) + (offset / 2));
        
        //restore gc state
        gc.restore();
	}
	
	/**
	 * Passes the up keystroke event
	 */
	public abstract void onUp();
	
	/**
	 * Passes the down keystroke event
	 */
	public abstract void onDown();
	
	/**
	 * Passes the enter / return keystroke event
	 */
	public abstract void onEnter();


	/**
	 * @return the questionCorrect
	 */
	public boolean isQuestionCorrect() {
		return QuestionCorrect;
	}


	/**
	 * @return the questionSubmitted
	 */
	public BooleanProperty getQuestionSubmitted() {
		return QuestionSubmitted;
	}

}
