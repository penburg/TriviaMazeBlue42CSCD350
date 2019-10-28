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
public class IncreaseHitPoint extends Attack {

    private final int MIN_ADD = 25;
    private final int MAX_ADD = 50;

    public IncreaseHitPoint(String name, String attackText, DungeonCharacter owner) {
        super(name, attackText, owner);
    }

    @Override
    public String attackOpponent(DungeonCharacter opponent) {
        int hPoints;

        hPoints = (int) (Math.random() * (MAX_ADD - MIN_ADD + 1)) + MIN_ADD;
        owner.addHitPoints(hPoints);
        String ret;
        ret = owner.getName() + " added [" + hPoints + "] points.\n"
                + "Total hit points remaining are: "
                + owner.getHitPoints();
        
        return ret;
    }

}
