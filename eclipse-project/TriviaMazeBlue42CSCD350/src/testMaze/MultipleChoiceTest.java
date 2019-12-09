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
import dungeon.MultipleChoice;
import dungeon.Question;
import javafx.embed.swing.JFXPanel;

class MultipleChoiceTest {

	@BeforeEach
	public void initialize() {
		//Initialize graphics
		//https://stackoverflow.com/questions/27839441/internal-graphics-not-initialized-yet-javafx
		JFXPanel jfxPanel = new JFXPanel();
	}

	@Test
	void testCreation() {
		
		Question q = new MultipleChoice("Opt1", "Opt2", "Opt3", "Opt4", 4, "Test Question", "Your Wrong!!");
		assertTrue(q.getExplanation().equals("Your Wrong!!"), "Explanation is incorrct");
	}

	@Test
	void testOnUp() {
		Question q = new MultipleChoice("Opt1", "Opt2", "Opt3", "Opt4", 4, "Test Question", "Your Wrong!!");
		for(int i = 0; i < 100; i++) {
			q.onUp();
		}
	}

	@Test
	void testOnDown() {
		Question q = new MultipleChoice("Opt1", "Opt2", "Opt3", "Opt4", 4, "Test Question", "Your Wrong!!");
		for(int i = 0; i < 100; i++) {
			q.onDown();
		}
	}
	
	@Test
	void testOnEnter() {
		Question q = new MultipleChoice("Opt1", "Opt2", "Opt3", "Opt4", 4, "Test Question", "Your Wrong!!");
		for(int i = 0; i < 100; i++) {
			q.onEnter();
		}
	}
	
	@Test
	void testOnDownAndEnterToAnswer() {
		Question q = new MultipleChoice("Opt1", "Opt2", "Opt3", "Opt4", 4, "Test Question", "Your Wrong!!");
		q.onDown(); // -> opt 2
		q.onDown(); // -> opt 3
		q.onDown(); // -> opt 4
		q.onEnter();
		assertTrue(q.isQuestionCorrect(), "Answer Incorrect");
	}

}
