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

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class DungeonCharacter implements Drawable {

    private ArrayList<Attack> attackList;
    private String name;
    private int hitPoints;
    private int maxHitPoints;
    private int attackSpeed;
    private double chanceToHit;
    private int damageMin, damageMax;
    private Image CharacterImage;

//-----------------------------------------------------------------
//explicit constructor to initialize instance variables -- it is called
// by derived classes
    public DungeonCharacter(String name, int hitPoints, int attackSpeed,
            double chanceToHit, int damageMin, int damageMax) {

        this.name = name;
        this.hitPoints = hitPoints;
        this.maxHitPoints = hitPoints;
        this.attackSpeed = attackSpeed;
        this.chanceToHit = chanceToHit;
        this.damageMin = damageMin;
        this.damageMax = damageMax;
        this.attackList = new ArrayList();
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
    public int getHitPoints() {
        return hitPoints;
    }//end getHitPoints
//-----------------------------------------------------------------

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public final int getAttackSpeed() {
        return attackSpeed;
    }//end getAttackSpeed

    public final double getChanceToHit() {
        return chanceToHit;
    }

    public final int getDamageMin() {
        return damageMin;
    }

    public final int getDamageMax() {
        return damageMax;
    }

    public final void withAttack(Attack a) {
        this.attackList.add(a);
    }

    
    public void addHitPoints(int hitPoints) {
        if (hitPoints <= 0) {
            System.out.println("Hitpoint amount must be positive.");
        } else {
            this.hitPoints += hitPoints;
            //System.out.println("Remaining Hit Points: " + hitPoints);

        }
    }//end addHitPoints method

   
    public String subtractHitPoints(int hitPoints) {
        String ret = "";
        if (hitPoints < 0) {
            ret += "Hitpoint amount must be positive.";
        } else if (hitPoints > 0) {
            this.hitPoints -= hitPoints;
            if (this.hitPoints < 0) {
                this.hitPoints = 0;
            }
            ret += getName() + " hit  for <" + hitPoints + "> points damage.\n";
            ret += getName() + " now has " + getHitPoints() + " hit points remaining.";

        }//end else if

        if (this.hitPoints == 0) {
           ret += name + " has been killed :-(";
        }
        return ret;
    }//end method

    
    public boolean isAlive() {
        return (hitPoints > 0);
    }//end isAlive method

    public abstract String attack(DungeonCharacter opponent);


    public final void setName(String name) {
        this.name = name;
    }

//-----------------------------------------------------------------
    protected ArrayList<Attack> getAttackList() {
        return attackList;
    }
    
    public void kill() {
    	this.hitPoints = 0;
    }

    @Override
    public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //canvas.getGraphicsContext2D().fillRect(imgX + (x * offset), imgY + (y * offset), offset, offset);
        gc.drawImage(CharacterImage, imgX + (x * offset), imgY + (y * offset),  offset, offset);
    }
}//end class Character
