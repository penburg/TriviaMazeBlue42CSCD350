/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import dungeon.Room.DoorPosition;
import dungeon.Room.DoorState;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

/**
 *
 * @author patrick
 */
public class Dungeon extends Region {

	private Canvas mCanvas;
	private Image mBackground;
	private double mScale;
	private IntegerProperty mIsQuestionTriggered;

	private Room[][] mGameBoard;
	//[0] East - West
	//[1] North - South
	private int[] mHeroLoc = {-1, -1};
	private int[] mExitLoc = {-1, -1};
	private int[] mEntranceLoc = {-1, -1};

	private Hero mHero;


	private Question question;

	private StringProperty statusString;

	private final int pWidth = 4096;
	private final int pHeight = 4096;
	public static final int BOARDSIZE = 5;

	private final Image gravestoneImage = new Image("images/gravestone.png");

	private final static Random rand = new Random();
	private HeroFactory heroFactory;

	private int[] questions = shuffleQuestionNumbers();
	private int currentQuestion = 0;



	public Dungeon() {
		mScale = 1;
		setPrefSize(pWidth * mScale, pHeight * mScale);
		mCanvas = new Canvas(pWidth * mScale, pHeight * mScale);
		mBackground = new Image("images/background.png");
		getChildren().add(mCanvas);
		heroFactory = new HeroFactory();
		statusString = new SimpleStringProperty();
		mIsQuestionTriggered = new SimpleIntegerProperty(0);
		mIsQuestionTriggered.addListener(notUsed -> onQuestionTrigger());
		newGame();



		//draw();
	}

	public StringProperty getStatusStringProperty() {
		return statusString;
	}

	private int[] shuffleQuestionNumbers()
	{

		int[] nums = new int[35];
		int i = 0;

		for (i = 0; i < 35; i++)
		{
			nums[i] = i + 1;
		}

		Random rgen = new Random();		

		for (i = 0; i < nums.length; i++)
		{
			int randomPosition = rgen.nextInt(nums.length);
			int temp = nums[i];
			nums[i] = nums[randomPosition];
			nums[randomPosition] = temp;
		}

		return nums;
	}

	private Question askQuestion(int questionNumberInDatabase)
	{
		String sql = "SELECT id, type, question, correct, shortanswercorrect, a1, a2, a3, a4, explanation FROM questions WHERE id = " + questionNumberInDatabase;

		int qType = 0, correct = 0;
		String question = "", shortanswer = "", a1 = "", a2 = "", a3 = "", a4 = "", explanation = "";

		String url = "jdbc:sqlite:src/questions.db";

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt  = conn.createStatement();
				ResultSet rs    = stmt.executeQuery(sql))
		{
			while (rs.next())
			{
				qType = rs.getInt("type");
				correct = rs.getInt("correct");
				question = rs.getString("question");
				shortanswer = rs.getString("shortanswercorrect");
				a1 = rs.getString("a1");
				a2 = rs.getString("a2");
				a3 = rs.getString("a3");
				a4 = rs.getString("a4");
				explanation = rs.getString("explanation");
			}
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}

		Question q = null;

