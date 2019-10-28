/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

/**
 *
 * @author patrick
 */
public class PowerAttack extends Attack {

    public PowerAttack(String name, String attackText, DungeonCharacter owner) {
        super(name, attackText, owner);
    }

    @Override
    public String attackOpponent(DungeonCharacter opponent) {
        String ret = "";
        if (Math.random() <= .4) {
            int blowPoints = (int) (Math.random() * 76) + 100;
            ret += owner.getName() + " lands a " + getAttackText() + " for " + blowPoints
                    + " damage!\n";
            opponent.subtractHitPoints(blowPoints);
        }//end blow succeeded
        else {
            ret += owner.getName() + " failed to land a " + getAttackText() + "\n";
 
        }//blow failed
        return ret;
    }

}
