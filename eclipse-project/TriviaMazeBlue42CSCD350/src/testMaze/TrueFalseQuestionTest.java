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
import dungeon.TrueFalse;
import javafx.embed.swing.JFXPanel;

class TrueFalseQuestionTest {

	@BeforeEach
	public void initialize() {
		//Initialize graphics
		//https://stackoverflow.com/questions/27839441/internal-graphics-not-initialized-yet-javafx
		JFXPanel jfxPanel = new JFXPanel();
	}

	@Test
	void testCreation() {
		
		Question q = new TrueFalse(1, "Test Question", "Your Wrong");
		assertTrue(q.getExplanation().equals("Your Wrong"), "Explanation is not blank");
	}

	@Test
	void testOnUp() {
		Question q = new TrueFalse(1, "Test Question", "Your Wrong");
		for(int i = 0; i < 100; i++) {
			q.onUp();
		}
	}

	@Test
	void testOnDown() {
		Question q = new TrueFalse(1, "Test Question", "Your Wrong");
		for(int i = 0; i < 100; i++) {
			q.onDown();
		}
	}
	
	@Test
	void testOnEnter() {
		Question q = new TrueFalse(1, "Test Question", "Your Wrong");
		for(int i = 0; i < 100; i++) {
			q.onEnter();
		}
	}
	
	@Test
	void testOnDownAndEnterToAnswer() {
		Question q = new TrueFalse(1, "Test Question", "Your Wrong");
		q.onEnter();
		assertTrue(q.isQuestionCorrect(), "Answer Incorrect");
	}

}
