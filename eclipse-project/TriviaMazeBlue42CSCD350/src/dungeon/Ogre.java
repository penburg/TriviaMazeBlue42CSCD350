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

import javafx.scene.image.Image;

public class Ogre extends Monster {

    public Ogre() {
        super("Oscar the Ogre", 200, 2, .6, .1, 30, 50, 30, 50);
        withAttack(new GenericAttack("Club", " slowly swings a club toward's ", this));
        setCharacterImage(new Image("images/ogre.png"));

    }//end constructor

//    public void attack(DungeonCharacter opponent) {
//        System.out.println(getName() + " slowly swings a club toward's "
//                + opponent.getName() + ":");
//        super.attack(opponent);
//
//    }//end override of attack
}//end Monster class
