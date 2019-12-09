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
import dungeon.Question;
import dungeon.ShortAnswer;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

class ShortAnswerTest {

	@BeforeEach
	public void initialize() {
		//Initialize graphics
		//https://stackoverflow.com/questions/27839441/internal-graphics-not-initialized-yet-javafx
		JFXPanel jfxPanel = new JFXPanel();
	}

	@Test
	void testCreation() {
		
		Question q = new ShortAnswer("ANSWER", "Test Question", "Your Wrong!!");
		assertTrue(q.getExplanation().equals("Your Wrong!!"), "Explanation is incorrect");
	}


	@Test
	void testOnEnter() {
		Question q = new ShortAnswer("ANSWER", "Test Question", "Your Wrong!!");
		for(int i = 0; i < 100; i++) {
			q.onEnter();
		}
	}
	
	@Test
	void testOnKeyPress() {
		Question q = new ShortAnswer("ANSWER", "Test Question", "Your Wrong!!");
		KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "A", "A", KeyCode.A, false, false, false, false);
		q.onKeyPress(keyEvent);
	}
	
	@Test
	void testOnTypeAndEnterToAnswer() {
		Question q = new ShortAnswer("ANSWER", "Test Question", "Your Wrong!!");
		KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "A", "A", KeyCode.A, false, false, false, false);
		q.onKeyPress(keyEvent);
		keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "N", "N", KeyCode.N, false, false, false, false);
		q.onKeyPress(keyEvent);
		keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "S", "S", KeyCode.S, false, false, false, false);
		q.onKeyPress(keyEvent);
		keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "W", "W", KeyCode.W, false, false, false, false);
		q.onKeyPress(keyEvent);
		keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "E", "E", KeyCode.E, false, false, false, false);
		q.onKeyPress(keyEvent);
		keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "R", "R", KeyCode.R, false, false, false, false);
		q.onKeyPress(keyEvent);
		q.onEnter();
		assertTrue(q.isQuestionCorrect(), "Answer Incorrect");
	}

}
