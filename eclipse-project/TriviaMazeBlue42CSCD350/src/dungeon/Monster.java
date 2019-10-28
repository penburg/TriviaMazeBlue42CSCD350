/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 *
 * @author
 * @version 1.0
 */
package dungeon;

public abstract class Monster extends DungeonCharacter {

    protected double chanceToHeal;
    protected int minHeal, maxHeal;

//-----------------------------------------------------------------
    public Monster(String name, int hitPoints, int attackSpeed,
            double chanceToHit, double chanceToHeal,
            int damageMin, int damageMax,
            int minHeal, int maxHeal) {
        super(name, hitPoints, attackSpeed, chanceToHit, damageMin, damageMax);
        this.chanceToHeal = chanceToHeal;
        this.maxHeal = maxHeal;
        this.minHeal = minHeal;

    }//end monster construcotr

//-----------------------------------------------------------------
    public String heal() {
        boolean canHeal;
        int healPoints;

        canHeal = (Math.random() <= chanceToHeal) && (getHitPoints() > 0);
        String ret = "";
        if (canHeal) {
            healPoints = (int) (Math.random() * (maxHeal - minHeal + 1)) + minHeal;
            addHitPoints(healPoints);
            ret += getName() + " healed itself for " + healPoints + " points."
                    + "Total hit points remaining are: " + getHitPoints();
            
        }//end can heal
        return ret;
    }//end heal method

//-----------------------------------------------------------------
    public String subtractHitPoints(int hitPoints) {
        String ret = "";
        ret += super.subtractHitPoints(hitPoints);
        ret += heal();
        return ret;
    }//end method

    @Override
    public final String attack(DungeonCharacter opponent) {
        return getAttackList().get(0).attackOpponent(opponent);
        //super.attack(opponent); //To change body of generated methods, choose Tools | Templates.
    }

}//end Monster class
