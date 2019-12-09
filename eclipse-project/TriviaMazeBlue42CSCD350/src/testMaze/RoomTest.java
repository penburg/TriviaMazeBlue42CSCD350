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

import dungeon.Room;
import dungeon.Room.DoorState;
import javafx.embed.swing.JFXPanel;

class RoomTest {
	private Room room;

	@BeforeEach
	public void initialize() {
		//Initialize graphics
		//https://stackoverflow.com/questions/27839441/internal-graphics-not-initialized-yet-javafx
		JFXPanel jfxPanel = new JFXPanel();
		room = new Room(null, null);
	}

	@Test
	public void testGetHeroLoc() {
		int[] testLoc = {0,0};
		assertArrayEquals(testLoc, room.getHeroLoc(), "Hero location wrong");
	}

	@Test
	public void testDoorState() {
		DoorState[] doors = {DoorState.CLOSED, DoorState.CLOSED, DoorState.CLOSED, DoorState.CLOSED};
		assertArrayEquals(doors, room.getDoors(), "Door State incorrect");
	}
	
	@Test
	public void testExit() {
		room.setIsExit();
		assertTrue(room.isExit(), "Not an exit");
	}
}
