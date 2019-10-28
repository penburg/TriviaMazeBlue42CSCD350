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
public class GenericAttack extends Attack {

    public GenericAttack(String name, String attackText, DungeonCharacter owner) {
        super(name, attackText, owner);
    }

    @Override
    public String attackOpponent(DungeonCharacter opponent) {
        String ret = "";
        ret += owner.getName() + attackText + opponent.getName() + ":\n";
        ret += super.attackOpponent(opponent) ;
        return ret;
    }

}
