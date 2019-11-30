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

public class Warrior extends Hero {

	public Warrior() {

		super("Warrior", 125, 4, .8, 35, 60, .2);
		String imgName = "images/m-warrior.png";
		setCharacterImage(new Image(imgName));

	}//end constructor


}//end Hero class
