/*
 * Course:	CSCD350
 * Term:	Fall 2019
 * Team:	Blue42
 *
 */


/**
 * @author Team Blue42
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
	}

    private void readObject(java.io.ObjectInputStream in)
    	     throws IOException, ClassNotFoundException{
    	String imgName = "images/m-warrior.png";
		setCharacterImage(new Image(imgName));
    }
}
