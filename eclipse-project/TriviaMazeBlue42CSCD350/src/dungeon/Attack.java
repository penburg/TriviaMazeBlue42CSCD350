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
public abstract class Attack {

    protected String name;
    protected String attackText;
    protected DungeonCharacter owner;

    public Attack(String name, String attackText, DungeonCharacter owner) {
        this.name = name;
        this.attackText = attackText;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getAttackText() {
        return attackText;
    }

    public String attackOpponent(DungeonCharacter opponent) {
        boolean canAttack;
        int damage;
        String ret = "";
        canAttack = Math.random() <= owner.getChanceToHit();

        if (canAttack) {
            damage = (int) (Math.random() * (owner.getDamageMax() - owner.getDamageMin() + 1))
                    + owner.getDamageMin();
            ret += opponent.subtractHitPoints(damage);

            //System.out.println();
        }//end if can attack
        else {

            ret += owner.getName() + "'s attack on " + opponent.getName()
                    + " failed!\n";
            //System.out.println();
        }//end else
        //Attack command succeded
        return ret;
    }
}
