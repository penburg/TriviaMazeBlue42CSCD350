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
public class SneekAttack extends Attack {

    public SneekAttack(String name, String attackText, DungeonCharacter owner) {
        super(name, attackText, owner);
    }

    @Override
    public String attackOpponent(DungeonCharacter opponent) {
        String ret = "";
        double surprise = Math.random();
        if (surprise <= .4) {
            ret += "Surprise attack was successful!\n"
                    + owner.getName() + " gets an additional turn.\n";
            ret += owner.attack(opponent);
            return ret;
        }//end surprise
        else if (surprise >= .9) {
            ret += "Uh oh! " + opponent.getName() + " saw you and"
                    + " blocked your attack!\n";
            return ret;
        } else {
            ret += attackOpponent(opponent);
            return ret;
        }
    }
}
