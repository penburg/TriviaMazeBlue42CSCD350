/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import javafx.scene.canvas.Canvas;

/**
 *
 * @author penburg
 */
public interface Drawable {
    public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas);
}
