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

public class Skeleton extends Monster {

    public Skeleton() {
        super("Sargath the Skeleton", 100, 3, .8, .3, 30, 50, 30, 50);
        withAttack(new GenericAttack("Blade", "  slices his rusty blade at  ", this));
        setCharacterImage(new Image("images/skeleton.png"));
    }//end constructor

//    public void attack(DungeonCharacter opponent) {
//        System.out.println(getName() + " slices his rusty blade at "
//                + opponent.getName() + ":");
//        super.attack(opponent);
//
//    }//end override of attack

}//end class Skeleton
