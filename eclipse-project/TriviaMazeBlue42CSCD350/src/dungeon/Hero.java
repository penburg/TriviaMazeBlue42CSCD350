/**
 * Title: Hero.java
 *
 * Description: Abstract base class for a hierarchy of heroes.  It is derived
 *  from DungeonCharacter.  A Hero has battle choices: regular attack and a
 *  special skill which is defined by the classes derived from Hero.
 *
 *  class variables (all are directly accessible from derived classes):
 *    chanceToBlock -- a hero has a chance to block an opponents attack
 *    numTurns -- if a hero is faster than opponent, their is a possibility
 *                for more than one attack per round of battle
 *
 *  class methods (all are public):
 *    public Hero(String name, int hitPoints, int attackSpeed,
 * double chanceToHit, int damageMin, int damageMax,
 * double chanceToBlock)
 * public void readName()
 * public boolean defend()
 * public void subtractHitPoints(int hitPoints)
 * public void battleChoices(DungeonCharacter opponent)
 *
 * Copyright:    Copyright (c) 2001
 * Company:
 *
 * @author
 * @version 1.0
 */
package dungeon;

import java.util.prefs.Preferences;

public abstract class Hero extends DungeonCharacter {

	private static final long serialVersionUID = 1L;
	private int numPotionsVision;
	
    @Override
    public String toString() {
        String ret = "";
        ret += getName();
        ret += " Vision potions: " + this.getNumPotionsVision();
        return ret;
    }

    public Hero() {
    	numPotionsVision = 0;
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        setName(prefs.get("Name", "Jack"));
    }
    
    public void addPotionVision() {
        this.numPotionsVision++;
    }

    public boolean usePotionVision() {
        if (numPotionsVision > 0) {
            numPotionsVision--;
            return true;
        }else {
            return false;
        }
    }

     public int getNumPotionsVision() {
        return numPotionsVision;
    }
}
