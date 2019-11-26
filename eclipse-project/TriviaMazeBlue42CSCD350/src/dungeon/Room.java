/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import java.util.Random;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author penburg
 */
public class Room implements Drawable {

	public enum DoorState{
		CLOSED,
		OPEN,
		LOCKED,
		WALL,
	};

	public enum DoorPosition{
		NORTH,
		SOUTH,
		EAST,
		WEST,
	};

	private boolean isVisable;
	private boolean isEntrance;
	private boolean isExit;
	private DoorState doors[] = {DoorState.CLOSED, DoorState.CLOSED, DoorState.CLOSED, DoorState.CLOSED};
	private DoorPosition doorInQuestion;
	private String treasure;
	private Hero hero;
	private int[] heroLoc;
	private int[][] roomGrid;
	private StringProperty statusString;
	private IntegerProperty questionTrigger;

	private final int PROBIBILITY = 10;
	public static final int ROOMSIZE = 3;
	private final int ROOM_MID = ROOMSIZE / 3;
	private final int EMPTY = 0;
	private final int ENTRANCE = 3;
	private final int EXIT = 4;
	private final int POTION_HEAL = 5;
	private final int POTION_VISION = 6;
	private final int POTION_GREEN = 7;
	private final double wallWidth = 3;
	private final double doorWidth = 5;

	public static final Image IMAGE_POTION_HEAL = new Image("images/potion-red.png");
	public static final Image IMAGE_POTION_VISION = new Image("images/potion-blue.png");
	public static final Image IMAGE_POTION_GREEN = new Image("images/potion-green.png");
	public static final Image IMAGE_TREASURE = new Image("images/pillar2.png");
	public static final Image IMAGE_EXIT = new Image("images/exit.png");
	public static final Image IMAGE_ENTRANCE = new Image("images/ladder.png");

	private static final Random rand = new Random();

	public Room(StringProperty statusS, IntegerProperty trigger) {
		//System.out.println("Creating room");
		statusString = statusS;
		questionTrigger = trigger;
		roomGrid = new int[ROOMSIZE][ROOMSIZE];
		heroLoc = new int[2];
		isVisable = false;
		isExit = false;
		isEntrance = false;

		//Vision Potion
		if (rand.nextInt(100) <= PROBIBILITY) {
			setItem(POTION_VISION);
		}

	}


	public final void setItem(int item) {
		int x = rand.nextInt(ROOMSIZE);
		int y = rand.nextInt(ROOMSIZE);
		while (roomGrid[x][y] != EMPTY) {
			x = rand.nextInt(ROOMSIZE);
			y = rand.nextInt(ROOMSIZE);
		}
		roomGrid[rand.nextInt(ROOMSIZE)][rand.nextInt(ROOMSIZE)] = item;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
		this.isVisable = true;
	}

	public int[] getHeroLoc() {
		return heroLoc;
	}

	public void setHeroLoc(int x, int y) {

		if (x < 0) {
			if (y == ROOM_MID && doors[DoorPosition.WEST.ordinal()] == DoorState.OPEN) {
				this.hero = null;
			} 
			else if (y == ROOM_MID && doors[DoorPosition.WEST.ordinal()] == DoorState.CLOSED) {
				x = 0;
				doorInQuestion = DoorPosition.WEST;
				questionTrigger.set(questionTrigger.get() + 1);
				//statusString.set("WEST");
			} 
			else {
				x = 0;
			}
		}

		if (y < 0) {
			if (x == ROOM_MID && doors[DoorPosition.NORTH.ordinal()] == DoorState.OPEN) {
				this.hero = null;
			}
			else if (x == ROOM_MID && doors[DoorPosition.NORTH.ordinal()] == DoorState.CLOSED) {
				y = 0;
				doorInQuestion = DoorPosition.NORTH;
				questionTrigger.set(questionTrigger.get() + 1);
				//statusString.set("NORTH");
			} 
			else {
				y = 0;
			}
		}

		if (x >= ROOMSIZE) {
			if (y == ROOM_MID && doors[DoorPosition.EAST.ordinal()] == DoorState.OPEN) {
				this.hero = null;
			}
			else if (y == ROOM_MID && doors[DoorPosition.EAST.ordinal()] == DoorState.CLOSED) {
				x = ROOMSIZE - 1;
				doorInQuestion = DoorPosition.EAST;
				questionTrigger.set(questionTrigger.get() + 1);
				//statusString.set("EAST");
			}
			else {
				x = ROOMSIZE - 1;
			}
		}

		if (y >= ROOMSIZE) {
			if (x == ROOM_MID && doors[DoorPosition.SOUTH.ordinal()] == DoorState.OPEN) {
				this.hero = null;
			}
			else if (x == ROOM_MID && doors[DoorPosition.SOUTH.ordinal()] == DoorState.CLOSED) {
				y = ROOMSIZE - 1;
				doorInQuestion = DoorPosition.SOUTH;
				questionTrigger.set(questionTrigger.get() + 1);
				//statusString.set("SOUTH");
			}
			else {
				y = ROOMSIZE - 1;
			}
		}

		this.heroLoc[0] = x;
		this.heroLoc[1] = y;



		if (this.hero != null) {
			switch (roomGrid[x][y]) {
			case ENTRANCE:
				break;
			case EXIT:
				break;
			case POTION_HEAL:
				this.hero.addPotionHeal();
				this.statusString.set(this.hero.getName() + " found a potion of healing");
				roomGrid[x][y] = EMPTY;
				break;
			case POTION_VISION:
				this.hero.addPotionVision();
				this.statusString.set(this.hero.getName() + " found a potion of vision");
				roomGrid[x][y] = EMPTY;
				break;
			case POTION_GREEN://future use
				break;
			default:
				break;
			}
		}
	}