		switch (qType)
		{
		case 1:
			q = new MultipleChoice(a1, a2, a3, a4, correct, question, explanation);
			statusString.set("DEBUG - Multiple Choice Question");
			break;
		case 2:
			q = new TrueFalse(correct, question, explanation);
			statusString.set("DEBUG - True / False Question");
			break;
		case 3:
			q = new ShortAnswer(shortanswer, question, explanation);
			statusString.set("DEBUG -Short Answer Question");
			break;
		}
		return q;
	}

	public void draw() {
		GraphicsContext gc = mCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
		//image offsets to be centered 
		double imgX = (getWidth() - (pWidth * mScale)) / 2f;
		double imgY = (getHeight() - (pHeight * mScale)) / 2f;
		//System.out.println("offset " + imgX + " " + imgY);
		if (question == null) {
			gc.drawImage(mBackground, imgX, imgY, (pWidth * mScale), (pHeight * mScale));
			double offset = pWidth * mScale / (double) BOARDSIZE;
			for (int i = 0; i < BOARDSIZE; i++) {
				for (int j = 0; j < BOARDSIZE; j++) {
					mGameBoard[i][j].draw(imgX, imgY, i, j, offset, mCanvas);
				}

			}
		} 
		else if(question != null) {
			question.draw(imgX, imgY, 0, 0, (pWidth * mScale), mCanvas);
		}
	}

	@Override
	protected void layoutChildren() {
		double w = getWidth();
		double h = getHeight();

		mScale = (float) w / pWidth;

		if (mScale * pHeight > (float) h) {
			mScale = (float) h / pHeight;
		}
		mCanvas.setHeight(h);
		mCanvas.setWidth(w);

		draw();
	}

	private void newLevel() {
		//System.out.println("Start new game");
		mGameBoard = new Room[BOARDSIZE][BOARDSIZE];

		for (int i = 0; i < BOARDSIZE; i++) {
			for (int j = 0; j < BOARDSIZE; j++) {
				mGameBoard[i][j] = new Room(statusString, mIsQuestionTriggered);
				if(j == 0) {
					mGameBoard[i][j].setDoorState(DoorState.WALL, DoorPosition.NORTH);
				}
				if(j == BOARDSIZE -1) {
					mGameBoard[i][j].setDoorState(DoorState.WALL, DoorPosition.SOUTH);
				}
				if(i == 0) {
					mGameBoard[i][j].setDoorState(DoorState.WALL, DoorPosition.WEST);
				}
				if(i == BOARDSIZE -1) {
					mGameBoard[i][j].setDoorState(DoorState.WALL, DoorPosition.EAST);
				}
			}

		}
		//System.out.println("Board created");
		mHeroLoc[0] = rand.nextInt(BOARDSIZE);
		mHeroLoc[1] = rand.nextInt(BOARDSIZE);
		//System.out.println("Hero located");
		mEntranceLoc[0] = mHeroLoc[0];
		mEntranceLoc[1] = mHeroLoc[1];

		mExitLoc[0] = mHeroLoc[0];
		mExitLoc[1] = mHeroLoc[1];

		while (mExitLoc[0] == mHeroLoc[0] && mExitLoc[1] == mHeroLoc[1]) {
			mExitLoc[0] = rand.nextInt(BOARDSIZE);
			mExitLoc[1] = rand.nextInt(BOARDSIZE);
		}
		//System.out.println("Exit placed");
		mGameBoard[mEntranceLoc[0]][mEntranceLoc[1]].setIsEntrance();
		mGameBoard[mExitLoc[0]][mExitLoc[1]].setIsExit();

		mHero = heroFactory.createHero();
		//System.out.println("Hero Created");

		mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHero(mHero);
		mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHeroLoc(0, 0);
		//System.out.println("Hero Placed");

		//System.out.println("Placing treasure");

		//System.out.println("end new game");
		draw();
	}

	public void newGame() {

		newLevel();

	}

	public void onLeft() {
		if (question == null) {
			moveHero(-1, 0);
		}
		draw();
	}

	public void onRight() {
		if (question == null) {
			moveHero(1, 0);
		}
		draw();
	}

	public void onUp() {
		if(question != null){
			question.onUp();
		}
		else {
			moveHero(0, -1);
		}
		draw();
	}

	public void onDown() {
		if(question != null) {
			question.onDown();
		}
		else {
			moveHero(0, 1);
		}
		draw();
	}

	private void onGameOver(boolean isWinner) {
		if(isWinner) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("GameOver");
			alert.setHeaderText("You have found the exit!\nWould you like to play again?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				newGame();
			} else {
				// ... user chose CANCEL or closed the dialog
			}
		}
		else {
			mHero.kill();
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("GameOver");
			alert.setHeaderText("You have failed to find the exit!\nYou have died of starvation, sorry");
			alert.show();
			draw();
		}
	}

	private void moveHero(int x, int y) {
		int oldX = mGameBoard[mHeroLoc[0]][mHeroLoc[1]].getHeroLoc()[0];
		int oldY = mGameBoard[mHeroLoc[0]][mHeroLoc[1]].getHeroLoc()[1];
		int newX = oldX + x;
		int newY = oldY + y;

		//newX = newX % Room.ROOMSIZE;
		//newY = newY % Room.ROOMSIZE;
		if (mHero.isAlive()) {
			ArrayList<Room> path = new ArrayList<Room>();
			path = exitPath(path, mHeroLoc[0], mHeroLoc[1]);
			String pathMessage = "";
			if(path == null) {
				onGameOver(false);
			}
			else {
				pathMessage = " Exit path found length to exit: " + path.size();
			}
			statusString.set(this.mHero.toString() + pathMessage);
			mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHeroLoc(newX, newY);

			if (mGameBoard[mHeroLoc[0]][mHeroLoc[1]].getHeroLoc()[0] >= Room.ROOMSIZE) {
				mHeroLoc[0]++;
				if (mHeroLoc[0] < BOARDSIZE) {
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHero(mHero);
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHeroLoc(0, newY);
				} else {
					mHeroLoc[0]--;
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHero(mHero);
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHeroLoc(oldX, oldY);
				}
				//mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setDoorState(DoorState.OPEN, DoorPosition.WEST);
			} else if (mGameBoard[mHeroLoc[0]][mHeroLoc[1]].getHeroLoc()[0] < 0) {
				mHeroLoc[0]--;
				if (mHeroLoc[0] >= 0) {
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHero(mHero);
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHeroLoc(Room.ROOMSIZE - 1, newY);
				} else {
					mHeroLoc[0]++;
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHero(mHero);
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHeroLoc(oldX, oldY);
				}
				//mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setDoorState(DoorState.OPEN, DoorPosition.EAST);
			}
			if (mGameBoard[mHeroLoc[0]][mHeroLoc[1]].getHeroLoc()[1] >= Room.ROOMSIZE) {
				mHeroLoc[1]++;
				if (mHeroLoc[1] < BOARDSIZE) {
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHero(mHero);
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHeroLoc(newX, 0);
				} else {
					mHeroLoc[1]--;
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHero(mHero);
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHeroLoc(oldX, oldY);
				}
				//mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setDoorState(DoorState.OPEN, DoorPosition.NORTH);
			} else if (mGameBoard[mHeroLoc[0]][mHeroLoc[1]].getHeroLoc()[1] < 0) {
				mHeroLoc[1]--;
				if (mHeroLoc[1] >= 0) {
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHero(mHero);
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHeroLoc(newX, Room.ROOMSIZE - 1);
				} else {
					mHeroLoc[1]++;
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHero(mHero);
					mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setHeroLoc(oldX, oldY);
				}
				//mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setDoorState(DoorState.OPEN, DoorPosition.SOUTH);
			}
			if(mGameBoard[mHeroLoc[0]][mHeroLoc[1]].isExit()) {
				draw();
				onGameOver(true);
			}
		} 
		
		else {
			this.mHero.setCharacterImage(gravestoneImage);
			this.statusString.set(this.mHero.getName() + " is dead :(");
		}
	}

	public void onEnter() {
		if (mHero.isAlive()) {
			if (question == null) {
				if (mGameBoard[mHeroLoc[0]][mHeroLoc[1]].isEntrance() && mHero.isAlive()) {
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Return to the surface?");
					alert.setHeaderText("Would you like to flee the dungeon \nand return to the surface in disgrace?");
					Optional<ButtonType> results = alert.showAndWait();
					if (results.get() == ButtonType.OK) {
						statusString.set(mHero.getName() + " runs away in terror");
						Platform.exit();
					}

				} else if (mGameBoard[mHeroLoc[0]][mHeroLoc[1]].isExit() && mHero.isAlive()) {
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Exit the Dungeon?");
					String text = "Would you like to exit the dungeon?\n";
					alert.setHeaderText(text);
					Optional<ButtonType> results = alert.showAndWait();
					if (results.get() == ButtonType.OK) {
						Platform.exit();
					}

				}
			}
			if(question != null) {
				question.onEnter();
			}

		}
		draw();
	}

	public void onUseVisionPotion() {
		if (mHero.isAlive()) {
			if (mHero.getNumPotionsVision() > 0) {
				int a, b, c, d;
				a = (mHeroLoc[0] > 0) ? mHeroLoc[0] - 1 : 0;
				b = (mHeroLoc[0] < BOARDSIZE - 1) ? mHeroLoc[0] + 1 : BOARDSIZE - 1;
				//vb = mHeroLoc[0] + 1;
				c = (mHeroLoc[1] > 0) ? mHeroLoc[1] - 1 : 0;
				d = (mHeroLoc[1] < BOARDSIZE - 1) ? mHeroLoc[1] + 1 : BOARDSIZE - 1;
				//d = mHeroLoc[1] + 1;
				for (int i = a; i <= b; i++) {
					for (int j = c; j <= d; j++) {
						mGameBoard[i][j].setIsVisable(true);

					}

				}
				mHero.usePotionVision();
				statusString.set(mHero.getName() + " drank a vision potion");
				;
			} else {
				statusString.set(mHero.getName() + " has no vision potions to drink.");
			}
		}
		draw();

	}

	public void onCheatCode() {
		statusString.set("You shall from this day forward be called The Cheater " + mHero.getName());
		mHero.setName("The Cheater " + mHero.getName());
		for (int i = 0; i < BOARDSIZE; i++) {
			for (int j = 0; j < BOARDSIZE; j++) {
				mGameBoard[i][j].setIsVisable(true);
			}

		}
		draw();
	}

	public void onDebugQuestion() {
		if(question == null) {
			statusString.set("Debug launch question screen");
			question = new NullQuestion();
			question.getQuestionSubmitted().addListener(notUsed -> questionSubmitted());
		}
		else {
			question = null;
			statusString.set("Debug Question screen terminated");
		}
		draw();
	}

	private void onQuestionTrigger() {
		if(question == null) {
			question = askQuestion(questions[currentQuestion]);
			question.getQuestionSubmitted().addListener(notUsed -> questionSubmitted());
			currentQuestion++; //Iterates to the next question in the question list.
		}
		else {
			question = null;
		}
		draw();
	}

	private void questionSubmitted() {
		mGameBoard[mHeroLoc[0]][mHeroLoc[1]].setDoorInQuestion(question.isQuestionCorrect());
		int nextRoom[] = new int[2];
		DoorPosition nextRoomDoor = null;

		switch(mGameBoard[mHeroLoc[0]][mHeroLoc[1]].getDoorInQuestion()) {
		case NORTH:
			nextRoom[0] = mHeroLoc[0];
			nextRoom[1] = mHeroLoc[1] - 1;
			nextRoomDoor =  DoorPosition.SOUTH;
			break;
		case SOUTH:
			nextRoom[0] = mHeroLoc[0];
			nextRoom[1] = mHeroLoc[1] + 1;
			nextRoomDoor =  DoorPosition.NORTH;
			break;
		case EAST:
			nextRoom[0] = mHeroLoc[0] + 1;
			nextRoom[1] = mHeroLoc[1];
			nextRoomDoor =  DoorPosition.WEST;
			break;
		case WEST:
			nextRoom[0] = mHeroLoc[0] - 1;
			nextRoom[1] = mHeroLoc[1];
			nextRoomDoor =  DoorPosition.EAST;
			break;
		}
		if(question.isQuestionCorrect()) {
			statusString.set("Open Sesame!!");
			mGameBoard[nextRoom[0]][nextRoom[1]].setDoorState(DoorState.OPEN, nextRoomDoor);
		}
		else {
			statusString.set(this.question.getExplanation());
			mGameBoard[nextRoom[0]][nextRoom[1]].setDoorState(DoorState.LOCKED, nextRoomDoor);
		}

		this.question = null;
		draw();

	}

	public void onDoorCheatCode() {
		for (int i = 0; i < BOARDSIZE; i++) {
			for (int j = 0; j < BOARDSIZE; j++) {
				mGameBoard[i][j].setDoorState(DoorState.OPEN, DoorPosition.EAST);
				mGameBoard[i][j].setDoorState(DoorState.OPEN, DoorPosition.WEST);
				mGameBoard[i][j].setDoorState(DoorState.OPEN, DoorPosition.NORTH);
				mGameBoard[i][j].setDoorState(DoorState.OPEN, DoorPosition.SOUTH);
			}

		}
		draw();

	}

	public  void traverseMaze() {
		for (int i = 0; i < mGameBoard.length-1; i++) {
			for (int j = 0; j < mGameBoard[i].length; j++) {
				System.out.println("Room at " + i + ", " + j + ", " + mGameBoard[i][j].isExit());

			}

		}
		draw();

	}

	/**
	 * Returns a path to the exit, may not be optimal
	 * 
	 * @param prevPath The previous path taken
	 * @param x The East - West location on the game board to start from
	 * @param y The North - South location on the game board to start from
	 * @return The path to exit or null if none exist
	 */
	private ArrayList<Room> exitPath(ArrayList<Room> prevPath, int x, int y){
		if(prevPath.contains(mGameBoard[x][y]) || x < 0 || y < 0 || x > BOARDSIZE || y > BOARDSIZE) {
			//Returned to previously visited room
			return null;
		}
		prevPath.add(mGameBoard[x][y]);

		if(mGameBoard[x][y].isExit()) {
			return prevPath;
		}
		else {
			DoorState[] doors = mGameBoard[x][y].getDoors();
			ArrayList<Room> nextPath = null;
			if(doors[DoorPosition.NORTH.ordinal()] == DoorState.OPEN || doors[DoorPosition.NORTH.ordinal()] ==  DoorState.CLOSED) {
				nextPath = exitPath(prevPath, x, y - 1);
			}
			if(nextPath == null && (doors[DoorPosition.SOUTH.ordinal()] == DoorState.OPEN || doors[DoorPosition.SOUTH.ordinal()] ==  DoorState.CLOSED)) {
				nextPath = exitPath(prevPath, x, y + 1);
			}
			if(nextPath == null && (doors[DoorPosition.EAST.ordinal()] == DoorState.OPEN || doors[DoorPosition.EAST.ordinal()] ==  DoorState.CLOSED)) {
				nextPath = exitPath(prevPath, x + 1, y);
			}
			if(nextPath == null && (doors[DoorPosition.WEST.ordinal()] == DoorState.OPEN || doors[DoorPosition.WEST.ordinal()] ==  DoorState.CLOSED)) {
				nextPath = exitPath(prevPath, x - 1, y);
			}

			return nextPath;

		}

	}
}
