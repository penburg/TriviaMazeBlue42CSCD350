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

import dungeon.NullQuestion;
import dungeon.Question;
import javafx.embed.swing.JFXPanel;

class NullQuestionTest {
	
	@BeforeEach
	public void initialize() {
		//Initialize graphics
		//https://stackoverflow.com/questions/27839441/internal-graphics-not-initialized-yet-javafx
		JFXPanel jfxPanel = new JFXPanel();
	}

	@Test
	void testCreation() {
		
		Question q = new NullQuestion();
		assertTrue(q.getExplanation().isBlank(), "Explanation is not blank");
	}

	@Test
	void testOnUp() {
		Question q = new NullQuestion();
		for(int i = 0; i < 100; i++) {
			q.onUp();
		}
	}

	@Test
	void testOnDown() {
		Question q = new NullQuestion();
		for(int i = 0; i < 100; i++) {
			q.onDown();
		}
	}
	
	@Test
	void testOnEnter() {
		Question q = new NullQuestion();
		for(int i = 0; i < 100; i++) {
			q.onEnter();
		}
	}
	
	@Test
	void testOnDownAndEnterToAnswer() {
		Question q = new NullQuestion();
		q.onDown();
		q.onEnter();
		assertTrue(q.isQuestionCorrect(), "Answer Incorrect");
	}
}
