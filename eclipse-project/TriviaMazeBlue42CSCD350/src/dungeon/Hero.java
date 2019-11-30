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

import java.util.Random;
import java.util.prefs.Preferences;

public abstract class Hero extends DungeonCharacter {

    protected double chanceToBlock;
    protected int numTurns;
    private int numPotionsHeal;
    private int numPotionsVision;
    private Random rand;

    @Override
    public String toString() {
        String ret = "";
        ret += getName();
        ret += " Vision potions: " + this.getNumPotionsVision();
        return ret;
    }

//-----------------------------------------------------------------
//calls base constructor and gets name of hero from user
    public Hero(String type, int hitPoints, int attackSpeed,
            double chanceToHit, int damageMin, int damageMax,
            double chanceToBlock) {
        super(type, hitPoints, attackSpeed, chanceToHit, damageMin, damageMax);
        this.chanceToBlock = chanceToBlock;

        Preferences prefs = Preferences.userNodeForPackage(getClass());
        setName(prefs.get("Name", "Jack"));
        this.rand = new Random();

    }

    public void addPotionHeal() {
        this.numPotionsHeal++;
    }

    public void addPotionVision() {
        this.numPotionsVision++;
    }

    public int usePotionHeal() {
        if (numPotionsHeal > 0) {
            int ret = rand.nextInt(10) + 5;
            addHitPoints(ret);
            numPotionsHeal--;
            return ret;
        } else {
            return 0;
        }
    }

    public boolean usePotionVision() {
        if (numPotionsVision > 0) {
            numPotionsVision--;
            return true;
        } else {
            return false;
        }
    }

    public int getNumPotionsHeal() {
        return numPotionsHeal;
    }

    public int getNumPotionsVision() {
        return numPotionsVision;
    }
    
    public boolean defend() {
        return Math.random() <= chanceToBlock;

    }//end defend method

    public String subtractHitPoints(int hitPoints) {
        String ret = "";
        if (defend()) {
            ret += getName() + " BLOCKED the attack!";
        } else {
            ret += super.subtractHitPoints(hitPoints);
        }
        return ret;
    }//end method
    
    
    @Override
    public final String attack(DungeonCharacter opponent) {
        return getAttackList().get(0).attackOpponent(opponent);
        //super.attack(opponent); //To change body of generated methods, choose Tools | Templates.
    }
}//end Hero class
