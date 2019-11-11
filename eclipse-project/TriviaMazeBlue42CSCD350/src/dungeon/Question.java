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

/**
 * @author Patrick Enburg <patrick@enburg.info
 */
public abstract class Question implements Drawable{
	protected boolean QuestionCorrect;
	protected BooleanProperty QuestionSubmitted;
	
	public Question() {
		this.QuestionCorrect = false;
		this.QuestionSubmitted = new SimpleBooleanProperty(false);
	}


	@Override
	public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Set background color
        gc.setFill(Color.FUCHSIA);
        //Fill background
        gc.fillRect(imgX + (x * offset), imgY + (y * offset), offset, offset);
        
        //Draw a black ? somewhere near the middle
        gc.setFill(Color.BLACK);
        gc.fillText("?", imgX + (x * offset) + (offset / 2), imgY + (y * offset) + (offset / 2));
        gc.strokeText("?", imgX + (x * offset) + (offset / 2), imgY + (y * offset) + (offset / 2));
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
