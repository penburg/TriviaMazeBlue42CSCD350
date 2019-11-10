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

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Question implements Drawable{

	@Override
	public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Set background color
        gc.setFill(Color.FUCHSIA);
        //Fill background
        gc.fillRect(imgX + (x * offset), imgY + (y * offset), offset, offset);
        
        //Draw a black ? somewhere near the middle
        gc.setFill(Color.BLACK);
        gc.strokeText("?", imgX + (x * offset) + (offset / 2), imgY + (y * offset) + (offset / 2));
	}
	
	public abstract void onUp();
	
	public abstract void onDown();
	
	public abstract void onEnter();

}
