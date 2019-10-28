/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import java.util.Optional;
import java.util.Random;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
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
    private BooleanProperty mIsPaused;

    private Room[][] mGameBoard;
    private int[] mHeroLoc = {-1, -1};
    private int[] mExitLoc = {-1, -1};
    private int[] mEntranceLoc = {-1, -1};

    private Hero mHero;

    private BattleScene battle;
    private BooleanProperty isBattleOver;

    private StringProperty statusString;

    private final int pWidth = 4096;
    private final int pHeight = 4096;
    public static final int BOARDSIZE = 5;

    private final Image gravestoneImage = new Image("images/gravestone.png");

    private final static Random rand = new Random();
    private HeroFactory heroFactory;

    private final String TREASURES[] = {"Abstraction", "Encapsulation", "Inheritance", "Polymorphism"};

    public Dungeon() {
        mScale = 1;
        //scale based on picture
        setPrefSize(pWidth * mScale, pHeight * mScale);
        mCanvas = new Canvas(pWidth * mScale, pHeight * mScale);
        mBackground = new Image("images/background.png");
        getChildren().add(mCanvas);
        heroFactory = new HeroFactory();
        statusString = new SimpleStringProperty();
        newGame();

        

        //draw();
    }

    public StringProperty getStatusStringProperty() {
        return statusString;
    }

    public void draw() {
        GraphicsContext gc = mCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
        //image offsets to be centered 
        double imgX = (getWidth() - (pWidth * mScale)) / 2f;
        double imgY = (getHeight() - (pHeight * mScale)) / 2f;
        //System.out.println("offset " + imgX + " " + imgY);
        if (battle == null) {
            gc.drawImage(mBackground, imgX, imgY, (pWidth * mScale), (pHeight * mScale));
            double offset = pWidth * mScale / (double) BOARDSIZE;
            for (int i = 0; i < BOARDSIZE; i++) {
                for (int j = 0; j < BOARDSIZE; j++) {
                    mGameBoard[i][j].draw(imgX, imgY, i, j, offset, mCanvas);
                }

            }
        } else {
            battle.draw(imgX, imgY, 0, 0, (pWidth * mScale), mCanvas);
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
                mGameBoard[i][j] = new Room(statusString);
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

        int x = rand.nextInt(BOARDSIZE);
        int y = rand.nextInt(BOARDSIZE);
        //System.out.println("Placing treasure");
        for (String s : TREASURES) {
            while (mGameBoard[x][y].hasTreasure() || mGameBoard[x][y].isEntrance() || mGameBoard[x][y].isExit()) {
                x = rand.nextInt(BOARDSIZE);
                y = rand.nextInt(BOARDSIZE);
            }
            mGameBoard[x][y].setTreasure(s);
            x = rand.nextInt(BOARDSIZE);
            y = rand.nextInt(BOARDSIZE);
        }

        //System.out.println("end new game");
        draw();
    }

    public void newGame() {

        newLevel();

    }

    public void onLeft() {
        if (battle == null) {
            moveHero(-1, 0);
        }
        draw();
    }

    public void onRight() {
        if (battle == null) {
            moveHero(1, 0);
        }
        draw();
    }

    public void onUp() {
        if (battle == null) {
            moveHero(0, -1);
        } else {
            battle.onUp();
        }
        draw();
    }

    public void onDown() {
        if (battle == null) {
            moveHero(0, 1);
        } else {
            battle.onDown();
        }
        draw();
    }

    private void moveHero(int x, int y) {
        int oldX = mGameBoard[mHeroLoc[0]][mHeroLoc[1]].getHeroLoc()[0];
        int oldY = mGameBoard[mHeroLoc[0]][mHeroLoc[1]].getHeroLoc()[1];
        int newX = oldX + x;
        int newY = oldY + y;

        //newX = newX % Room.ROOMSIZE;
        //newY = newY % Room.ROOMSIZE;
        if (mHero.isAlive()) {
            statusString.set(this.mHero.toString());
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
            }
        } else {
            this.mHero.setCharacterImage(gravestoneImage);
            this.statusString.set(this.mHero.getName() + " is dead :(");
        }
    }

    public void onAttack() {
        if (mHero.isAlive()) {
            if (battle == null) {
                battle = new BattleScene(this.mHero, mGameBoard[mHeroLoc[0]][mHeroLoc[1]].getMonster());
                battle.isBattleOver().addListener(notUsed -> battleOver());
            } else {
                battle = null;
            }

        }
        draw();
    }

    public void onEnter() {
        if (mHero.isAlive()) {
            if (battle == null) {
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
                    text += mHero.getName() + " has " + mHero.TreasureList.size() + " of 4 treasures";
                    alert.setHeaderText(text);
                    Optional<ButtonType> results = alert.showAndWait();
                    if (results.get() == ButtonType.OK) {
                        Platform.exit();
                    }
                    
                }
            } else {
                battle.onEnter();
            }

        }
        draw();
    }

    private void battleOver() {
        if (battle.isBattleOver().get()) {
            
            this.battle = null;
            
        }
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

    public void onUseHealthPotion() {
        if (mHero.isAlive()) {
            if (mHero.getNumPotionsHeal() > 0) {
                statusString.set(mHero.getName() + " drank a health potion\nand gained " + mHero.usePotionHeal() + " hit points.");

            } else {
                statusString.set(mHero.getName() + " has no health potions to drink.");
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

}