	public DoorState[] getDoors() {
		return doors;
	}

	public void setDoorState(DoorState state, DoorPosition pos) {
		if(doors[pos.ordinal()] != DoorState.WALL) {
			doors[pos.ordinal()] = state;
		}
	}

	public void setIsVisable(boolean isVisable) {
		this.isVisable = isVisable;
	}

	public void setIsEntrance() {
		roomGrid = new int[ROOMSIZE][ROOMSIZE];
		roomGrid[0][0] = ENTRANCE;
		this.isExit = false;
		this.isEntrance = true;
		this.isVisable = true;
	}

	public void setIsExit() {
		roomGrid = new int[ROOMSIZE][ROOMSIZE];
		roomGrid[ROOMSIZE - 1][ROOMSIZE - 1] = EXIT;
		this.isEntrance = false;
		this.isExit = true;
	}

	public boolean hasTreasure() {
		return treasure != null;
	}

	public boolean isEntrance() {
		return isEntrance;
	}

	public boolean isExit() {
		return isExit;
	}

	@Override
	public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		if (!isVisable) {
			canvas.getGraphicsContext2D().fillRect(imgX + (x * offset), imgY + (y * offset), offset, offset);
		} else {
			double topX = imgX + (x * offset);
			double topY = imgY + (y * offset);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(wallWidth);
			gc.strokeRect(topX, topY, offset, offset);
			double doorLength = (double) offset / 3f;


			setDoorColor(gc, doors[DoorPosition.NORTH.ordinal()]);
			gc.strokeLine(topX + doorLength, topY, topX + doorLength + doorLength, topY);

			setDoorColor(gc, doors[DoorPosition.EAST.ordinal()]);
			gc.strokeLine(topX + offset, topY + doorLength, topX + offset, topY + doorLength + doorLength);

			setDoorColor(gc, doors[DoorPosition.SOUTH.ordinal()]);
			gc.strokeLine(topX + doorLength, topY + offset, topX + doorLength + doorLength, topY + offset);

			setDoorColor(gc, doors[DoorPosition.WEST.ordinal()]);
			gc.strokeLine(topX, topY + doorLength, topX, topY + doorLength + doorLength);

			double roomOffset = offset / (double) ROOMSIZE;
			for (int i = 0; i < ROOMSIZE; i++) {
				for (int j = 0; j < ROOMSIZE; j++) {
					switch (roomGrid[i][j]) {
					case ENTRANCE:
						gc.drawImage(IMAGE_ENTRANCE, (roomOffset * i) + imgX + (x * offset), (roomOffset * j) + imgY + (y * offset), roomOffset, roomOffset);
						break;
					case EXIT:
						gc.drawImage(IMAGE_EXIT, (roomOffset * i) + imgX + (x * offset), (roomOffset * j) + imgY + (y * offset), roomOffset, roomOffset);
						break;
					case POTION_HEAL:
						gc.drawImage(IMAGE_POTION_HEAL, (roomOffset * i) + imgX + (x * offset), (roomOffset * j) + imgY + (y * offset), roomOffset, roomOffset);
						break;
					case POTION_VISION:
						gc.drawImage(IMAGE_POTION_VISION, (roomOffset * i) + imgX + (x * offset), (roomOffset * j) + imgY + (y * offset), roomOffset, roomOffset);
						break;
					case POTION_GREEN:
						gc.drawImage(IMAGE_POTION_GREEN, (roomOffset * i) + imgX + (x * offset), (roomOffset * j) + imgY + (y * offset), roomOffset, roomOffset);
						break;
					default:
						break;
					}
				}

			}

			if (this.hero != null) {
				this.hero.draw(imgX + (x * offset), imgY + (y * offset), heroLoc[0], heroLoc[1], roomOffset, canvas);
			}

		}
	}

	private void setDoorColor(GraphicsContext gc, DoorState d) {
		switch (d){
		case OPEN:
			gc.setLineWidth(doorWidth);
			gc.setStroke(Color.WHITESMOKE);
			break;
		case CLOSED:
			gc.setLineWidth(doorWidth);
			gc.setStroke(Color.BURLYWOOD);
			break;
		case LOCKED:
			gc.setLineWidth(doorWidth);
			gc.setStroke(Color.DARKRED);
			break;
		case WALL:
			gc.setLineWidth(wallWidth);
			gc.setStroke(Color.BLACK);
			break;
		}
	}


	/**
	 * @param questionCorrect
	 */
	public void setDoorInQuestion(boolean questionCorrect) {
		DoorState newState = questionCorrect ? DoorState.OPEN : DoorState.LOCKED;
		switch (doorInQuestion) {
		case NORTH:
			doors[DoorPosition.NORTH.ordinal()] = newState;
			break;
		case SOUTH:
			doors[DoorPosition.SOUTH.ordinal()] = newState;
			break;
		case EAST:
			doors[DoorPosition.EAST.ordinal()] = newState;
			break;
		case WEST:
			doors[DoorPosition.WEST.ordinal()] = newState;
			break;
		}

	}

	/**
	 * @return the doorInQuestion
	 */
	public DoorPosition getDoorInQuestion() {
		return doorInQuestion;
	}

}
