/*
 * Course:	CSCD350
 * Term:	Fall 2019
 * Team:	Blue42
 * 
 */

/**
 * @author Patrick Enburg <patrick@enburg.info>
 */

package testMaze;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dungeon.DungeonCharacter;
import dungeon.Warrior;
import javafx.embed.swing.JFXPanel;

class DungeonCharacterTest {

	private DungeonCharacter Bob;

	@BeforeEach
	public void initialize() {
		//Initialize graphics
		//https://stackoverflow.com/questions/27839441/internal-graphics-not-initialized-yet-javafx
		JFXPanel jfxPanel = new JFXPanel();
		Bob = new Warrior();
	}
	
	@Test
	public void testIsAlive() {
		assertTrue(Bob.isAlive(), "Character is not alive");
		Bob.kill();
		assertFalse(Bob.isAlive(), "Character is not dead");
	}

}
