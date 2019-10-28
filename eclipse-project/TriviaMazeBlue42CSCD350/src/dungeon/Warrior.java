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

import java.util.prefs.Preferences;
import javafx.scene.image.Image;

public class Warrior extends Hero {

    public Warrior() {

        super("Warrior", 125, 4, .8, 35, 60, .2);
        withAttack(new PowerAttack("Crushing Blow on Opponent", "CRUSHING BLOW", this));
        withAttack(new GenericAttack("Attack Opponent", " swings a mighty sword at ", this));
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        String gender = prefs.get("Gender", "Male");
        String imgName = (gender.equals("Male")) ? "images/m-warrior.png" : "images/f-warrior.png";
        setCharacterImage(new Image(imgName));

    }//end constructor

//    public void crushingBlow(DungeonCharacter opponent) {
//        if (Math.random() <= .4) {
//            int blowPoints = (int) (Math.random() * 76) + 100;
//            System.out.println(getName() + " lands a CRUSHING BLOW for " + blowPoints
//                    + " damage!");
//            opponent.subtractHitPoints(blowPoints);
//        }//end blow succeeded
//        else {
//            System.out.println(getName() + " failed to land a crushing blow");
//            System.out.println();
//        }//blow failed
//
//    }//end crushingBlow method

//    public void attack(DungeonCharacter opponent) {
//        System.out.println(getName() + " swings a mighty sword at "
//                + opponent.getName() + ":");
//        super.attack(opponent);
//    }//end override of attack method
//
//    public void battleChoices(DungeonCharacter opponent) {
//        int choice;
//
//        super.battleChoices(opponent);
//
//        do {
//            System.out.println("1. Attack Opponent");
//            System.out.println("2. Crushing Blow on Opponent");
//            System.out.print("Choose an option: ");
//            choice = Keyboard.readInt();
//
//            switch (choice) {
//                case 1:
//                    attack(opponent);
//                    break;
//                case 2:
//                    crushingBlow(opponent);
//                    break;
//                default:
//                    System.out.println("invalid choice!");
//            }//end switch
//
//            numTurns--;
//            if (numTurns > 0) {
//                System.out.println("Number of turns remaining is: " + numTurns);
//            }
//
//        } while (numTurns > 0);
//
//    }//end battleChoices method

}//end Hero class
