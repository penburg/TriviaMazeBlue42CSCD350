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

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dungeon.Question;
import dungeon.VideoQuestion;
import javafx.embed.swing.JFXPanel;

class VideoQuestionTest {

	@BeforeEach
	public void initialize() {
		//Initialize graphics
		//https://stackoverflow.com/questions/27839441/internal-graphics-not-initialized-yet-javafx
		JFXPanel jfxPanel = new JFXPanel();
	}

	@Test
	void testCreation() {
		ArrayList<String> options = new ArrayList<String>(Arrays.asList( "true" , "false"));
		Question q = new VideoQuestion("Test Question", "URL", options, 1, "Your Wrong");
		assertTrue(q.getExplanation().equals("Your Wrong"), "Explanation is not blank");
	}

	@Test
	void testOnUp() {
		ArrayList<String> options = new ArrayList<String>(Arrays.asList( "true" , "false"));
		Question q = new VideoQuestion("Test Question", "URL", options, 1, "Your Wrong");
		for(int i = 0; i < 100; i++) {
			q.onUp();
		}
	}

	@Test
	void testOnDown() {
		ArrayList<String> options = new ArrayList<String>(Arrays.asList( "true" , "false"));
		Question q = new VideoQuestion("Test Question", "URL", options, 1, "Your Wrong");
		for(int i = 0; i < 100; i++) {
			q.onDown();
		}
	}
	
	@Test
	void testOnEnter() {
		ArrayList<String> options = new ArrayList<String>(Arrays.asList( "true" , "false"));
		Question q = new VideoQuestion("Test Question", "URL", options, 1, "Your Wrong");
		for(int i = 0; i < 100; i++) {
			q.onEnter();
		}
	}
	
	@Test
	void testOnDownUpAndEnterToAnswer() {
		ArrayList<String> options = new ArrayList<String>(Arrays.asList( "true" , "false"));
		Question q = new VideoQuestion("Test Question", "URL", options, 1, "Your Wrong");
		q.onDown();
		q.onUp();
		q.onEnter();
		assertTrue(q.isQuestionCorrect(), "Answer Incorrect");
	}

}
