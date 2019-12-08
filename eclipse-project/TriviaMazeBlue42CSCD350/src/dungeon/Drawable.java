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

public interface Drawable {
    public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas);
}
