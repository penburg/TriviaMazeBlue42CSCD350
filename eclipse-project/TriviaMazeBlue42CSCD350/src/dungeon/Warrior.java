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

import java.io.IOException;
import java.io.Serializable;

import javafx.scene.image.Image;

public class Warrior extends Hero implements Serializable{

	private static final long serialVersionUID = 1L;

	public Warrior() {

		String imgName = "images/m-warrior.png";
		setCharacterImage(new Image(imgName));

	}//end constructor

    private void readObject(java.io.ObjectInputStream in)
    	     throws IOException, ClassNotFoundException{
    	String imgName = "images/m-warrior.png";
		setCharacterImage(new Image(imgName));
    }

}//end Hero class
