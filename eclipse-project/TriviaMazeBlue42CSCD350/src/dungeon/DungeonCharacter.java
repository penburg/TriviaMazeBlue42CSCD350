/**
 * Title: DungeonCharacter.java
 *
 * Description: Abstract Base class for inheritance hierarchy for a
 *              role playing game
 *
 *  class variables (all will be directly accessible from derived classes):
 *    name (name of character)
 *    hitPoints (points of damage a character can take before killed)
 *    attackSpeed (how fast the character can attack)
 *    chanceToHit (chance an attack will strike the opponent)
 *    damageMin, damageMax (range of damage the character can inflict on
 *     opponent)
 *
 *  class methods (all are directly accessible by derived classes):
 *    DungeonCharacter(String name, int hitPoints, int attackSpeed,
 * double chanceToHit, int damageMin, int damageMax)
 * public String getName()
 * public int getHitPoints()
 * public int getAttackSpeed()
 * public void addHitPoints(int hitPoints)
 * public void subtractHitPoints(int hitPoints) -- this method will be
 * overridden
 * public boolean isAlive()
 * public void attack(DungeonCharacter opponent) -- this method will be
 * overridden
 *
 * Copyright:    Copyright (c) 2001
 * Company:
 *
 * @author
 * @version 1.0
 */
package dungeon;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class DungeonCharacter implements Drawable {

    private String name;
    private boolean alive;
    private Image CharacterImage;

//-----------------------------------------------------------------
//explicit constructor to initialize instance variables -- it is called
// by derived classes
    public DungeonCharacter() {
    	this.alive = true;
        this.CharacterImage = new Image("images/person.png");
    }//end constructor

    public final void setCharacterImage(Image CharacterImage) {
        this.CharacterImage = CharacterImage;
    }

//-----------------------------------------------------------------
    public final String getName() {
        return name;
    }//end getName

//-----------------------------------------------------------------

    
    public boolean isAlive() {
        return alive;
    }//end isAlive method
    
   public final void setName(String name) {
        this.name = name;
    }

    
    public void kill() {
    	this.alive = false;
    }

    @Override
    public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //canvas.getGraphicsContext2D().fillRect(imgX + (x * offset), imgY + (y * offset), offset, offset);
        gc.drawImage(CharacterImage, imgX + (x * offset), imgY + (y * offset),  offset, offset);
    }
}//end class Character
